package cn.six.sec.key;

import cn.six.sec.Base64;
import cn.six.sec.Util;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by hzsongzhengwang on 2016/1/6.
 */
public class PairKeyTest {
    public String moduleStr, exponentStr;


    public void create() throws Exception {
        // 生成一对公私钥
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("\nszw createKey() publicKey = "+ Util.bytesToHexString(publicKey.getEncoded()));
//        System.out.println("\nszw createKey() privateKey = "+ Util.bytesToHexString(privateKey.getEncoded()));


        // 产生关键变量， 供使用方还原出公钥。 关键变量就是module, exponents两个。
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        BigInteger modules = publicKeySpec.getModulus();
        BigInteger exponents = publicKeySpec.getPublicExponent();
        byte[] moduleBytes = modules.toByteArray();
        byte[] exponentBytes = exponents.toByteArray();
        moduleStr = Base64.encodeToString(moduleBytes, Base64.DEFAULT);
        exponentStr = Base64.encodeToString(exponentBytes, Base64.DEFAULT);
    }

    public void verify(String moduleStr, String exponentStr) throws Exception {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(
                new BigInteger( Base64.decode(moduleStr, Base64.DEFAULT)   ),
                new BigInteger( Base64.decode(exponentStr, Base64.DEFAULT) )
                );

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        System.out.println("\nszw verifyKey() publicKey = "+ Util.bytesToHexString(publicKey.getEncoded()));
    }

    public static void main(String[] args)  throws Exception {
        PairKeyTest test = new PairKeyTest();
        test.create();
        test.verify(test.moduleStr, test.exponentStr);
    }
}
