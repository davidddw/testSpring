package com.yunshan.cloudstack.utils.exception;

import java.util.ArrayList;

public class CloudException extends Exception {
    private static final long serialVersionUID = 8784427323859682503L;

    // This holds a list of uuids and their names. Add uuid:fieldname pairs
    protected ArrayList<String> idList = new ArrayList<String>();

    protected Integer csErrorCode;

    public CloudException(String message) {
        super(message);
        setCSErrorCode(CSExceptionErrorCode.getCSErrCode(this.getClass().getName()));
    }

    public CloudException(String message, Throwable cause) {
        super(message, cause);
        setCSErrorCode(CSExceptionErrorCode.getCSErrCode(this.getClass().getName()));
    }

    public CloudException() {
        super();
        setCSErrorCode(CSExceptionErrorCode.getCSErrCode(this.getClass().getName()));
    }

    public void addProxyObject(String uuid) {
        idList.add(uuid);
        return;
    }

    public ArrayList<String> getIdProxyList() {
        return idList;
    }

    public void setCSErrorCode(int cserrcode) {
        csErrorCode = cserrcode;
    }

    public int getCSErrorCode() {
        return csErrorCode;
    }
}
