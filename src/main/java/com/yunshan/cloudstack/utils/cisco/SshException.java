package com.yunshan.cloudstack.utils.cisco;

import com.yunshan.cloudstack.utils.SerialVersionUID;

public class SshException extends Exception {
    private static final long serialVersionUID = SerialVersionUID.sshException;

    public SshException(String msg) {
        super(msg);
    }
}
