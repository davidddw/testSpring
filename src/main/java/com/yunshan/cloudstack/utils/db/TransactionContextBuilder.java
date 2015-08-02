package com.yunshan.cloudstack.utils.db;

import java.lang.reflect.Method;

import com.yunshan.cloudstack.utils.component.ComponentMethodInterceptor;


public class TransactionContextBuilder implements ComponentMethodInterceptor {
    public TransactionContextBuilder() {
    }

    public boolean needToIntercept(Method method) {
        DB db = method.getAnnotation(DB.class);
        if (db != null) {
            return true;
        }

        Class<?> clazz = method.getDeclaringClass();

        do {
            db = clazz.getAnnotation(DB.class);
            if (db != null) {
                return true;
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);

        return false;
    }

    public Object interceptStart(Method method, Object target) {
        return TransactionLegacy.open(method.getName());
    }

    public void interceptComplete(Method method, Object target, Object objReturnedInInterceptStart) {
        TransactionLegacy txn = (TransactionLegacy)objReturnedInInterceptStart;
        if (txn != null)
            txn.close();
    }

    public void interceptException(Method method, Object target, Object objReturnedInInterceptStart) {
        TransactionLegacy txn = (TransactionLegacy)objReturnedInInterceptStart;
        if (txn != null)
            txn.close();
    }
}
