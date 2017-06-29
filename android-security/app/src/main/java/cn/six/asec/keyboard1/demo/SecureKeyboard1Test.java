package cn.six.asec.keyboard1.demo;

import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.widget.EditText;

import cn.six.asec.R;
import cn.six.asec.keyboard1.SecKeyboardView;

@Deprecated
public class SecureKeyboard1Test extends Activity {

    private EditText et;
    private KeyboardView keyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_keyboard_one);

        et = (EditText) findViewById(R.id.password_edit);
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);

        new SecKeyboardView(this, et, keyboardView);
    }
}
