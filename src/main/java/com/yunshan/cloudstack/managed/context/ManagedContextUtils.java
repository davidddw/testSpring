package com.yunshan.cloudstack.managed.context;

public class ManagedContextUtils {

    private static final ThreadLocal<Object> OWNER = new ThreadLocal<Object>();

    public static boolean setAndCheckOwner(Object owner) {
        if (OWNER.get() == null) {
            OWNER.set(owner);
            return true;
        }

        return false;
    }

    public static boolean clearOwner(Object owner) {
        if (OWNER.get() == owner) {
            OWNER.remove();
            return true;
        }

        return false;
    }

    public static boolean isInContext() {
        return OWNER.get() != null;
    }

    public static void rethrowException(Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
        } else if (t instanceof Error) {
            throw (Error)t;
        }
    }

}
