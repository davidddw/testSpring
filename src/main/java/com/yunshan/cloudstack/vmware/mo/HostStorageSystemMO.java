package com.yunshan.cloudstack.vmware.mo;

import java.util.List;

import com.vmware.vim25.HostInternetScsiHbaStaticTarget;
import com.vmware.vim25.HostStorageDeviceInfo;
import com.vmware.vim25.ManagedObjectReference;
import com.yunshan.cloudstack.vmware.util.VmwareContext;


public class HostStorageSystemMO extends BaseMO {
    public HostStorageSystemMO(VmwareContext context, ManagedObjectReference morHostDatastore) {
        super(context, morHostDatastore);
    }

    public HostStorageSystemMO(VmwareContext context, String morType, String morValue) {
        super(context, morType, morValue);
    }

    public HostStorageDeviceInfo getStorageDeviceInfo() throws Exception {
        return (HostStorageDeviceInfo)_context.getVimClient().getDynamicProperty(_mor, "storageDeviceInfo");
    }

    public void addInternetScsiStaticTargets(String iScsiHbaDevice, List<HostInternetScsiHbaStaticTarget> lstTargets) throws Exception {
        _context.getService().addInternetScsiStaticTargets(_mor, iScsiHbaDevice, lstTargets);
    }

    public void removeInternetScsiStaticTargets(String iScsiHbaDevice, List<HostInternetScsiHbaStaticTarget> lstTargets) throws Exception {
        _context.getService().removeInternetScsiStaticTargets(_mor, iScsiHbaDevice, lstTargets);
    }

    public void rescanHba(String iScsiHbaDevice) throws Exception {
        _context.getService().rescanHba(_mor, iScsiHbaDevice);
    }

    public void rescanVmfs() throws Exception {
        _context.getService().rescanVmfs(_mor);
    }
}
