package ca.six.sec;

/**
 * Created by songzhw on 2016/1/6.
 */
public class Util {

    @Deprecated
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

    public static byte[] hexStringToBytes(String raw) {
        int len = raw.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(raw.charAt(i), 16) << 4)
                    + Character.digit(raw.charAt(i+1), 16));
        }
        return data;
    }
}
