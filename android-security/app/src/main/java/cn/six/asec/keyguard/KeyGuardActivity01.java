package cn.six.asec.keyguard;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.six.asec.R;

/*
  disableKeyguard只是关闭系统锁屏服务，调用该方法后并不会立即解锁，而是使之不显示解锁.
  同样reenableKeyguard是恢复锁屏服 务，并不会立即锁屏

  szw: Android 6.0, the two functions is disabled!
* */
public class KeyGuardActivity01 extends Activity {
    private TextView tv;
    private KeyguardManager mgr;
    private KeyguardManager.KeyguardLock lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_tv);

        mgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        lock = mgr.newKeyguardLock("test");
        tv = (TextView) findViewById(R.id.tv_two);

    }

    public void onClickTwo(View v){
        lock.disableKeyguard();
    }

    public void onClickTwo2(View v){
        lock.reenableKeyguard();
    }

}
