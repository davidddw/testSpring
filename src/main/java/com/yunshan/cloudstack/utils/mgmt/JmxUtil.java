package com.yunshan.cloudstack.utils.mgmt;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class JmxUtil {
    public static ObjectName registerMBean(ManagementBean mbean) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException,
        NotCompliantMBeanException {

        return registerMBean(mbean.getName(), null, mbean);
    }

    public static ObjectName registerMBean(String objTypeName, String objInstanceName, Object mbean) throws MalformedObjectNameException, InstanceAlreadyExistsException,
        MBeanRegistrationException, NotCompliantMBeanException {

        String name = "com.cloud:type=" + objTypeName;
        if (objInstanceName != null && !objInstanceName.isEmpty())
            name += ", name=" + objInstanceName;
        ObjectName objectName = new ObjectName(name);

        ArrayList<MBeanServer> server = MBeanServerFactory.findMBeanServer(null);
        if (server.size() > 0) {
            MBeanServer mBeanServer = server.get(0);
            if (!mBeanServer.isRegistered(objectName))
                mBeanServer.registerMBean(mbean, objectName);
            return objectName;
        } else {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            if (!mBeanServer.isRegistered(objectName))
                mBeanServer.registerMBean(mbean, objectName);
            return objectName;
        }
    }

    public static void unregisterMBean(String objTypeName, String objInstanceName) throws MalformedObjectNameException, MBeanRegistrationException,
        InstanceNotFoundException {

        ObjectName name = composeMBeanName(objTypeName, objInstanceName);
        unregisterMBean(name);
    }

    public static void unregisterMBean(ObjectName name) throws MalformedObjectNameException, MBeanRegistrationException, InstanceNotFoundException {

        ArrayList<MBeanServer> server = MBeanServerFactory.findMBeanServer(null);
        if (server.size() > 0) {
            MBeanServer mBeanServer = server.get(0);
            mBeanServer.unregisterMBean(name);
        } else {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.unregisterMBean(name);
        }
    }

    private static ObjectName composeMBeanName(String objTypeName, String objInstanceName) throws MalformedObjectNameException {

        String name = "com.cloud:type=" + objTypeName;
        if (objInstanceName != null && !objInstanceName.isEmpty())
            name += ", name=" + objInstanceName;

        return new ObjectName(name);
    }
}
