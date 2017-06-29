package cn.six.asec.keystore.rsa;

import android.app.Activity;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;

import javax.crypto.Cipher;

import cn.six.asec.R;
import cn.six.asec.Util;

/**
 * Created by songzhw on 2016/1/21
 */
public class RsaKeyStoreActivity extends Activity {
    private String keyAlias = "rsa01";
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
            keyStore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     the private key is authorized to be used only for decryption using RSA OAEP encryption padding scheme with SHA-256 or SHA-512 digests.
     The use of the public key is unrestricted.
      */
    public void genSaveKey(View v) throws Exception {
        /* to fix the ANR in the main thread*/
        //  error msg : "Skipped 89 frames!  The application may be doing too much work on its main thread."
        //  szw : This generation of key and the encryption should be operated out of the main thread
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    int purpose = KeyProperties.PURPOSE_DECRYPT;
                    KeyGenParameterSpec param = new KeyGenParameterSpec.Builder(keyAlias, purpose)
                            .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                            .build();

                    KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
                    keyGen.initialize(param);

                    KeyPair keyPair = keyGen.generateKeyPair();

                    Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
                    encrypted = cipher.doFinal("RSA--Key".getBytes());
                    Log.d("szw", "[RSA] encrypted = " + Util.bytesToHexString(encrypted));
                }catch (Exception e){}
            }
        }).start();

    }

    public void loadKey(View v) throws Exception{

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, null);

        PublicKey publicKey = keyStore.getCertificate(keyAlias)
                                      .getPublicKey();

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] ret = cipher.doFinal(encrypted);
        Log.d("szw", "[RSA] returned = " + new String(ret));

//        KeyFactory kf = KeyFactory.getInstance(privateKey.getAlgorithm(), "AndroidKeyStore");
//        KeyInfo keyInfo = kf.getKeySpec(privateKey, KeyInfo.class);
//        Log.d("szw", "[RSA] privateKey.isInHardware = " + keyInfo.isInsideSecureHardware() ); //=>true

        // error :  KeyInfo can be obtained only for Android Keystore private keys
//        KeyInfo keyInfo2 = kf.getKeySpec(publicKey, KeyInfo.class);
//        Log.d("szw", "[RSA] publicKey.isInHardware = " + keyInfo2.isInsideSecureHardware() );
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

