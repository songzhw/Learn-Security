package ca.six.sec.keystore.store_value;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import ca.six.sec.R;
import ca.six.sec.Util;


public class StoreKeyDemo extends Activity {
    private final String plainText = "private information 2017";
    private final String provider = "AndroidKeyStore";
    private final String keyAlias = "key12";


    private KeyStore keyStore;
    private SecretKey key;
    private byte[] encrypted;
    private byte[] iv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_btn);

        try {
            keyStore = KeyStore.getInstance(provider);
            keyStore.load(null);

            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, provider);
            int purpose = KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT;
            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(keyAlias, purpose)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build();
            keyGenerator.init(keyGenParameterSpec);
            key = keyGenerator.generateKey();
        } catch (Exception e) {
        }

    }

    public void onClickSimpleButton(View v) throws Exception {
        // encrypt
        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encrypted = cipher.doFinal(plainText.getBytes());
        iv = cipher.getIV();
        Log.d("szw", "03-02 encrypted = " + Util.bytesToHexString(encrypted));
        Log.d("szw", "     : iv = " + Util.bytesToHexString(iv));
    }


    public void onClickSimpleButton2(View v) throws Exception {
        // decrypt
        SecretKey key = (SecretKey) keyStore.getKey(keyAlias, null);
        //TODO weird!! the key.getEncoded() is null!!!! (I don't know why)
        //            Log.d("szw", "szw " + (key == null) + " ; key04 = " + (key.getEncoded()));

        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] ret = cipher.doFinal(encrypted);
        Log.d("szw", "04-02 decrypted = " + new String(ret));

    }
}


