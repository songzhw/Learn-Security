package cn.six.sec.sign;

import cn.six.sec.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;

/**
 * Created by hzsongzhengwang on 2016/1/8.
 */
public class SignTest {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private byte[] signedResult;

    // test12.p12 : "keytool -genkey -v -alias szw -keyalg RSA -storetype PKCS12 -keystore test12.p12 -dname "CN=www.six.cn,OU=ipcc,O=MyCompany,L=MyCity,ST=MyProvice,C=China" -storepass 123qwe -keypass 123qwe"
    public static void main(String[] args) throws Exception{

        SignTest test = new SignTest();
        test.getKeyPair();
        test.sign();
        test.verify();

    }

    public void getKeyPair()  throws Exception{
        File file = new File("src/cn/six/sec/cert/test12.p12");
        InputStream is = new FileInputStream(file);

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(is, "123qwe".toCharArray());

        String alias = "szw";
        Certificate cert = keyStore.getCertificate(alias);
        publicKey = cert.getPublicKey();
        privateKey = (PrivateKey) keyStore.getKey(alias, "123qwe".toCharArray());
        is.close();
    }

    // 对bbb.cer进行签名
    public void sign()  throws Exception{
        File file = new File("src/cn/six/sec/cert/bbb.cer");
        InputStream is = new FileInputStream(file);


        Signature sign = Signature.getInstance("MD5withRSA");
        sign.initSign(privateKey);
        byte[] data = new byte[1024];
        int nread = 0;
        while((nread = is.read(data))>0){
            sign.update(data, 0, nread);
        }
        signedResult = sign.sign();
        System.out.println("001 sign = "+ Util.bytesToHexString(signedResult));
        is.close();
    }

    public void verify()  throws Exception{
        // -------------------------------------------------------------------------------

        File file2 = new File("src/cn/six/sec/cert/bbb.cer");
        InputStream is2 = new FileInputStream(file2);
        Signature sign2 = Signature.getInstance("MD5WithRSA");

        // 校验签名是需要初始化， 并在初始化时要传入公钥来验证的
        sign2.initVerify(publicKey);
        byte[] data = new byte[1024];
        int nread = 0;
        while((nread =is2.read(data))>0){
            sign2.update(data, 0, nread);
        }
        boolean isSignCorrect = sign2.verify(signedResult);
        System.out.println("002 isCorrect = "+isSignCorrect);

        is2.close();
    }

}
