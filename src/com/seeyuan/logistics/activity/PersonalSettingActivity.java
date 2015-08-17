package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;

/**
 * 设置
 * 
 * @author Administrator
 * 
 */
/**
 * @author Administrator
 * 
 */
public class PersonalSettingActivity extends BaseActivity implements
		OnClickListener {

	private Context context;

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_personal_setting); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局

		context = getApplicationContext();
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.setting);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!PersonalSettingActivity.this.isFinishing()) {
				finish();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.update_password:
			doChangePassword();
			break;
		case R.id.setting_command:
			doSettingCommand();
			break;

		default:
			break;
		}
	}

	/**
	 * 设置密保口令
	 */
	private void doSettingCommand() {
		Intent intent = new Intent(PersonalSettingActivity.this,
				PasswordCommandActivity.class);
		startActivity(intent);
	}

	/**
	 * 修改密码
	 */
	private void doChangePassword() {
		Intent intent = new Intent(PersonalSettingActivity.this,
				ChangePasswordActivity.class);
		startActivity(intent);
	}
}
