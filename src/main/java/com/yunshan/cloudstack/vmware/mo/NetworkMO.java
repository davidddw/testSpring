package com.yunshan.cloudstack.vmware.mo;

import java.util.List;

import com.vmware.vim25.ManagedObjectReference;

import com.yunshan.cloudstack.vmware.util.VmwareContext;

public class NetworkMO extends BaseMO {
    public NetworkMO(VmwareContext context, ManagedObjectReference morCluster) {
        super(context, morCluster);
    }

    public NetworkMO(VmwareContext context, String morType, String morValue) {
        super(context, morType, morValue);
    }

    public void destroyNetwork() throws Exception {
        _context.getService().destroyNetwork(_mor);
    }

    public List<ManagedObjectReference> getVMsOnNetwork() throws Exception {
        return _context.getVimClient().getDynamicProperty(_mor, "vm");
    }
}
