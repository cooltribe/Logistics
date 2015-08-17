package com.seeyuan.logistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;

public class CertificationManagerActivity extends BaseActivity implements
		OnClickListener {

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
		setContentView(R.layout.activity_certification_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.certification_manager_hint);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.PersonalCenter_Certification:
			doCertification();
			break;
		case R.id.PersonalCenter_phone_authentication:
			doPhoneAuthentication();
			break;
		case R.id.PersonalCenter_email_authentication:
			doEmailAuthentication();
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(CertificationManagerActivity.this);
			break;
		}

	}

	/**
	 * 
	 * 邮箱认证
	 */
	private void doEmailAuthentication() {

		Intent intent = new Intent(CertificationManagerActivity.this,
				EmailAuthenticationActivity.class);
		startActivity(intent);

	}

	/**
	 * 
	 * 手机认证
	 */
	private void doPhoneAuthentication() {

		Intent intent = new Intent(CertificationManagerActivity.this,
				PhoneAuthenticationActivity.class);
		startActivity(intent);

	}

	/**
	 * 实名认证
	 */
	private void doCertification() {
		Intent intent = new Intent(CertificationManagerActivity.this,
				CertificationActivity.class);
		startActivity(intent);
	}

}
