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
 * why the encryption result is different every time?
 * : because the iv is different
 *
 * I have a log proof:
 D/szw: 03-02 encrypted = 38403D05246C59003B39230F6B154E41
 D/szw:      : iv = 37372E/46B0039/5750B7004647E2365
 D/szw: 04-02 decrypted = 123qwe

 D/szw: 03-02 encrypted = 7160552F224C072E6E27637567201D6E
 D/szw:      : iv = 4C6D35364D48426D/E1B5D2F3D742D4C
 D/szw: 04-02 decrypted = 123qwe
 */
public class SameKeyOrNotActivity2 extends Activity {
    private String keyAlias = "test01";
    private KeyStore keyStore;
    private byte[] encrypted, iv;

    private SecretKey keyIdle;

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
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
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
        cipher.init(Cipher.ENCRYPT_MODE, keyIdle);
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
