package com.yunshan.cloudstack.utils.concurrency;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {
    private int _number;
    private final String _name;

    public NamedThreadFactory(String name) {
        _name = name;
        _number = 1;
    }

    public synchronized Thread newThread(Runnable r) {
        return new Thread(r, _name + "-" + _number++);
    }

}
