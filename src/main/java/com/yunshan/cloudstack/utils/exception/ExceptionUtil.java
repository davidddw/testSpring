package com.yunshan.cloudstack.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    public static String toString(Throwable th) {
        return toString(th, true);
    }

    public static String toString(Throwable th, boolean printStack) {
        final StringWriter writer = new StringWriter();
        writer.append("Exception: " + th.getClass().getName() + "\n");
        writer.append("Message: ");
        writer.append(th.getMessage()).append("\n");

        if (printStack) {
            writer.append("Stack: ");
            th.printStackTrace(new PrintWriter(writer));
        }
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void rethrow(Throwable t, Class<T> clz) throws T {
        if (clz.isAssignableFrom(t.getClass()))
            throw (T)t;
    }

    public static <T extends Throwable> void rethrowRuntime(Throwable t) {
        rethrow(t, RuntimeException.class);
        rethrow(t, Error.class);
    }

}
