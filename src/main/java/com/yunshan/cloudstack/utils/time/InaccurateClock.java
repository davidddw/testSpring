package com.yunshan.cloudstack.utils.time;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.management.StandardMBean;

import org.apache.log4j.Logger;

import com.yunshan.cloudstack.utils.concurrency.NamedThreadFactory;
import com.yunshan.cloudstack.utils.mgmt.JmxUtil;



/**
 */

public class InaccurateClock extends StandardMBean implements InaccurateClockMBean {
    private static final Logger s_logger = Logger.getLogger(InaccurateClock.class);
    static ScheduledExecutorService s_executor = null;
    static final InaccurateClock s_timer = new InaccurateClock();
    private static long time;

    public InaccurateClock() {
        super(InaccurateClockMBean.class, false);
        time = System.currentTimeMillis();
        restart();
        try {
            JmxUtil.registerMBean("InaccurateClock", "InaccurateClock", this);
        } catch (Exception e) {
            s_logger.warn("Unable to initialize inaccurate clock", e);
        }
    }

    public long[] getCurrentTimes() {
        long[] results = new long[2];
        results[0] = time;
        results[1] = System.currentTimeMillis();

        return results;
    }

    public synchronized String restart() {
        turnOff();
        s_executor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("InaccurateClock"));
        s_executor.scheduleAtFixedRate(new SetTimeTask(), 0, 60, TimeUnit.SECONDS);
        return "Restarted";
    }

    public String turnOff() {
        if (s_executor != null) {
            try {
                s_executor.shutdown();
            } catch (Throwable th) {
                s_logger.error("Unable to shutdown the Executor", th);
                return "Unable to turn off check logs";
            }
        }
        s_executor = null;
        return "Off";
    }

    public static long getTime() {
        return s_executor != null ? time : System.currentTimeMillis();
    }

    public static long getTimeInSeconds() {
        return time / 1000;
    }

    protected class SetTimeTask implements Runnable {
        @Override
        public void run() {
            try {
                time = System.currentTimeMillis();
            } catch (Throwable th) {
                try {
                    s_logger.error("Unable to time", th);
                } catch (Throwable th2) {
                }
            }
        }
    }
}
