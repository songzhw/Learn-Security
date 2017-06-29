package cn.six.asec;

/**
 * Created by songzhw on 2016/1/6.
 */
public class Util {

    public static String bytesToHexString(byte[] data){
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) ;  // 取的是高位的4位
            int counts = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('A' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F; // 取的是低位的4位
            } while (counts++ < 1); // two_half就是保证只取两次
        }
        return buf.toString();
    }


    public static void main(String[] args) {
        System.out.println("result = " +Util.bytesToHexString(new byte[]{127}));
//        111 halfbyte = 7
//        222 halfbyte = 15
//        222 halfbyte = 15
//        result = 7F
    }

}
