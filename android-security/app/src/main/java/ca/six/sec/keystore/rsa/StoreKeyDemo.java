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

import javax.crypto.Cipher;

import ca.six.sec.R;

@RequiresApi(api = Build.VERSION_CODES.M)
public class StoreKeyDemo extends Activity {
    private final String plainText = "private information 2017RSA";
    private final String androidKeyProvider = "AndroidKeyStore";
    private final String keyAlias = "key34";

    private KeyStore keyStore;

    private TextView tv;
    private KeyPair keyPair;
    private byte[] encrypted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_btn);
        tv = (TextView)findViewById(R.id.tv_simple);

        try{
            keyStore = KeyStore.getInstance(androidKeyProvider);
            keyStore.load(null);

            // purpose就4个: 加密, 解密 ; sign, verify;
            int purpose = KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT;
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(keyAlias, purpose)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                .build();

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA,
                androidKeyProvider);
            keyPairGenerator.initialize(spec);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch(Exception e){

        }
    }

    // encrpyt
    public void onClickSimpleButton(View v) throws Exception {
        Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_RSA+"/" +
            KeyProperties.BLOCK_MODE_ECB+"/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        encrypted = cipher.doFinal(plainText.getBytes());
        System.out.println("szw : RSA encrypted = "+ Base64.encodeToString(encrypted, Base64.DEFAULT));
    }

    // naviagte
    public void onClickSimpleButton2(View v){
        Intent it = new Intent(this, DecryptFromKeyStoreDemo.class);
        it.putExtra("encrypted", Base64.encodeToString(encrypted, Base64.DEFAULT));
        startActivity(it);
    }

}
