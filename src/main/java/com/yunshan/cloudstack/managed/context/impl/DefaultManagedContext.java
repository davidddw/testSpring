package com.yunshan.cloudstack.managed.context.impl;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunshan.cloudstack.managed.context.ManagedContext;
import com.yunshan.cloudstack.managed.context.ManagedContextListener;
import com.yunshan.cloudstack.managed.context.ManagedContextUtils;
import com.yunshan.cloudstack.managed.threadlocal.ManagedThreadLocal;


public class DefaultManagedContext implements ManagedContext {

    private static final Logger log = LoggerFactory.getLogger(DefaultManagedContext.class);

    List<ManagedContextListener<?>> listeners = new CopyOnWriteArrayList<ManagedContextListener<?>>();

    public void registerListener(ManagedContextListener<?> listener) {
        listeners.add(listener);
    }

    public void unregisterListener(ManagedContextListener<?> listener) {
        listeners.remove(listener);
    }

    public void runWithContext(final Runnable run) {
        try {
            callWithContext(new Callable<Object>() {
                public Object call() throws Exception {
                    run.run();
                    return null;
                }
            });
        } catch (Exception e) {
            /* Only care about non-checked exceptions
             * as the nature of runnable prevents checked
             * exceptions from happening
             */
            ManagedContextUtils.rethrowException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T callWithContext(Callable<T> callable) throws Exception {
        Object owner = new Object();

        Stack<ListenerInvocation> invocations = new Stack<ListenerInvocation>();
        boolean reentry = !ManagedContextUtils.setAndCheckOwner(owner);
        Throwable firstError = null;

        try {
            for (ManagedContextListener<?> listener : listeners) {
                Object data = null;

                try {
                    data = listener.onEnterContext(reentry);
                } catch (Throwable t) {
                    /* If one listener fails, still call all other listeners
                     * and then we will call onLeaveContext for all
                     */
                    if (firstError == null) {
                        firstError = t;
                    }
                    log.error("Failed onEnterContext for listener [{}]", listener, t);
                }

                /* Stack data structure is used because in between onEnter and onLeave
                 * the listeners list could have changed
                 */
                invocations.push(new ListenerInvocation((ManagedContextListener<Object>)listener, data));
            }

            try {
                if (firstError == null) {
                    /* Only call if all the listeners didn't blow up on onEnterContext */
                    return callable.call();
                } else {
                    throwException(firstError);
                    return null;
                }
            } finally {
                Throwable lastError = null;

                while (!invocations.isEmpty()) {
                    ListenerInvocation invocation = invocations.pop();
                    try {
                        invocation.listener.onLeaveContext(invocation.data, reentry);
                    } catch (Throwable t) {
                        lastError = t;
                        log.error("Failed onLeaveContext for listener [{}]", invocation.listener, t);
                    }
                }

                if (firstError == null && lastError != null) {
                    throwException(lastError);
                }
            }
        } finally {
            if (ManagedContextUtils.clearOwner(owner))
                ManagedThreadLocal.reset();
        }
    };

    protected void throwException(Throwable t) throws Exception {
        ManagedContextUtils.rethrowException(t);
        if (t instanceof Exception) {
            throw (Exception)t;
        }
    }

    public List<ManagedContextListener<?>> getListeners() {
        return listeners;
    }

    public void setListeners(List<ManagedContextListener<?>> listeners) {
        this.listeners = new CopyOnWriteArrayList<ManagedContextListener<?>>(listeners);
    }

    private static class ListenerInvocation {
        ManagedContextListener<Object> listener;
        Object data;

        public ListenerInvocation(ManagedContextListener<Object> listener, Object data) {
            super();
            this.listener = listener;
            this.data = data;
        }
    }
}
