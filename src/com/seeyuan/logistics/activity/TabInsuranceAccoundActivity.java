package com.seeyuan.logistics.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.seeyuan.logistics.R;

public class TabInsuranceAccoundActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_insurance_account);

		initView();
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.BuyInsurance_Submit_But:
			Log.d("TAG", "提交");
			break;

		default:
			break;
		}
	}

	@Override
	public void initView() {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		default:
			break;
		}
	}

}
