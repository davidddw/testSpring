package com.yunshan.cloudstack.vmware.mo;

import java.util.List;

import org.apache.log4j.Logger;

import com.vmware.vim25.KeyAnyValue;
import com.vmware.vim25.KeyValue;
import com.vmware.vim25.LicenseAssignmentManagerLicenseAssignment;
import com.vmware.vim25.LicenseManagerLicenseInfo;
import com.vmware.vim25.ManagedObjectReference;

import com.yunshan.cloudstack.vmware.util.VmwareContext;


public class LicenseAssignmentManagerMO extends BaseMO {

    @SuppressWarnings("unused")
    private static final Logger s_logger = Logger.getLogger(LicenseAssignmentManagerMO.class);
    private static final String LICENSE_INFO_FEATURE = "feature";

    public LicenseAssignmentManagerMO(VmwareContext context, ManagedObjectReference mor) {
        super(context, mor);
    }

    public LicenseAssignmentManagerMO(VmwareContext context, String morType, String morValue) {
        super(context, morType, morValue);
    }

    public LicenseAssignmentManagerLicenseAssignment getAssignedLicenseToHost(ManagedObjectReference hostMor) throws Exception {
        List<LicenseAssignmentManagerLicenseAssignment> licenses = _context.getVimClient().getService().queryAssignedLicenses(_mor, hostMor.getValue());
        return licenses.get(0);
    }

    public boolean isFeatureSupported(String featureKey, ManagedObjectReference hostMor) throws Exception {
        boolean featureSupported = false;

        // Retrieve host license properties
        List<KeyAnyValue> props = getHostLicenseProperties(hostMor);

        // Check host license properties to see if specified feature is supported by the license.
        for (KeyAnyValue prop : props) {
            String key = prop.getKey();
            if (key.equalsIgnoreCase(LICENSE_INFO_FEATURE)) {
                KeyValue propValue = (KeyValue)prop.getValue();
                if (propValue.getKey().equalsIgnoreCase(featureKey)) {
                    featureSupported = true;
                    break;
                }
            }
        }

        return featureSupported;
    }

    public LicenseManagerLicenseInfo getHostLicenseInfo(ManagedObjectReference hostMor) throws Exception {
        // Retrieve license assigned to specified host
        LicenseAssignmentManagerLicenseAssignment license = getAssignedLicenseToHost(hostMor);
        return license.getAssignedLicense();
    }

    public List<KeyAnyValue> getHostLicenseProperties(ManagedObjectReference hostMor) throws Exception {
        return getHostLicenseInfo(hostMor).getProperties();
    }

    public String getHostLicenseName(ManagedObjectReference hostMor) throws Exception {
        return getHostLicenseInfo(hostMor).getName();
    }

}

