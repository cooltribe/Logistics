package com.seeyuan.logistics.activity;

import com.seeyuan.logistics.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 选择车主
 * @author 陈玉柱
 *
 */
public class SelectCarOwnActivity extends Activity implements OnClickListener{

	private TextView defaulttitle_title_tv;
	private Button maintitle_back_iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_select_car_own);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.deflaut_titlebar);
		findView();
	}
	private void findView(){
		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText("车主选择");
		maintitle_back_iv = (Button) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.maintitle_back_iv:
			finish();
			break;

		default:
			break;
		}
	}
}
