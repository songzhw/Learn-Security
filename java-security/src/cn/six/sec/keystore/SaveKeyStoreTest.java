package cn.six.sec.keystore;

import cn.six.sec.Util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;

/**
 * Created by songzhw on 2016/1/16.
 *
 * reference : http://www.javacirecep.com/java-security/java-how-to-store-secret-keys-in-keystore/
 *
 */
public class SaveKeyStoreTest {

    // Note that, the KeyStore is stored and loaded with a password (lines 7 and 11) which provides an authenticated access to the managed keys.
    public static KeyStore createKeyStore(String fileName, String pwd) throws Exception {
        File file = new File(fileName);

        final KeyStore keyStore = KeyStore.getInstance("JCEKS");

        if(file.exists()){
            // .keystore file already exists ==> load it
            keyStore.load(new FileInputStream(file), pwd.toCharArray());
        } else {
            // .keystore file not created yet ==> create it
            file.createNewFile();
            keyStore.load(null, null);
            keyStore.store(new FileOutputStream(file), pwd.toCharArray());
        }

        return keyStore;
    }

    //Note that, in addition to the KeyStore password we assign an extra password (e.g. "aes_key_pwd")
    // to store and read the secret keys. This provides a further level of security.
    public static void createAndStore() throws Exception {
        String keyAlias = "MyKey01";
        String keystorePwd = "szwjavac";

        // 1. generate key store and key
        final String keystoreFilePath = "out/myks.keystore";
        KeyStore keyStore = SaveKeyStoreTest.createKeyStore(keystoreFilePath, keystorePwd);
        KeyStore.PasswordProtection keyPwd = new KeyStore.PasswordProtection("aes_key_pwd".toCharArray());


        // 2. save key
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        System.out.println("szw01 key = "+ Util.bytesToHexString(key.getEncoded()));

        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);
        keyStore.setEntry(keyAlias, secretKeyEntry, keyPwd);
        keyStore.store(new FileOutputStream(keystoreFilePath), keystorePwd.toCharArray());


        // 3. load key
        KeyStore.Entry entry = keyStore.getEntry(keyAlias, keyPwd);
        SecretKey keyFound = ((KeyStore.SecretKeyEntry)entry).getSecretKey();
        System.out.println("szw02 key = "+ Util.bytesToHexString(keyFound.getEncoded()));



    }

    public static void checkSaved() throws Exception{
        String keyAlias = "MyKey01";
        String keystorePwd = "szwjavac";

        // 1. generate key store and key
        final String keystoreFilePath = "out/myks.keystore";
        KeyStore keyStore = SaveKeyStoreTest.createKeyStore(keystoreFilePath, keystorePwd);
        KeyStore.PasswordProtection keyPwd = new KeyStore.PasswordProtection("aes_key_pwd".toCharArray());


        // 2. load key
        KeyStore.Entry entry = keyStore.getEntry(keyAlias, keyPwd);
        SecretKey keyFound = ((KeyStore.SecretKeyEntry)entry).getSecretKey();
        System.out.println("szw02 key = "+ Util.bytesToHexString(keyFound.getEncoded()));
    }

    public static void main(String[] args) throws Exception {
        createAndStore();

        System.out.println("=============================");
        checkSaved();
    }

}
