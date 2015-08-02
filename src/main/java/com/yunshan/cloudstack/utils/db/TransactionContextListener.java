package com.yunshan.cloudstack.utils.db;


public class TransactionContextListener implements ManagedContextListener<TransactionLegacy> {

    @Override
    public TransactionLegacy onEnterContext(boolean reentry) {
        if (!reentry) {
            return TransactionLegacy.open(Thread.currentThread().getName());
        }

        return null;
    }

    @Override
    public void onLeaveContext(TransactionLegacy data, boolean reentry) {
        if (!reentry) {
            data.close();
        }
    }

}
