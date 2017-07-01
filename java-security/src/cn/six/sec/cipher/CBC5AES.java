package cn.six.sec.cipher;

import cn.six.sec.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Other things to keep in mind:
 * 1. Always use a fully qualified Cipher name. AES is not appropriate in such a case, because different JVMs/JCE
 * 2. providers may use different defaults for mode of operation and padding. Use AES/CBC/PKCS5Padding. Don't use ECB
 * mode, because it is not semantically secure.
 * 3. If you don't use ECB mode then you need to send the IV along with the ciphertext. This is usually done by
 * prefixing
 * the IV to the ciphertext byte array. The IV is automatically created for you and you can get it through
 * cipherInstance.getIV().
 */

public class CBC5AES {

    // 因为用了md5, 所以多次调用getKey()， 生成的SecretKey是一样能用的！
    private SecretKey getKey(String password) throws Exception {
        // 注意，为了能与 iOS 统一， 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] rawKey = get16Bytes(password);
        SecretKeySpec key = new SecretKeySpec(rawKey, "AES");
        return key;
    }

    private IvParameterSpec getIv(String iv) throws Exception {
        byte[] ivBytes = get16Bytes(iv);
        return new IvParameterSpec(ivBytes);
    }

    /**
     * 由任意长度的String, 生成一个128bit大小的结果(相同长度，生成的结果最好也一样， 即有消息摘要的效果)
     */
    private byte[] get16Bytes(String raw) throws Exception {
        // 虽然用了不被推荐的MD5， 但我们不是用来生成消息摘要并存起来。  而只是要生成一个128bit长度的string而已， 所以安全上应该没问题
        final MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = raw.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        return digest.digest();
    }

    public String encrypt(String password, String vector, String data) throws Exception {
        SecretKey key = getKey(password);
        IvParameterSpec iv = getIv(vector);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }

    public String decrypt(String password, String vector, String cipherText) throws Exception {
        SecretKey key = getKey(password);
        IvParameterSpec iv = getIv(vector);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decodedCipherBytes = Base64.decode(cipherText, Base64.NO_WRAP);
        byte[] decrpyted = cipher.doFinal(decodedCipherBytes);
        return new String(decrpyted, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        CBC5AES aes = new CBC5AES();
        String cipherText = aes.encrypt("apple", "KingKong", "I'm Orange~!");
        System.out.println("szw 01 = " + cipherText);
        String plain = aes.decrypt("apple", "KingKong", cipherText);
        System.out.println("szw 02 = " + plain);
    }
}
