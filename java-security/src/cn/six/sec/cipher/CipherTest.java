package cn.six.sec.cipher;

import cn.six.sec.Base64;
import cn.six.sec.Util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hzsongzhengwang on 2016/1/8.
 */
public class CipherTest {
    private static byte[] iv = {1,2,3,4,5,6,7,8};  // 初始化向量。 用于生成Cipher的
    private String keyStr, encryptedStr; // 给外部使用者的

    public static void main(String[] args) throws Exception {
        String plainText = "programming is fun";
        CipherTest test = new CipherTest();
        test.encrypt(plainText);
        test.decrypt();
    }

    public void encrypt(String plainText) throws Exception {

        SecretKey key = KeyGenerator.getInstance("DES").generateKey();

        // 加密
        Cipher encryptor = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptor.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedBytes = encryptor.doFinal(plainText.getBytes());
        System.out.println("encrypted = "+ Util.bytesToHexString(encryptedBytes)); //=> 8D420B105827767959E7B51F444AEA547BDDF3A84C53C6F9. 此值每次运行都不一样，可见Key不见得总是一样。

        // 把密文、关键IV， KEY传给外部使用者
        keyStr = Base64.encodeToString(key.getEncoded(), Base64.DEFAULT); //这个Base64就是Android平台的Base64
        encryptedStr = Base64.encodeToString( encryptedBytes , Base64.DEFAULT);
    }

    public void decrypt() throws Exception {
        // 还原出 key, 以及密文对应的byte[]
        byte[] encryptedBytes = Base64.decode(encryptedStr, Base64.DEFAULT);
        SecretKeySpec key = new SecretKeySpec( Base64.decode(keyStr, Base64.DEFAULT), "DES");

        Cipher decryptor = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptor.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decryptedBytes = decryptor.doFinal(encryptedBytes);
        System.out.println("decrypted = " + new String(decryptedBytes)); //=>  programming is fun
    }

}
