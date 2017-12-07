package ca.six.sec.keystore.aes;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import ca.six.sec.R;


public class StoreKeyDemo extends Activity {
    private final String plainText = "0123456789abcdeqwert0123456789abcdeqwert0123456789abcdeqwert0123456789abcdeqwert0123456789abcdeqwert0123456789abcdeqwert0123456789abcdeqwert0123456789abcdeqwert";
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
        } catch (Exception e) {
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickSimpleButton(View v) throws Exception {
        long start = System.currentTimeMillis();

        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, provider);
        int purpose = KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT;
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(keyAlias, purpose)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build();
        keyGenerator.init(keyGenParameterSpec);
        key = keyGenerator.generateKey();
        // System.out.println("szw 03-01 generated a key : "+Base64.encodeToString(key.getEncoded(), Base64.DEFAULT)); // key.getEncoded为空, 直接crash

        // encrypt
        final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        // Use Encrypt mode.
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encrypted = cipher.doFinal(plainText.getBytes());
        iv = cipher.getIV();
        Log.d("szw", "03-02 encrypted = " + Base64.encodeToString(encrypted, Base64.DEFAULT));
        Log.d("szw", "     : iv = " + Base64.encodeToString(iv, Base64.DEFAULT));
        Log.d("szw", "       (encrypt takes time "+ (System.currentTimeMillis() - start)+" ms)");
    }

    // keyStore.deleteEntry(alias);  --> So keystore can also delete one key


    public void onClickSimpleButton2(View v) throws Exception {
        Intent it = new Intent(this, DecryptFromKeyStoreDemo.class);
        it.putExtra("iv", Base64.encodeToString(iv, Base64.DEFAULT));
        it.putExtra("encrypted", Base64.encodeToString(encrypted, Base64.DEFAULT));
        startActivity(it);
    }


}


