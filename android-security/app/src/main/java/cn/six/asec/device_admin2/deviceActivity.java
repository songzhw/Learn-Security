package cn.six.asec.device_admin2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import cn.six.asec.R;

public class deviceActivity extends Activity {
	static final int RESULT_ENABLE = 1;
	DevicePolicyManager devicePolicyManager;
	ActivityManager activityManager;
	ComponentName deviceComponentName;
	Button btnEnable, btnDisable, force_lock, btn_time_out, reset;
	EditText et;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		deviceComponentName = new ComponentName(deviceActivity.this,
				deviceAdminReceiver.class);
		setContentView(R.layout.activity_device_admin_two);

		btnEnable = (Button) findViewById(R.id.enable_admin);
		btnDisable = (Button) findViewById(R.id.disable_admin);
		force_lock = (Button) findViewById(R.id.force_lock);
		btn_time_out = (Button) findViewById(R.id.time_out);
		et = (EditText) findViewById(R.id.et_time_out);
		reset = (Button) findViewById(R.id.reset);

		btnEnable.setOnClickListener(new enableAdminClickEvent());
		btnDisable.setOnClickListener(new disableAdminClickEvent());
		force_lock.setOnClickListener(new force_lock());
		btn_time_out.setOnClickListener(new timeoutClickEvent());
		reset.setOnClickListener(new resetClickEvent());
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateButtonState();
	}


	void updateButtonState() {
		boolean active = devicePolicyManager.isAdminActive(deviceComponentName);
		if (active) {
			btnEnable.setEnabled(false);
			btnDisable.setEnabled(true);
			force_lock.setEnabled(true);
			btn_time_out.setEnabled(true);
			reset.setEnabled(true);
		} else {
			btnEnable.setEnabled(true);
			btnDisable.setEnabled(false);
			force_lock.setEnabled(false);
			btn_time_out.setEnabled(false);
			reset.setEnabled(false);
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RESULT_ENABLE:
			if (resultCode == Activity.RESULT_OK) {
				Log.v("DeviceEnable", "deviceAdmin:enable");
			} else {
				Log.v("DeviceEnable", "deviceAdmin:disable");
			}
			break;

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 设备管理可用的点击事件
	 * 
	 * @author terry
	 * 
	 */
	class enableAdminClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					deviceComponentName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"(可自定义区域2)");
			startActivityForResult(intent, RESULT_ENABLE);
		}

	}

	/**
	 * 设备管理不可用的点击事件
	 * 
	 * @author terry
	 * 
	 */
	class disableAdminClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			devicePolicyManager.removeActiveAdmin(deviceComponentName);
			updateButtonState();
		}

	}

	/**
	 * 锁屏
	 * 
	 * @author terry
	 * 
	 */
	class force_lock implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (ActivityManager.isUserAMonkey()) {
				Builder builder = new Builder(
						deviceActivity.this);
				builder.setMessage("你不能对此屏幕进行操作，因为你不是管理员");
				builder.setPositiveButton("I admit defeat", null);
				builder.show();
				return;
			}
			boolean active = devicePolicyManager.isAdminActive(deviceComponentName);
			if (active) {
				// ""中为设置的PIN密码，默认为123456
				devicePolicyManager.resetPassword("123456",
						DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
				devicePolicyManager.lockNow();
			}
		}
	}

	/**
	 * 屏幕自动变暗
	 * 
	 * @author terry
	 * 
	 */
	class timeoutClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (ActivityManager.isUserAMonkey()) {
				Builder builder = new Builder(
						deviceActivity.this);
				builder.setMessage("你不能对我的屏幕进行操作，因为你不是管理员");
				builder.setPositiveButton("I admit defeat", null);
				builder.show();
				return;
			}
			boolean active = devicePolicyManager.isAdminActive(deviceComponentName);
			if (active) {
				long timeout = 1000L * Long.parseLong(et.getText().toString());
				devicePolicyManager.setMaximumTimeToLock(deviceComponentName, timeout);
			}
		}
	}

	/**
	 * 恢复出厂设置
	 * 
	 * @author terry
	 * 
	 */
	class resetClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (ActivityManager.isUserAMonkey()) {
				// Don't trust monkeys to do the right thing!
				Builder builder = new Builder(
						deviceActivity.this);
				builder
						.setMessage("You can't wipe my data because you are a monkey!");
				builder.setPositiveButton("I admit defeat", null);
				builder.show();
				return;
			}

			Builder builder = new Builder(deviceActivity.this);
			builder.setMessage("将重置数据，你确定此操作吗？");
			builder.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Builder aler = new Builder(
									deviceActivity.this);
							aler.setMessage("删除数据后，系统将会重新启动.确定吗？");
							aler.setPositiveButton(android.R.string.ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											boolean active = devicePolicyManager
													.isAdminActive(deviceComponentName);
											if (active) {
												devicePolicyManager.wipeData(0);
											}
										}
									});
							aler
									.setNeutralButton(android.R.string.cancel,
											null);
							aler.show();
						}
					});
			builder.setNeutralButton(android.R.string.cancel, null);
			builder.show();
		}
	}

}