package cn.six.sec.msgdigest;

import cn.six.sec.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * Created by hzsongzhengwang on 2016/1/7.
 */
public class SimpleMdTest {

    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");

        String data = "msg from the future";
        md.update(data.getBytes());
        byte[] mdBytes = md.digest();
        System.out.println("Simple Message Digest = "+ Util.bytesToHexString(mdBytes)); // 259E 798B 5EDD 05F1 ED2C A748 BDAB C37E

        md.reset(); // 重置MessageDigest对象，这样就能重复使用它 (szw: 好像不reset()也行)
        md.update("test2".getBytes());
        byte[] mdBytes22 = md.digest();
        System.out.println("Simple Message Digest = "+ Util.bytesToHexString(mdBytes22)); // AD0234829205B9033196BA818F7A872B


        // 计算一个文件的MD5值。 比如说校验下载的AndroidStudio是否被篡改
        File file = new File("src/cn/six/sec/cert/test12.p12");
        InputStream is = new FileInputStream(file);
        DigestInputStream dis = new DigestInputStream(is, md);
        byte[] buffer = new byte[1024];
        while( dis.read(buffer) > 0) {}
        // after reading the whole file, we get the md value
        byte[] fileMdBytes = md.digest();
        System.out.println("File Message Digest = "+Util.bytesToHexString(fileMdBytes)); //D9662F96B8F1E61C7BF3E0917C62235F

        dis.close();
        is.close();
    }

}
