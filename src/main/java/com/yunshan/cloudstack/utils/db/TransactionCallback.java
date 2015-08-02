package com.yunshan.cloudstack.utils.db;

public interface TransactionCallback<T> {

    public T doInTransaction(TransactionStatus status);

}
