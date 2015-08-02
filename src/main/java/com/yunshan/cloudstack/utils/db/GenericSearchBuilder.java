package com.yunshan.cloudstack.utils.db;

import java.util.UUID;

import com.yunshan.cloudstack.utils.db.SearchCriteria.Op;

public class GenericSearchBuilder<T, K> extends SearchBase<GenericSearchBuilder<T, K>, T, K> {
    protected GenericSearchBuilder(Class<T> entityType, Class<K> resultType) {
        super(entityType, resultType);
    }

    /**
     * Adds an AND condition to the SearchBuilder.
     *
     * @param name param name you will use later to set the values in this search condition.
     * @param field SearchBuilder.entity().get*() which refers to the field that you're searching on.
     * @param op operation to apply to the field.
     * @return this
     */
    public GenericSearchBuilder<T, K> and(String name, Object field, Op op) {
        constructCondition(name, " AND ", _specifiedAttrs.get(0), op);
        return this;
    }

    /**
     * Adds an AND condition.  Some prefer this method because it looks like
     * the actual SQL query.
     *
     * @param field field of entity object
     * @param op operator of the search condition
     * @param name param name used to later to set parameter value
     * @return this
     */
    public GenericSearchBuilder<T, K> and(Object field, Op op, String name) {
        constructCondition(name, " AND ", _specifiedAttrs.get(0), op);
        return this;
    }

    /**
     * Adds an AND condition but allows for a preset value to be set for this conditio.
     *
     * @param field field of the entity object
     * @param op operator of the search condition
     * @return Preset which allows you to set the values
     */
    public Preset and(Object field, Op op) {
        Condition condition = constructCondition(UUID.randomUUID().toString(), " AND ", _specifiedAttrs.get(0), op);
        return new Preset(this, condition);
    }

    /**
     * Starts the search
     *
     * @param field field of the entity object
     * @param op operator
     * @param name param name to refer to the value later.
     * @return this
     */
    public GenericSearchBuilder<T, K> where(Object field, Op op, String name) {
        return and(name, field, op);
    }

    /**
     * Starts the search but the value is already set during construction.
     *
     * @param field field of the entity object
     * @param op operator of the search condition
     * @return Preset which allows you to set the values
     */
    public Preset where(Object field, Op op) {
        return and(field, op);
    }

    protected GenericSearchBuilder<T, K> left(Object field, Op op, String name) {
        constructCondition(name, " ( ", _specifiedAttrs.get(0), op);
        return this;
    }

    protected Preset left(Object field, Op op) {
        Condition condition = constructCondition(UUID.randomUUID().toString(), " ( ", _specifiedAttrs.get(0), op);
        return new Preset(this, condition);
    }

    /**
     * Adds an condition that starts with open parenthesis.  Use cp() to close
     * the parenthesis.
     *
     * @param field field of the entity object
     * @param op operator
     * @param name parameter name used to set the value later
     * @return this
     */
    public GenericSearchBuilder<T, K> op(Object field, Op op, String name) {
        return left(field, op, name);
    }

    public Preset op(Object field, Op op) {
        return left(field, op);
    }

    /**
     * Adds an condition that starts with open parenthesis.  Use cp() to close
     * the parenthesis.
     *
     * @param name parameter name used to set the parameter value later.
     * @param field field of the entity object
     * @param op operator
     * @return this
     */
    public GenericSearchBuilder<T, K> op(String name, Object field, Op op) {
        return left(field, op, name);
    }

    /**
     * Adds an OR condition to the SearchBuilder.
     *
     * @param name param name you will use later to set the values in this search condition.
     * @param field SearchBuilder.entity().get*() which refers to the field that you're searching on.
     * @param op operation to apply to the field.
     * @return this
     */
    public GenericSearchBuilder<T, K> or(String name, Object field, Op op) {
        constructCondition(name, " OR ", _specifiedAttrs.get(0), op);
        return this;
    }

    /**
     * Adds an OR condition
     *
     * @param field field of the entity object
     * @param op operator
     * @param name parameter name
     * @return this
     */
    public GenericSearchBuilder<T, K> or(Object field, Op op, String name) {
        constructCondition(name, " OR ", _specifiedAttrs.get(0), op);
        return this;
    }

    /**
     * Adds an OR condition but the values can be preset
     *
     * @param field field of the entity object
     * @param op operator
     * @return Preset
     */
    public Preset or(Object field, Op op) {
        Condition condition = constructCondition(UUID.randomUUID().toString(), " OR ", _specifiedAttrs.get(0), op);
        return new Preset(this, condition);
    }

    /**
     * Convenience method to create the search criteria and set a
     * parameter in the search.
     *
     * @param name parameter name set during construction
     * @param values values to be inserted for that parameter
     * @return SearchCriteria
     */
    public SearchCriteria<K> create(String name, Object... values) {
        SearchCriteria<K> sc = create();
        sc.setParameters(name, values);
        return sc;
    }

    /**
     * Marks the SearchBuilder as completed in building the search conditions.
     */
    public synchronized void done() {
        super.finalize();
    }

    public class Preset {
        GenericSearchBuilder<T, K> builder;
        Condition condition;

        protected Preset(GenericSearchBuilder<T, K> builder, Condition condition) {
            this.builder = builder;
            this.condition = condition;
        }

        public GenericSearchBuilder<T, K> values(Object... params) {
            if (condition.op.getParams() > 0 && condition.op.params != params.length) {
                throw new RuntimeException("The # of parameters set " + params.length + " does not match # of parameters required by " + condition.op);
            }
            condition.setPresets(params);
            return builder;
        }
    }
}
