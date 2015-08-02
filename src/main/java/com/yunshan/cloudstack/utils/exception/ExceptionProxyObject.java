package com.yunshan.cloudstack.utils.exception;

import java.io.Serializable;

public class ExceptionProxyObject implements Serializable {
    private static final long serialVersionUID = -7514266713085362352L;

    private String uuid;
    private String description;

    public ExceptionProxyObject(){

    }

    public ExceptionProxyObject(String uuid, String desc){
        this.uuid = uuid;
        description = desc;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
