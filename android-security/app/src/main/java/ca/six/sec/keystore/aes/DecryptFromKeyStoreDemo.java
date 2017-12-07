package ca.six.sec.keystore.aes;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import ca.six.sec.R;


/*
    TODO is it to secure to save the iv?

https://stackoverflow.com/questions/3225640/how-to-decrypt-aes-cbc-with-known-iv
https://security.stackexchange.com/questions/17044/when-using-aes-and-cbc-is-it-necessary-to-keep-the-iv-secret

 */
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickSimpleButton(View v) throws Exception {
        long start = System.currentTimeMillis();

        Intent it = getIntent();
        byte[] iv = Base64.decode(it.getStringExtra("iv"), Base64.DEFAULT);
        byte[] encrypted = Base64.decode(it.getStringExtra("encrypted"), Base64.DEFAULT);


        // decrypt
        SecretKey key = (SecretKey) keyStore.getKey(keyAlias, null);
//        Log.d("szw", "szw " + (key == null) + " ; key04 = " + (key != null ? key.getEncoded() : "null")); // key.getEncode is null!

        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] ret = cipher.doFinal(encrypted);
        Log.d("szw", "04-01 decrypted = " + new String(ret));
        Log.d("szw", "       (decrypt takes time "+ (System.currentTimeMillis() - start)+" ms)");

    }


    public void onClickSimpleButton2(View v) {

    }
}
