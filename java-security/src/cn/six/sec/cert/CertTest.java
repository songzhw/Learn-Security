package cn.six.sec.cert;



import cn.six.sec.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by hzsongzhengwang on 2016/1/6.
 */
public class CertTest {

    public static void main(String[] args) throws Exception {
        File file = new File("src/cn/six/sec/cert/bbb.cer");
        InputStream is = new FileInputStream(file);

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Cert = (X509Certificate) certFactory.generateCertificate(is);
        System.out.println("SubjectDN = "+ x509Cert.getSubjectDN().getName());  // CN=*.alipay.com, OU=Operations Department, O="Alipay.com Co.,Ltd", L=HANGZHOU, ST=ZHEJIANG, C=CN
        System.out.println("IssuerDn = "+ x509Cert.getIssuerDN().getName());   // CN=Symantec Class 3 Secure Server CA - G4, OU=Symantec Trust Network, O=Symantec Corporation, C=US
        System.out.println("publicKey = "+ Util.bytesToHexString(x509Cert.getPublicKey().getEncoded()));  //publicKey = 30820122300D06092A864886F70D0101010500............

        is.close();

    }
}
