package com.yunshan.cloudstack.vmware.mo;

import org.apache.log4j.Logger;

import com.vmware.vim25.ManagedObjectReference;

import com.yunshan.cloudstack.vmware.util.VmwareContext;


public class LicenseManagerMO extends BaseMO {

    @SuppressWarnings("unused")
    private static final Logger s_logger = Logger.getLogger(LicenseManagerMO.class);
    private ManagedObjectReference _licenseAssignmentManager = null;

    public LicenseManagerMO(VmwareContext context, ManagedObjectReference mor) {
        super(context, mor);
    }

    public LicenseManagerMO(VmwareContext context, String morType, String morValue) {
        super(context, morType, morValue);
    }

    public ManagedObjectReference getLicenseAssignmentManager() throws Exception {
        if (_licenseAssignmentManager == null) {
            _licenseAssignmentManager = (ManagedObjectReference)_context.getVimClient().getDynamicProperty(_mor, "licenseAssignmentManager");
        }
        return _licenseAssignmentManager;
    }
}
