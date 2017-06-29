package ca.six.sec.keystore.from_other_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.KeyStore;
import java.util.Enumeration;

import ca.six.sec.R;

/**
 * This is a source from another project!!
 *
 * And now I get none of key, which means other app cannot access the key my app put in the KeyStore!!!
 * This is a great news. The security is good now.
 * 
 * */
public class ShowKeysActivity extends AppCompatActivity {
    private String keyAlias = "test02"; // "test01" before
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void genSaveKey(View v) throws Exception {
    }

    public void loadKey(View v) throws Exception {
    }

    public void showKeys(View v) {
        try {
            keyStore.load(null); // reload the keystore

            Enumeration<String> aliases = keyStore.aliases();
            Log.d("szw", "111");
            while (aliases.hasMoreElements()) {
                Log.d("szw", "222");
                String aalias = aliases.nextElement();
                boolean isCE = keyStore.isCertificateEntry(aalias);
                boolean isKE = keyStore.isKeyEntry(aalias);
                Log.d("szw", "KeyStore alias = " + aalias + " ; isCe = " + isCE + " ; isKe = " + isKE);
            }
        } catch (Exception e) {
            Log.d("szw", "show keys error! : " + e.getLocalizedMessage());
        }
    } //=> 111
}


