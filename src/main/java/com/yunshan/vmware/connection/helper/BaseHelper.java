package com.yunshan.vmware.connection.helper;

import com.yunshan.vmware.connection.Connection;

public abstract class BaseHelper {
    final Connection connection;

    public BaseHelper(final Connection connection) {
        try {
            this.connection = connection.connect();
        } catch (Throwable t) {
            throw new HelperException(t);
        }
    }

    public class HelperException extends RuntimeException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public HelperException(Throwable cause) {
            super(cause);
        }
    }
}
