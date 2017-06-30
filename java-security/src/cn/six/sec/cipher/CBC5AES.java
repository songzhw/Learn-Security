package cn.six.sec.cipher;

import cn.six.sec.Base64;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 Other things to keep in mind:

 Always use a fully qualified Cipher name. AES is not appropriate in such a case, because different JVMs/JCE providers may use different defaults for mode of operation and padding. Use AES/CBC/PKCS5Padding. Don't use ECB mode, because it is not semantically secure.
 If you don't use ECB mode then you need to send the IV along with the ciphertext. This is usually done by prefixing the IV to the ciphertext byte array. The IV is automatically created for you and you can get it through cipherInstance.getIV().
 Whenever you send something, you need to be sure that it wasn't altered along the way. It is hard to implement a encryption with MAC correctly. I recommend you to use an authenticated mode like CCM or GCM.
 */

public class CBC5AES {
    private static final String HASH_ALGORITHM = "SHA-128";

    private SecretKey getKey(String password) throws Exception {
//        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
//        return key;

        final MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();

        return new SecretKeySpec(key, "AES");
    }

    private IvParameterSpec getIv() {
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);
//        byte[] ivBytes = "1234567890abcdef".getBytes();
        return new IvParameterSpec(ivBytes);
    }

    public String encrypt(String password, String data) throws Exception {
        Key key = getKey(password);
        IvParameterSpec iv = getIv();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }

    public String decrypt(String password, String cipherText) throws Exception {
        Key key = getKey(password);
        IvParameterSpec iv = getIv();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decodedCipherBytes = Base64.decode(cipherText, Base64.NO_WRAP);
        byte[] decrpyted = cipher.doFinal(decodedCipherBytes);
        return new String(decrpyted, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        CBC5AES aes = new CBC5AES();
        String cipherText = aes.encrypt("apple", "I'm Orange~!");
        System.out.println("szw 01 = " + cipherText);
        String plain = aes.decrypt("apple", cipherText);
        System.out.println("szw 02 = " + plain);
    }
}
