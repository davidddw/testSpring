package com.yunshan.cloudstack.utils;

import java.util.Date;

public interface Vlan {
    public enum VlanType {
        DirectAttached, VirtualNetwork
    }

    public final static String UNTAGGED = "untagged";

    public String getVlanTag();

    public String getVlanGateway();

    public String getVlanNetmask();

    public long getDataCenterId();

    public String getIpRange();

    public VlanType getVlanType();

    public Long getNetworkId();

    public Date getRemoved();

    public Date getCreated();

    public Long getPhysicalNetworkId();

    public String getIp6Gateway();

    public String getIp6Cidr();

    public String getIp6Range();
}
