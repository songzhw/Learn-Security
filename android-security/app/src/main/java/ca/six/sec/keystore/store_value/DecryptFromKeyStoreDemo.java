package ca.six.sec.keystore.store_value;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import ca.six.sec.R;
import ca.six.sec.Util;


public class DecryptFromKeyStoreDemo extends Activity {
    private final String provider = "AndroidKeyStore";
    private final String keyAlias = "key12";

    private KeyStore keyStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_btn);

        try {
            keyStore = KeyStore.getInstance(provider);
            keyStore.load(null);
        } catch (Exception e) {
        }

    }

    public void onClickSimpleButton(View v) throws Exception {
        Intent it = getIntent();
        byte[] iv = Util.hexStringToBytes(it.getStringExtra("iv"));
        byte[] encrypted = Util.hexStringToBytes(it.getStringExtra("encrypted"));


        // decrypt
        SecretKey key = (SecretKey) keyStore.getKey(keyAlias, null);
        //TODO weird!! the key.getEncoded() is null!!!! (I don't know why)
        //            Log.d("szw", "szw " + (key == null) + " ; key04 = " + (key.getEncoded()));

        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] ret = cipher.doFinal(encrypted);
        Log.d("szw", "04-01 decrypted = " + new String(ret));

    }


    public void onClickSimpleButton2(View v) {

    }
}
