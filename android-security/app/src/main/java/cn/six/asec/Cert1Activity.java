package cn.six.asec;

import android.content.Intent;
import android.security.KeyChain;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.IOException;

public class Cert1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert_one);

        try {
            BufferedInputStream bis = new BufferedInputStream( getAssets().open("test12.p12"));
            byte[] keychainBytes = new byte[bis.available()];
            bis.read(keychainBytes);

            Intent installIt = KeyChain.createInstallIntent();
            installIt.putExtra(KeyChain.EXTRA_PKCS12, keychainBytes);
            installIt.putExtra(KeyChain.EXTRA_NAME, "test12");
            startActivityForResult(installIt, 101);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
