package ca.six.sec.keyguard;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ca.six.sec.R;

public class KeyGuardActivity02 extends Activity {
    private TextView tv;
    private KeyguardManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_tv);

        mgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        tv = (TextView) findViewById(R.id.tv_two);

    }

    public void onClickTwo(View v){
        if(mgr.isDeviceSecure()) { // otherwise ,the intent below is null, and a crash will happen.
            Intent it = mgr.createConfirmDeviceCredentialIntent("My Title", "My Description");
            startActivityForResult(it, 101);
        }
    }

    public void onClickTwo2(View v){
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Log.d("szw", " onActivityResult() ok");
            }
        }
    }
}
