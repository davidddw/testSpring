package com.yunshan.cloudstack.utils.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import com.yunshan.cloudstack.utils.PropertiesUtil;
import com.yunshan.cloudstack.utils.crypt.EncryptionSecretKeyChecker;

public class DbProperties {

    private static final Logger log = Logger.getLogger(DbProperties.class);

    private static Properties properties = new Properties();
    private static boolean loaded = false;

    protected static Properties wrapEncryption(Properties dbProps) throws IOException {
        EncryptionSecretKeyChecker checker = new EncryptionSecretKeyChecker();
        checker.check(dbProps);

        if (EncryptionSecretKeyChecker.useEncryption()) {
            return dbProps;
        } else {
            EncryptableProperties encrProps = new EncryptableProperties(EncryptionSecretKeyChecker.getEncryptor());
            encrProps.putAll(dbProps);
            return encrProps;
        }
    }

    public synchronized static Properties getDbProperties() {
        if (!loaded) {
            Properties dbProps = new Properties();
            InputStream is = null;
            try {
                File props = PropertiesUtil.findConfigFile("db.properties");
                if (props != null && props.exists()) {
                    is = new FileInputStream(props);
                }

                if (is == null) {
                    is = PropertiesUtil.openStreamFromURL("db.properties");
                }

                if (is == null) {
                    System.err.println("Failed to find db.properties");
                    log.error("Failed to find db.properties");
                }

                if (is != null) {
                    dbProps.load(is);
                }

                EncryptionSecretKeyChecker checker = new EncryptionSecretKeyChecker();
                checker.check(dbProps);

                if (EncryptionSecretKeyChecker.useEncryption()) {
                    StandardPBEStringEncryptor encryptor = EncryptionSecretKeyChecker.getEncryptor();
                    EncryptableProperties encrDbProps = new EncryptableProperties(encryptor);
                    encrDbProps.putAll(dbProps);
                    dbProps = encrDbProps;
                }
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load db.properties", e);
            } finally {
                IOUtils.closeQuietly(is);
            }

            properties = dbProps;
            loaded = true;
        }

        return properties;
    }

    public synchronized static Properties setDbProperties(Properties props) throws IOException {
        if (loaded) {
            throw new IllegalStateException("DbProperties has already been loaded");
        }
        properties = wrapEncryption(props);
        loaded = true;
        return properties;
    }
}
