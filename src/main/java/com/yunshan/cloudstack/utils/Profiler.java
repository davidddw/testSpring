package com.yunshan.cloudstack.utils;

public class Profiler {
    private Long startTickInMs;
    private Long stopTickInMs;

    public Profiler() {
        startTickInMs = null;
        stopTickInMs = null;
    }

    public long start() {
        startTickInMs = System.currentTimeMillis();
        return startTickInMs.longValue();
    }

    public long stop() {
        stopTickInMs = System.currentTimeMillis();
        return stopTickInMs.longValue();
    }

    public long getDuration() {
        if (startTickInMs != null && stopTickInMs != null)
            return stopTickInMs.longValue() - startTickInMs.longValue();

        return -1;
    }

    public boolean isStarted() {
        return startTickInMs != null;
    }

    public boolean isStopped() {
        return stopTickInMs != null;
    }

    @Override
    public String toString() {
        if (startTickInMs == null)
            return "Not Started";

        if (stopTickInMs == null)
            return "Started but not stopped";

        return "Done. Duration: " + getDuration() + "ms";
    }
}
