package com.yunshan.cloudstack.utils.db;

public class SearchBuilder<T> extends GenericSearchBuilder<T, T> {

    public SearchBuilder(Class<T> entityType) {
        super(entityType, entityType);
    }
}
