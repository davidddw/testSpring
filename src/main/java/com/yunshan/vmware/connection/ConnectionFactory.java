package com.yunshan.vmware.connection;

/**
 * Holds the logic for how to construct a connection class. We allow the
 * specific implementation of the connection class to change underneath
 * the Connection interface. The specific implementation
 */

public class ConnectionFactory {
    /**
     * Constructs a connection class based on the system property
     * com.vmware.connection.Connection=some.class.name
     *
     * @return instance of a class that implements the Connection interface
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
	public static Connection newConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String connectionClassName = System.getProperty(
                Connection.class.getCanonicalName()
                // makes the default the SsoConnection class
        );

        Class<?> connectionClass = Class.forName(connectionClassName);
        return (Connection) connectionClass.newInstance();
    }
}
