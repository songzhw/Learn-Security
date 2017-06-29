package cn.six.asec.keystore.aes;

import android.app.Activity;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.KeyFactory;
import java.security.KeyStore;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;

import cn.six.asec.R;
import cn.six.asec.Util;

/**
 * Created by songzhw on 2016/1/16
 */
public class SameKeyOrNotActivity extends Activity {
    private String keyAlias = "test02"; // "test01" before
    private KeyStore keyStore;
    private byte[] encrypted, iv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_key_or_not);

        tv = (TextView) findViewById(R.id.tvSameKeyOrNot);
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genSaveKey(View v) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        keyStore.load(null); // reload the keystore
        int purpose = KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyAlias, purpose)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
        keyGenerator.init(builder.build());
        SecretKey key = keyGenerator.generateKey();
        //TODO weird!! the key.getEncoded() is null!!!! (I don't know why)
//        Log.d("szw", "szw " + (key == null) + " ; key03 = " + (key.getEncoded()));

        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encrypted = cipher.doFinal("123qwe".getBytes());
        iv = cipher.getIV();
        Log.d("szw","03-02 encrypted = "+Util.bytesToHexString(encrypted));

    }

    public void loadKey(View v){
        try {
            SecretKey key = (SecretKey) keyStore.getKey(keyAlias, null);
            //TODO weird!! the key.getEncoded() is null!!!! (I don't know why)
//            Log.d("szw", "szw " + (key == null) + " ; key04 = " + (key.getEncoded()));

            final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            // Use Encrypt mode.
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] ret = cipher.doFinal(encrypted);
            Log.d("szw", "04-02 encrypted = " + new String(ret));


            // java.security.NoSuchAlgorithmException: KeyFactory SimpleAES implementation not found
//            // KeyFactory kf = KeyFactory.getInstance(key.getAlgorithm(), "AndroidKeyStore");
//            SecretKeyFactory kf = SecretKeyFactory.getInstance(key.getAlgorithm(), "AndroidKeyStore");
//            KeyInfo keyInfo = (KeyInfo) kf.getKeySpec(key, KeyInfo.class);
//            Log.d("szw", "[SimpleAES] secretKey.isInHardware = " + keyInfo.isInsideSecureHardware() ); //=> true

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKeys(View v) {
        try {
            keyStore.load(null); // reload the keystore

            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String aalias = aliases.nextElement();
                boolean isCE = keyStore.isCertificateEntry(aalias);
                boolean isKE = keyStore.isKeyEntry(aalias);
                Log.d("szw", "KeyStore alias = " + aalias + " ; isCe = " + isCE + " ; isKe = " + isKE);
            }
        }catch(Exception e){
            Log.d("szw","show keys error! : "+e.getLocalizedMessage());
        }
    }
//     KeyStore alias = test01 ; isCe = false ; isKe = true
//     KeyStore alias = test02 ; isCe = false ; isKe = true
}
