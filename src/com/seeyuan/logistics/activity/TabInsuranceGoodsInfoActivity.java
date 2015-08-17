package com.seeyuan.logistics.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seeyuan.logistics.R;

public class TabInsuranceGoodsInfoActivity extends BaseActivity {

	/**
	 * 货物类型
	 */
//	private Button Insurance_GoodsType;

	/**
	 * 包装类型
	 */
//	private Button Insurance_PackingType;

	/**
	 * 下一步
	 */
//	private Button BuyInsurance_GoodsInfoNext_But;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_insurance_goodsinfo);

		initView();
	}

	@Override
	public void initView() {
		// Insurance_GoodsType = (Button)
		// findViewById(R.id.Insurance_GoodsType);
		// Insurance_GoodsType.setOnClickListener(this);
		//
		// Insurance_PackingType = (Button)
		// findViewById(R.id.Insurance_PackingType);
		// Insurance_PackingType.setOnClickListener(this);
		//
		// BuyInsurance_GoodsInfoNext_But = (Button)
		// findViewById(R.id.BuyInsurance_GoodsInfoNext_But);
		// BuyInsurance_GoodsInfoNext_But.setOnClickListener(this);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.Insurance_GoodsType:

			break;
		case R.id.Insurance_PackingType:

			break;
		case R.id.BuyInsurance_GoodsInfoNext_But:

			break;

		default:
			break;
		}
	}
}
