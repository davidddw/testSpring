package com.yunshan.cloudstack.vmware.mo;

public enum VirtualSwitchType {
    None, StandardVirtualSwitch, VMwareDistributedVirtualSwitch, NexusDistributedVirtualSwitch;

    public final static String vmwareStandardVirtualSwitch = "vmwaresvs";
    public final static String vmwareDistributedVirtualSwitch = "vmwaredvs";
    public final static String nexusDistributedVirtualSwitch = "nexusdvs";

    public static VirtualSwitchType getType(String vSwitchType) {
        if (vSwitchType == null || vSwitchType.equalsIgnoreCase(vmwareStandardVirtualSwitch)) {
            return VirtualSwitchType.StandardVirtualSwitch;
        } else if (vSwitchType.equalsIgnoreCase(vmwareDistributedVirtualSwitch)) {
            return VirtualSwitchType.VMwareDistributedVirtualSwitch;
        } else if (vSwitchType.equalsIgnoreCase(nexusDistributedVirtualSwitch)) {
            return VirtualSwitchType.NexusDistributedVirtualSwitch;
        }
        return VirtualSwitchType.None;
    }

    @Override
    public String toString() {
        if (this == VirtualSwitchType.StandardVirtualSwitch) {
            return vmwareStandardVirtualSwitch;
        } else if (this == VirtualSwitchType.VMwareDistributedVirtualSwitch) {
            return vmwareDistributedVirtualSwitch;
        } else if (this == VirtualSwitchType.NexusDistributedVirtualSwitch) {
            return nexusDistributedVirtualSwitch;
        }
        return "";
    }
}
