package com.yunshan.cloudstack.utils.net;

import java.io.Serializable;

import com.yunshan.cloudstack.utils.NumbersUtil;
import com.yunshan.cloudstack.utils.SerialVersionUID;

public class Ip implements Serializable, Comparable<Ip> {

    private static final long serialVersionUID = SerialVersionUID.Ip;

    long ip;

    public Ip(long ip) {
        this.ip = ip;
    }

    public Ip(String ip) {
        this.ip = NetUtils.ip2Long(ip);
    }

    protected Ip() {
    }

    public String addr() {
        return toString();
    }

    public long longValue() {
        return ip;
    }

    @Override
    public String toString() {
        return NetUtils.long2Ip(ip);
    }

    public boolean isIp4() {
        return ip <= 2L * Integer.MAX_VALUE + 1;
    }

    public boolean isIp6() {
        return ip > Integer.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return NumbersUtil.hash(ip);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ip) {
            return ip == ((Ip)obj).ip;
        } else if (obj instanceof String) {
            return ip == NetUtils.ip2Long((String)obj);
        } else if (obj instanceof Long) {
            return ip == (Long)obj;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Ip that) {
        return (int)(this.ip - that.ip);
    }
}
