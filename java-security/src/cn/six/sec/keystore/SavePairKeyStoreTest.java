package cn.six.sec.keystore;

import cn.six.sec.Util;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;

/**
 * Created by Administrator on 2016/1/16.
 */
public class SavePairKeyStoreTest {

    public static void main(String[] args) throws Exception {

        CertAndKeyGen certKeyGen = new CertAndKeyGen("RSA","SHA256WithRSA",null);//algorithm, signrature algorithm, provider
        certKeyGen.generate(2048);   // 2048 is keySize

        X500Name x500Name = new X500Name("CN=SavePair,O=six,L=hz,C=DE")  ;
        long validSeconds = (long) 365 * 24 * 60 * 60; //valid for one year
        X509Certificate cert = certKeyGen.getSelfCertificate(x500Name, validSeconds);

        File file = new File("out/myks.keystore");
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(new FileInputStream(file), "szwjavac".toCharArray());

        keyStore.setKeyEntry("certAlias",
                certKeyGen.getPrivateKey(),
                "key_s_pwd".toCharArray(),
                new X509Certificate[]{cert}
        );
        keyStore.store(new FileOutputStream(file), "szwjavac".toCharArray() );
        // 只setKeyEntry()， 不store()， 那么其实相当于在word上修改了，并不保存就直接强制关闭一样。
        // 这样子，KeyStore实际上是没有存住这个证书的！



        // load the cert
        Certificate cert2 =  keyStore.getCertificate("certAlias");
        X509Certificate cert2_2 = (X509Certificate) cert2;



        // get the private key
        KeyStore.Entry rawEntry = keyStore.getEntry("certAlias",
                new KeyStore.PasswordProtection("key_s_pwd".toCharArray()));
        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) rawEntry;
        PrivateKey privateKey = entry.getPrivateKey();
        X509Certificate cert3 = (X509Certificate) entry.getCertificate();

        System.out.println("szw 01 SubjectDN = "+ cert2_2.getSubjectDN().getName());
        System.out.println("szw 02 SubjectDN = "+ cert3.getSubjectDN().getName());

        System.out.println("\nszw 03 key = "+ Util.bytesToHexString(certKeyGen.getPrivateKey().getEncoded()));
        System.out.println("szw 04 key = "+ Util.bytesToHexString(privateKey.getEncoded()));

    }

}
