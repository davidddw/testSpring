package com.yunshan.cloudstack.utils.exception;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.yunshan.cloudstack.utils.Pair;
import com.yunshan.cloudstack.utils.SerialVersionUID;

public class CloudRuntimeException extends RuntimeException implements ErrorContext {

    private static final long serialVersionUID = SerialVersionUID.CloudRuntimeException;

    // This holds a list of uuids and their descriptive names.
    transient protected ArrayList<ExceptionProxyObject> idList = new ArrayList<ExceptionProxyObject>();

    transient protected ArrayList<Pair<Class<?>, String>> uuidList = new ArrayList<Pair<Class<?>, String>>();

    protected int csErrorCode;

    public CloudRuntimeException(String message) {
        super(message);
        setCSErrorCode(CSExceptionErrorCode.getCSErrCode(this.getClass().getName()));
    }

    public CloudRuntimeException(String message, Throwable th) {
        super(message, th);
        setCSErrorCode(CSExceptionErrorCode.getCSErrCode(this.getClass().getName()));
    }

    protected CloudRuntimeException() {
        super();

        setCSErrorCode(CSExceptionErrorCode.getCSErrCode(this.getClass().getName()));
    }

    public void addProxyObject(ExceptionProxyObject obj) {
        idList.add(obj);
    }

    public void addProxyObject(String uuid) {
        idList.add(new ExceptionProxyObject(uuid, null));
    }

    public void addProxyObject(String voObjUuid, String description) {
        ExceptionProxyObject proxy = new ExceptionProxyObject(voObjUuid, description);
        idList.add(proxy);
    }

    public CloudRuntimeException add(Class<?> entity, String uuid) {
        uuidList.add(new Pair<Class<?>, String>(entity, uuid));
        return this;
    }

    public ArrayList<ExceptionProxyObject> getIdProxyList() {
        return idList;
    }

    public void setCSErrorCode(int cserrcode) {
        csErrorCode = cserrcode;
    }

    public int getCSErrorCode() {
        return csErrorCode;
    }

    public CloudRuntimeException(Throwable t) {
        super(t.getMessage(), t);
    }

    public List<Pair<Class<?>, String>> getEntitiesInError() {
        return uuidList;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        int idListSize = idList.size();
        out.writeInt(idListSize);
        for (ExceptionProxyObject proxy : idList) {
            out.writeObject(proxy);
        }

        int uuidListSize = uuidList.size();
        out.writeInt(uuidListSize);
        for (Pair<Class<?>, String> entry : uuidList) {
            out.writeObject(entry.first().getCanonicalName());
            out.writeObject(entry.second());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int idListSize = in.readInt();
        if (idList == null)
            idList = new ArrayList<ExceptionProxyObject>();
        if (uuidList == null)
            uuidList = new ArrayList<Pair<Class<?>, String>>();

        for (int i = 0; i < idListSize; i++) {
            ExceptionProxyObject proxy = (ExceptionProxyObject)in.readObject();

            idList.add(proxy);
        }

        int uuidListSize = in.readInt();
        for (int i = 0; i < uuidListSize; i++) {
            String clzName = (String)in.readObject();
            String val = (String)in.readObject();

            uuidList.add(new Pair<Class<?>, String>(Class.forName(clzName), val));
        }
    }
}
