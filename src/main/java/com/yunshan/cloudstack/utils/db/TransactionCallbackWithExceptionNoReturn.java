package com.yunshan.cloudstack.utils.db;

public abstract class TransactionCallbackWithExceptionNoReturn<E extends Throwable> implements TransactionCallbackWithException<Boolean, E> {

    @Override
    public final Boolean doInTransaction(TransactionStatus status) throws E {
        doInTransactionWithoutResult(status);
        return true;
    }

    public abstract void doInTransactionWithoutResult(TransactionStatus status) throws E;

}
