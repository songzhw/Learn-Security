package ca.six.sec.keystore.rsa;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import ca.six.sec.R;


public class RestoreKeyDemo extends Activity {
    private TextView tv;
    private String moduleStr, exponentStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_btn);
        tv = (TextView) findViewById(R.id.tv_simple);
    }

    public void onClickSimpleButton(View v) throws Exception {
        // 生成一对公私钥
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 产生关键变量， 供使用方还原出公钥。 关键变量就是module, exponents两个。
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        BigInteger modules = publicKeySpec.getModulus();
        BigInteger exponents = publicKeySpec.getPublicExponent();
        byte[] moduleBytes = modules.toByteArray();
        byte[] exponentBytes = exponents.toByteArray();
        moduleStr = Base64.encodeToString(moduleBytes, Base64.DEFAULT);
        exponentStr = Base64.encodeToString(exponentBytes, Base64.DEFAULT);
        System.out.println("szw publicKey01 = " + Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));
    }

    public void onClickSimpleButton2(View v) throws Exception {
        //BigInteger的CF是： BigInteger(  byte[]   )
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(
                new BigInteger(Base64.decode(moduleStr, Base64.DEFAULT)),
                new BigInteger(Base64.decode(exponentStr, Base64.DEFAULT))
        );

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        System.out.println("szw publicKey02 = " + Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));
    }
}
/*
szw publicKey01 = MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYXrHB2Z3y8yEm+yRY8/w7bY71kjtKg5/aSQeY
szw publicKey02 = MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYXrHB2Z3y8yEm+yRY8/w7bY71kjtKg5/aSQeY
 */