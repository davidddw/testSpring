package com.yunshan.cloudstack.vmware.mo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunshan.cloudstack.utils.DatastoreFile;

public class VirtualMachineDiskInfoBuilder {
    Map<String, List<String>> disks;

    public VirtualMachineDiskInfoBuilder() {
        disks = new HashMap<String, List<String>>();
    }

    public void addDisk(String diskDeviceBusName, String diskBackingFilePath) {
        List<String> chain = getDiskChainContainer(diskDeviceBusName);
        chain.add(diskBackingFilePath);
    }

    public int getDiskCount() {
        return disks.keySet().size();
    }

    public List<VirtualMachineDiskInfo> getAllDiskInfo() {
        List<VirtualMachineDiskInfo> infoList = new ArrayList<VirtualMachineDiskInfo>();
        for (Map.Entry<String, List<String>> entry : disks.entrySet()) {
            VirtualMachineDiskInfo diskInfo = new VirtualMachineDiskInfo();
            diskInfo.setDiskDeviceBusName(entry.getKey());
            diskInfo.setDiskChain(entry.getValue().toArray(new String[1]));
            infoList.add(diskInfo);
        }
        return infoList;
    }

    public VirtualMachineDiskInfo getDiskInfoByDeviceBusName(String diskDeviceBusName) {
        List<String> chain = disks.get(diskDeviceBusName);
        if (chain != null && chain.size() > 0) {
            VirtualMachineDiskInfo diskInfo = new VirtualMachineDiskInfo();
            diskInfo.setDiskDeviceBusName(diskDeviceBusName);
            diskInfo.setDiskChain(chain.toArray(new String[1]));
            return diskInfo;
        }

        return null;
    }

    public VirtualMachineDiskInfo getDiskInfoByBackingFileBaseName(String diskBackingFileBaseName, String dataStoreName) {
        for (Map.Entry<String, List<String>> entry : disks.entrySet()) {
            if (chainContains(entry.getValue(), diskBackingFileBaseName, dataStoreName)) {
                VirtualMachineDiskInfo diskInfo = new VirtualMachineDiskInfo();
                diskInfo.setDiskDeviceBusName(entry.getKey());
                diskInfo.setDiskChain(entry.getValue().toArray(new String[1]));
                return diskInfo;
            }
        }

        return null;
    }

    private List<String> getDiskChainContainer(String diskDeviceBusName) {
        assert (diskDeviceBusName != null);
        List<String> chain = disks.get(diskDeviceBusName);
        if (chain == null) {
            chain = new ArrayList<String>();
            disks.put(diskDeviceBusName, chain);
        }
        return chain;
    }

    private static boolean chainContains(List<String> chain, String diskBackingFileBaseName, String dataStoreName) {
        for (String backing : chain) {
            DatastoreFile file = new DatastoreFile(backing);
            // Ensure matching disk exists in the right datastore
            if (file.getFileBaseName().equals(diskBackingFileBaseName) && backing.contains(dataStoreName))
                return true;
        }

        return false;
    }
}
