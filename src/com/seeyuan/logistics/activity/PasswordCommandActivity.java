package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.customview.MuInputEditText;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 设置密保口令
 * 
 * @author zhazhaobao
 * 
 */
public class PasswordCommandActivity extends BaseActivity implements
		OnClickListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	/**
	 * 密保问题
	 */
	private EditText ed_setting_command;

	/**
	 * 密保答案
	 */
	private MuInputEditText ed_ensure_setting_command;

	private List<String> mDataList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_password_command);
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
		defaulttitle_title_tv.setText(R.string.setting_command);

		ed_setting_command = (EditText) findViewById(R.id.ed_setting_command);
		ed_setting_command.setOnClickListener(this);
		ed_ensure_setting_command = (MuInputEditText) findViewById(R.id.ed_ensure_setting_command);
		ed_ensure_setting_command.setOnClickListener(this);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.btn_Call_But:
			CommonUtils.makeingCalls(context,
					getResources().getString(R.string.Service_phone));
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(PasswordCommandActivity.this);
			break;
		case R.id.ed_setting_command:
			doPasswordAsk();
			break;
		case R.id.ed_ensure_setting_command:
			doPasswordAnswer();
			break;
		default:
			break;
		}
	}

	/**
	 * 密保答案
	 */
	private void doPasswordAnswer() {

	}

	/**
	 * 密保问题
	 */
	private void doPasswordAsk() {
		String[] data = getResources().getStringArray(R.array.setting_command);
		for (int i = 0; i < data.length; i++) {
			mDataList.add(data[i]);
		}
		final SingleSelectAlertDlialog dialog = new SingleSelectAlertDlialog(
				PasswordCommandActivity.this);
		dialog.setListContentForNormalText(mDataList);
		dialog.setTitle(getResources().getString(R.string.select_psw_request));
		dialog.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ed_setting_command.setText(mDataList.get((int)id).toString());
				dialog.dismiss();
			}
		});
	}
}
