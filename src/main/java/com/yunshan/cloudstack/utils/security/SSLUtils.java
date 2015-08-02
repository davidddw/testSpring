package com.yunshan.cloudstack.utils.security;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;

public class SSLUtils {
    public static final Logger s_logger = Logger.getLogger(SSLUtils.class);

    public static String[] getSupportedProtocols(String[] protocols) {
        Set<String> set = new HashSet<String>();
        for (String s : protocols) {
            if (s.equals("SSLv3") || s.equals("SSLv2Hello")) {
                continue;
            }
            set.add(s);
        }
        return (String[]) set.toArray(new String[set.size()]);
    }

    public static String[] getSupportedCiphers() throws NoSuchAlgorithmException {
        String[] availableCiphers = getSSLContext().getSocketFactory().getSupportedCipherSuites();
        Arrays.sort(availableCiphers);
        return availableCiphers;
    }

    public static SSLContext getSSLContext() throws NoSuchAlgorithmException {
        return SSLContext.getInstance("TLSv1");
    }

    public static SSLContext getSSLContext(String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SSLContext.getInstance("TLSv1", provider);
    }
}
