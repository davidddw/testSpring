package com.yunshan.cloudstack.vmware.mo;

public class VirtualMachineDiskInfo {
    String diskDeviceBusName;
    String[] diskChain;

    public VirtualMachineDiskInfo() {
    }

    public String getDiskDeviceBusName() {
        return diskDeviceBusName;
    }

    public void setDiskDeviceBusName(String diskDeviceBusName) {
        this.diskDeviceBusName = diskDeviceBusName;
    }

    public String[] getDiskChain() {
        return diskChain;
    }

    public void setDiskChain(String[] diskChain) {
        this.diskChain = diskChain;
    }
}

