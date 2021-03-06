package com.yunshan.cloudstack.utils.db;

import java.util.ArrayList;
import java.util.List;

import com.yunshan.cloudstack.utils.Pair;
import com.yunshan.cloudstack.utils.db.SearchCriteria.Func;
import com.yunshan.cloudstack.utils.db.SearchCriteria.Op;

public class GroupBy<J extends SearchBase<?, T, R>, T, R> {
    J _builder;
    List<Pair<Func, Attribute>> _groupBys;
    Having _having;

    public GroupBy(final J builder) {
        init(builder);
    }

    protected void init(final J builder) {
        _builder = builder;
        _groupBys = new ArrayList<Pair<Func, Attribute>>();
        _having = null;
        for (final Attribute attr : _builder.getSpecifiedAttributes()) {
            _groupBys.add(new Pair<Func, Attribute>(null, attr));
        }
        _builder.getSpecifiedAttributes().clear();
    }

    public GroupBy<J, T, R> group(final Object useless) {
        _groupBys.add(new Pair<Func, Attribute>(null, _builder.getSpecifiedAttributes().get(0)));
        _builder.getSpecifiedAttributes().clear();
        return this;
    }

    public GroupBy<J, T, R> group(final Func func, final Object useless) {
        _groupBys.add(new Pair<Func, Attribute>(func, _builder.getSpecifiedAttributes().get(0)));
        _builder.getSpecifiedAttributes().clear();
        return this;
    }

    public J having(final Func func, final Object obj, final Op op, final Object value) {
        assert (_having == null) : "You can only specify one having in a group by";
        final List<Attribute> attrs = _builder.getSpecifiedAttributes();
        assert attrs.size() == 1 : "You didn't specified an attribute";

        _having = new Having(func, attrs.get(0), op, value);
        _builder.getSpecifiedAttributes().clear();
        return _builder;
    }

    public void toSql(final StringBuilder builder) {
        builder.append(" GROUP BY ");
        for (final Pair<Func, Attribute> groupBy : _groupBys) {
            if (groupBy.first() != null) {
                String func = groupBy.first().toString();
                func = func.replaceFirst("@", groupBy.second().table + "." + groupBy.second().columnName);
                builder.append(func);
            } else {
                builder.append(groupBy.second().table + "." + groupBy.second().columnName);
            }

            builder.append(", ");
        }

        builder.delete(builder.length() - 2, builder.length());
        if (_having != null) {
            _having.toSql(builder);
        }
    }

    protected class Having {
        public Func func;
        public Attribute attr;
        public Op op;
        public Object value;

        public Having(final Func func, final Attribute attr, final Op op, final Object value) {
            this.func = func;
            this.attr = attr;
            this.op = op;
            this.value = value;
        }

        public void toSql(final StringBuilder builder) {
            builder.append(" HAVING ");
            if (func != null) {
                String f = func.toString();
                f = f.replaceFirst("@", attr.toString());
                builder.append(f);
            } else {
                builder.append(attr.toString());
            }

            builder.append(op.toString());
        }
    }
}
