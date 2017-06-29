package cn.six.sec.msgdigest;

import cn.six.sec.Util;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by hzsongzhengwang on 2016/1/7.
 *
 MD值其实并不能真正解决数据被篡改的问题。因为作假者可以搞一个假网站，然后提供
 假数据和根据假数据得到的MD值。这样，下载者下载到假数据，计算的MD值和假网站提供的
 MD数据确实一样，但是这份数据是被篡改过了的。
 解决这个问题的一种方法是：计算MD的时候，输入除了消息数据外，还有一个密钥。
 由于作假者没有密钥信息，所以它在假网站上上提供的MD肯定会和数据下载者根据密钥+假
 数据得到的MD值不一样。
 这种方法得到的MD叫Message Authentication Code，简称MAC

 创建MAC计算对象，其常用算法有“HmacSHA1”和“HmacMD5”。其中
    SHA1和MD5是计算消息摘要的算法，
    Hmac是生成MAC码的算法

 */


public class ComplexMdTest {
    public static void main(String[] args) throws Exception {
        File file = new File("src/cn/six/sec/cert/test12.p12");
        InputStream is = new FileInputStream(file);

        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] buffer = new byte[1024];
        int alreadyRead = 0;
        while( (alreadyRead = is.read(buffer)) > 0) {
            mac.update(buffer, 0, alreadyRead);
        }
        byte[] macBytes = mac.doFinal();
        System.out.println("File Message Digest(Mac) = "+ Util.bytesToHexString(macBytes)); //87287F0396638F238F3816DCCB65531EB2B0B709

        is.close();
    }
}
