package cn.six.sec.cipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SimpleAES {
    public byte[] encrypt(byte[] keyBytes, String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    public byte[] decrypt(byte[] keyBytes, byte[] encrypted) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }

    public static void main(String[] args) throws Exception {
        SimpleAES aes = new SimpleAES();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        byte[] bytes = aes.encrypt(secretKey.getEncoded(), "I'm value");
        System.out.println("szw result1 = " + new String(bytes));    //=> �k*ie�{߼p�}

        byte[] decrypted = aes.decrypt(secretKey.getEncoded(), bytes);
        System.out.println("szw result2 = " + new String(decrypted)); //=> I'm value

    }
}
