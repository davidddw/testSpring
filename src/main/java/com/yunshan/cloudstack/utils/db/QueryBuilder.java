package com.yunshan.cloudstack.utils.db;

public class QueryBuilder<T> extends GenericQueryBuilder<T, T> {

    public static <T> QueryBuilder<T> create(Class<T> entityType) {
        return new QueryBuilder<T>(entityType);
    }

    protected QueryBuilder(Class<T> entityType) {
        super(entityType, entityType);
    }

}
