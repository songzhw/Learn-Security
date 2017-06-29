package ca.six.sec.keystore.aes;

import android.app.Activity;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;

import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import ca.six.sec.R;
import ca.six.sec.Util;

/**
 * Created by songzhw on 2016/1/16
 *
 * if the iv and key is the same, then the encryption result is the same.
 *    (-- it need you to set  KeyGenParameterSpec.Builder.setRandomizedEncryptionRequired(false) )
 *
 * Note : Disabling the randomized encryption is not recommended,
 * so not providing an IV when encrypting the data is recommended
 */
@Deprecated
public class SameKeyOrNotActivity3 extends Activity {
    private String keyAlias = "test01";
    private KeyStore keyStore;
    private byte[] encrypted;

    private SecretKey keyIdle;
    private byte[] iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_key_or_not);

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null); // reload the keystore
            int purpose = KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT;
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyAlias, purpose)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .setRandomizedEncryptionRequired(false); //TODO "random(false)" is not recommended !!!!
            keyGenerator.init(builder.build());
            keyIdle = keyGenerator.generateKey();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genSaveKey(View v) throws Exception {

        //TODO weird!! the key.getEncoded() is null!!!! (I don't know why)
//        Log.d("szw", "szw " + (key == null) + " ; key03 = " + (key.getEncoded()));

        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        if(iv == null || iv.length == 0){
            cipher.init(Cipher.ENCRYPT_MODE, keyIdle);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, keyIdle, new IvParameterSpec(iv));
        }
        encrypted = cipher.doFinal("123qwe".getBytes());
        iv = cipher.getIV();
        Log.d("szw","03-02 encrypted = "+Util.bytesToHexString(encrypted));
        Log.d("szw", "     : iv = "+Util.bytesToHexString(iv));

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
            Log.d("szw","04-02 decrypted = "+new String(ret));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
