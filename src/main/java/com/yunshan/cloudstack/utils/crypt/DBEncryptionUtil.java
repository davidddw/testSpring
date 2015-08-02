package com.yunshan.cloudstack.utils.crypt;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import com.yunshan.cloudstack.utils.db.DbProperties;
import com.yunshan.cloudstack.utils.exception.CloudRuntimeException;

public class DBEncryptionUtil {

    public static final Logger s_logger = Logger.getLogger(DBEncryptionUtil.class);
    private static StandardPBEStringEncryptor s_encryptor = null;

    public static String encrypt(String plain) {
        if (!EncryptionSecretKeyChecker.useEncryption() || (plain == null) || plain.isEmpty()) {
            return plain;
        }
        if (s_encryptor == null) {
            initialize();
        }
        String encryptedString = null;
        try {
            encryptedString = s_encryptor.encrypt(plain);
        } catch (EncryptionOperationNotPossibleException e) {
            s_logger.debug("Error while encrypting: " + plain);
            throw e;
        }
        return encryptedString;
    }

    public static String decrypt(String encrypted) {
        if (!EncryptionSecretKeyChecker.useEncryption() || (encrypted == null) || encrypted.isEmpty()) {
            return encrypted;
        }
        if (s_encryptor == null) {
            initialize();
        }

        String plain = null;
        try {
            plain = s_encryptor.decrypt(encrypted);
        } catch (EncryptionOperationNotPossibleException e) {
            s_logger.debug("Error while decrypting: " + encrypted);
            throw e;
        }
        return plain;
    }

    private static void initialize() {
        final Properties dbProps = DbProperties.getDbProperties();

        if (EncryptionSecretKeyChecker.useEncryption()) {
            String dbSecretKey = dbProps.getProperty("db.cloud.encrypt.secret");
            if (dbSecretKey == null || dbSecretKey.isEmpty()) {
                throw new CloudRuntimeException("Empty DB secret key in db.properties");
            }

            s_encryptor = new StandardPBEStringEncryptor();
            s_encryptor.setAlgorithm("PBEWithMD5AndDES");
            s_encryptor.setPassword(dbSecretKey);
        } else {
            throw new CloudRuntimeException("Trying to encrypt db values when encrytion is not enabled");
        }
    }
}
