package cn.six.sec.key;


import cn.six.sec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hzsongzhengwang on 2016/1/6.
 */
public class SynmmetricKeyTest {

    public String createKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);  // java在非56时，会出错并提示“ Wrong keysize: must be equal to 56 ”
        SecretKey secretKey = keyGen.generateKey();
        byte[] key16 = secretKey.getEncoded();
        String key16InBase64 = Base64.encodeToString(key16, Base64.DEFAULT);
        System.out.println("szw createKey() : keyStr = "+key16InBase64);
        return key16InBase64;
    }

    public void verifyKey(String keyStr) throws Exception {
        byte[] key16 = Base64.decode(keyStr, Base64.DEFAULT);
        SecretKeySpec key = new SecretKeySpec(key16, "DES");  // SecretKeySpec is a subclass of the Key class
        System.out.println("szw verifyKey() : keyStr = "+ Base64.encodeToString(key16, Base64.DEFAULT) );
    }

    public static void main(String[] args) throws Exception {
        SynmmetricKeyTest test = new SynmmetricKeyTest();
        String keyStr = test.createKey();
        test.verifyKey(keyStr);
    }

}
