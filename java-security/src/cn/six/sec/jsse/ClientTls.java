package cn.six.sec.jsse;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;

/**
 * Created by hzsongzhengwang on 2016/1/9.
 */
public class ClientTls {

    public static void main(String[] args) throws Exception {

        // the private key and cert is actually in this test12.p12 file
        File file = new File("src/cn/six/sec/cert/test12.p12");
        InputStream is = new FileInputStream(file);

        KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
        clientKeyStore.load(is, "123qwe".toCharArray());
        is.close();

        TrustManagerFactory tmf = TrustManagerFactory.getInstance( TrustManagerFactory.getDefaultAlgorithm() );
        tmf.init(clientKeyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        // 连接服务器
        InetAddress addr = Inet4Address.getLocalHost();
        Socket clientSocket = sslSocketFactory.createSocket(addr, 15000);
        // clientSocket.getOutputStreeam(), ...

    }

}
