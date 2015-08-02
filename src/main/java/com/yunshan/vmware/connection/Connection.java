package com.yunshan.vmware.connection;

import java.net.URL;
import java.util.Map;

import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ServiceContent;
import com.vmware.vim25.UserSession;
import com.vmware.vim25.VimPortType;
import com.vmware.vim25.VimService;

public interface Connection {
    // getters and setters
    void setUrl(String url);

    String getUrl();

    String getHost();

    Integer getPort();

    void setUsername(String username);

    String getUsername();

    void setPassword(String password);

    String getPassword();

    VimService getVimService();

    VimPortType getVimPort();

    ServiceContent getServiceContent();

    UserSession getUserSession();

    String getServiceInstanceName();

    @SuppressWarnings("rawtypes")
	Map getHeaders();

    ManagedObjectReference getServiceInstanceReference();

    Connection connect();

    boolean isConnected();

    Connection disconnect();

    URL getURL();
}
