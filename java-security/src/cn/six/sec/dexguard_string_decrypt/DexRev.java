package cn.six.sec.dexguard_string_decrypt;

// decrypt the string encryption in the DexGuard
// see : http://opensecurity.in/reversing-dexguards-string-encryption/

public class DexRev {
private static final byte[] \u02cb = new byte[]{110, -49, 71, -112, 33, -6, -12, 12, -25, -8, -33, 47, 17, -4, -82, 82, 4, -74, 33, -35, 18, 7, -25, 31};
    public static void main(String args[]) throws Exception {
        System.out.println("Decrypted: " + DexRev.\u02ca(0, 0, 0).intern());
    }

    private static String \u02ca(int var0, int var1_1, int var2_2) {
        var2_2 = var2_2 * 4 + 4;
        int var7_3 = var0 * 3 + 83;
        int var6_4 = -1;
        byte[] var3_5 = DexRev.\u02cb;
        int var8_6 = var1_1 * 3 + 21;
        byte [] var4_7 = new byte[var8_6];
        var1_1 = var6_4;
        int var5_8 = var7_3;
        var0 = var2_2;
        boolean firsttime=true;
        do {
        if(!firsttime){
            var0 = var7_3 + 1;
            var5_8 = var2_2 + var5_8 + 1;
        }
        firsttime=false;
            var4_7[++var1_1] = (byte)var5_8;
            if (var1_1 == var8_6 - 1) {
                return new String(var4_7, 0);
            }
            var2_2 = var5_8;
            var5_8 = var3_5[var0];
            var7_3 = var0;
        } while (true);
    }
}