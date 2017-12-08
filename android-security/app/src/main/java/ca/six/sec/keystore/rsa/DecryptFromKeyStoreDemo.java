package ca.six.sec.keystore.rsa;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import javax.crypto.Cipher;

import ca.six.sec.R;


public class DecryptFromKeyStoreDemo extends Activity {
    private final String provider = "AndroidKeyStore";
    private final String keyAlias = "key12";

    private KeyStore keyStore;
    private PrivateKey privateKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_btn);
        TextView tv = (TextView)findViewById(R.id.tv_simple);
        tv.setText("Page B");

        try {
            keyStore = KeyStore.getInstance(provider);
            keyStore.load(null);

            Certificate cert = keyStore.getCertificate(keyAlias);
            PublicKey publicKey = cert.getPublicKey();
            privateKey = (PrivateKey)keyStore.getKey(keyAlias, null);
            System.out.println("szw pub = "+publicKey+" ; pri = "+privateKey);

        } catch(Exception e){}

    }


    public void onClickSimpleButton(View v) throws Exception{
        Intent it = getIntent();
        byte[] encrypted = Base64.decode(it.getStringExtra("encrypted"), Base64.DEFAULT);

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println("szw RSA decrypted = "+new String(decrypted));
    }


    public void onClickSimpleButton2(View v){

    }
}

