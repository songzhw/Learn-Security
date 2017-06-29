package ca.six.sec.keystore.user_authen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.six.sec.R;

/**
 * Created by songzhw on 2016/1/25
 *
 * User Authentication (pattern/PIN/password/fingerprint) for key use
 * Currently, the only means of such authorization is fingerprint authentication: FingerprintManager.authenticate
 *
 * reference ： https://github.com/mariotaku/FingerprintSample
 */
public class UserAuthenActivity01 extends Activity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_tv);

        tv = (TextView) findViewById(R.id.tv_two);

    }
    public void onClickTwo2(View v){}

    public void onClickTwo(View v){

    }
}
