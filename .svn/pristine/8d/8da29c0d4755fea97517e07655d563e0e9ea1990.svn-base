package com.seeyuan.logistics.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 修改密码
 * 
 * @author zhazhaobao
 * 
 */
public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 新密码
	 */
	private MuInputEditText ed_newPsw;

	/**
	 * 确认新密码
	 */
	private MuInputEditText ed_user_againpwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_change_password);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.MyCollect_Title);

		ed_newPsw = (MuInputEditText) findViewById(R.id.ed_newPsw);
		ed_user_againpwd = (MuInputEditText) findViewById(R.id.ed_user_againpwd);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.bt_submit:
			doSubmit();
			break;

		default:
			break;
		}
	}

	/**
	 * 提交密码
	 */
	private void doSubmit() {
		if (ed_newPsw.length() < 6 || ed_newPsw.length() > 12) {
			ed_newPsw.showPopWindow(ChangePasswordActivity.this, getResources()
					.getString(R.string.psw_number_format));
		} else if (ed_user_againpwd.length() < 6 || ed_user_againpwd.length() > 12) {
			ed_user_againpwd.showPopWindow(ChangePasswordActivity.this,
					getResources().getString(R.string.psw_number_format));
		} else {
			if (!ed_newPsw.getText().toString()
					.equalsIgnoreCase(ed_user_againpwd.getText().toString())) {
				ed_user_againpwd.showPopWindow(ChangePasswordActivity.this,
						getResources().getString(R.string.entered_psw_differ));
			} else {
				// 提交密码
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(ChangePasswordActivity.this);
			break;

		default:
			break;
		}
	}

	public TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable arg0) {

		}
	};
}
