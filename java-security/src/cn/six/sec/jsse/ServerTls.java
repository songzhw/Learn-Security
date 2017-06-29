package cn.six.sec.jsse;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.KeyStore;

/**
 * Created by hzsongzhengwang on 2016/1/9.
 */
public class ServerTls {

    public static void main(String[] args) throws Exception{

        // the private key and cert is actually in this test12.p12 file
        File file = new File("src/cn/six/sec/cert/test12.p12");
        InputStream is = new FileInputStream(file);

        KeyStore serverKeyStore = KeyStore.getInstance("PKCS12");
        serverKeyStore.load(is, "123qwe".toCharArray());
        is.close();

        KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance( KeyManagerFactory.getDefaultAlgorithm() );
        keyMgrFactory.init(serverKeyStore, "123qwe".toCharArray());

        // 我们要用这个keystore来初始化一个SSLContext对象。SSLContext使得我们能够控制SSLSocket和KeyStore，TrustStore相关的部分，而不是使用系统默认值
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyMgrFactory.getKeyManagers(), null, null); // 第一二三参分别是KeyManager[], TrustManager[], SecureRandom[]。  ||  显然，第一个参数用来创建服务端Socket的，而第二个参数用于创建客户端Socket
        ServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();

        // 在localhost:15000端口监听，模拟一个服务器
        InetAddress addr = Inet4Address.getLocalHost();
        ServerSocket serverSocket = serverSocketFactory.createServerSocket(15000, 5, addr);


    }

}
