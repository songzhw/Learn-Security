package cn.six.sec.cert;

import cn.six.sec.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Created by hzsongzhengwang on 2016/1/7.
 */
public class Pkcs12CertKeyStoreTest {

    // test12.p12 : "keytool -genkey -v -alias szw -keyalg RSA -storetype PKCS12 -keystore test12.p12 -dname "CN=www.six.cn,OU=ipcc,O=MyCompany,L=MyCity,ST=MyProvice,C=China" -storepass 123qwe -keypass 123qwe"
    public static void main(String[] args) throws Exception {
        File file = new File("src/cn/six/sec/cert/test12.p12");
        InputStream is = new FileInputStream(file);

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(is, "123qwe".toCharArray()); // inputStream, password
        // if the password is wrong, an exception will occures : "javax.crypto.BadPaddingException: Given final block not properly padded"

        Enumeration<String> aliases = keyStore.aliases();
        while(aliases.hasMoreElements()) {
            String aalias = aliases.nextElement();
            boolean isCE =keyStore.isCertificateEntry(aalias);
            boolean isKE =keyStore.isKeyEntry(aalias);
            System.out.println("KeyStore isCe = "+isCE + " ; isKe = "+isKE); // => isKE

            // 从KeyStore中取出别名对应的证书链
            Certificate[] certs = keyStore.getCertificateChain(aalias);
            for(Certificate acert : certs) {
                X509Certificate cert = (X509Certificate) acert;
                System.out.println(" ================================================================================ ");
                System.out.println("SubjectDN = "+ cert.getSubjectDN().getName());  //=> SubjectDN = CN=www.six.cn, OU=ipcc, O=MyCompany, L=MyCity, ST=MyProvice, C=China
                System.out.println("IssuerDn = "+ cert.getIssuerDN().getName());   //=> SubjectDN = CN=www.six.cn, OU=ipcc, O=MyCompany, L=MyCity, ST=MyProvice, C=China
                System.out.println("publicKey = "+ Util.bytesToHexString(cert.getPublicKey().getEncoded()));  //publicKey = 30820122300D06092A864886F70D0101....
                System.out.println(" ================================================================================\n\n ");
            }


            // 由alias取出私钥或密钥
            Key key = keyStore.getKey(aalias, "123qwe".toCharArray());
            if(key instanceof PrivateKey){ // private key is much longer than public key
                System.out.println("privateKey = "+ Util.bytesToHexString(key.getEncoded())); // privateKey = 308204BC020100300D06092A864886F70D0101010500048204A630 ...
            }

        } // end of while

        is.close();
    }

}
