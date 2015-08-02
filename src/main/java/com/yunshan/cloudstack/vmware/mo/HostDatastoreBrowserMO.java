package com.yunshan.cloudstack.vmware.mo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vmware.vim25.HostDatastoreBrowserSearchResults;
import com.vmware.vim25.HostDatastoreBrowserSearchSpec;
import com.vmware.vim25.ManagedObjectReference;
import com.yunshan.cloudstack.vmware.util.VmwareClient;
import com.yunshan.cloudstack.vmware.util.VmwareContext;

public class HostDatastoreBrowserMO extends BaseMO {

    private static final Logger s_logger = Logger.getLogger(HostDatastoreBrowserMO.class);

    public HostDatastoreBrowserMO(VmwareContext context, ManagedObjectReference morHostDatastoreBrowser) {
        super(context, morHostDatastoreBrowser);
    }

    public HostDatastoreBrowserMO(VmwareContext context, String morType, String morValue) {
        super(context, morType, morValue);
    }
    
	public static void main(String[] args) throws Exception {
		VmwareClient vmware = new VmwareClient("172.16.1.111");
		vmware.connect("https://172.16.1.140/sdk", "root", "vmware");
		System.out.println(vmware.getRootFolder());
		vmware.disconnect();
	}

    public void DeleteFile(String datastoreFullPath) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - deleteFile(). target mor: " + _mor.getValue() + ", file datastore path: " + datastoreFullPath);

        _context.getService().deleteFile(_mor, datastoreFullPath);

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - deleteFile() done");
    }

    public HostDatastoreBrowserSearchResults searchDatastore(String datastorePath, HostDatastoreBrowserSearchSpec searchSpec) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - searchDatastore(). target mor: " + _mor.getValue() + ", file datastore path: " + datastorePath);

        try {
            ManagedObjectReference morTask = _context.getService().searchDatastoreTask(_mor, datastorePath, searchSpec);

            boolean result = _context.getVimClient().waitForTask(morTask);
            if (result) {
                _context.waitForTaskProgressDone(morTask);

                return (HostDatastoreBrowserSearchResults)_context.getVimClient().getDynamicProperty(morTask, "info.result");
            } else {
                s_logger.error("VMware searchDaastore_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }
        } finally {
            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - searchDatastore() done");
        }

        return null;
    }

    public HostDatastoreBrowserSearchResults searchDatastore(String datastorePath, String fileName, boolean caseInsensitive) throws Exception {
        HostDatastoreBrowserSearchSpec spec = new HostDatastoreBrowserSearchSpec();
        spec.setSearchCaseInsensitive(caseInsensitive);
        spec.getMatchPattern().add(fileName);

        return searchDatastore(datastorePath, spec);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<HostDatastoreBrowserSearchResults> searchDatastoreSubFolders(String datastorePath, HostDatastoreBrowserSearchSpec searchSpec) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - searchDatastoreSubFolders(). target mor: " + _mor.getValue() + ", file datastore path: " + datastorePath);

        try {
            ManagedObjectReference morTask = _context.getService().searchDatastoreSubFoldersTask(_mor, datastorePath, searchSpec);

            boolean result = _context.getVimClient().waitForTask(morTask);
            if (result) {
                _context.waitForTaskProgressDone(morTask);

                return (ArrayList<HostDatastoreBrowserSearchResults>)_context.getVimClient().getDynamicProperty(morTask, "info.result");
            } else {
                s_logger.error("VMware searchDaastoreSubFolders_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }
        } finally {
            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - searchDatastore() done");
        }

        return null;
    }
    
    public ArrayList<HostDatastoreBrowserSearchResults> searchDatastoreSubFolders(String datastorePath, String fileName, boolean caseInsensitive) throws Exception {
        HostDatastoreBrowserSearchSpec spec = new HostDatastoreBrowserSearchSpec();
        spec.setSearchCaseInsensitive(caseInsensitive);
        spec.getMatchPattern().add(fileName);

        return searchDatastoreSubFolders(datastorePath, spec);
    }
}

