package com.yunshan.cloudstack.utils.time;

public interface InaccurateClockMBean {
    String restart();

    String turnOff();

    long[] getCurrentTimes();
}
