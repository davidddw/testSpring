package com.yunshan.cloudstack.utils.db;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TransactionContextInterceptor implements MethodInterceptor {

    public TransactionContextInterceptor() {

    }

    @Override
    public Object invoke(MethodInvocation m) throws Throwable {
        TransactionLegacy txn = TransactionLegacy.open(m.getMethod().getName());
        try {
            return m.proceed();
        } finally {
            txn.close();
        }
    }

}
