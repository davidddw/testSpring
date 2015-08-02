package com.yunshan.cloudstack.utils.db;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Running with assertions on, will find all classes that are
 *
 * 1. Annotate method that starts and commits DB transactions.
 *    Transaction txn = Transaction.currentTxn();
 *    txn.start();
 *    ...
 *    txn.commit();
 *
 * 2. Annotate methods that uses a DAO's acquire method.
 *    _dao.acquireInLockTable(id);
 *    ...
 *    _dao.releaseFromLockTable(id);
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface DB {
}
