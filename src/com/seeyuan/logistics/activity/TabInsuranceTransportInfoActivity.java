package com.seeyuan.logistics.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seeyuan.logistics.R;

public class TabInsuranceTransportInfoActivity extends BaseActivity {

	/**
	 * 运输方式
	 */
	// private Button Insurance_TransportMode;

	/**
	 * 车辆类型
	 */
	// private Button Insurance_CarTypeBtn;

	/**
	 * 起始地
	 */
	// private Button Insurance_TransportOrigin;

	/**
	 * 目的地
	 */
	// private Button Insurance_TransportDestination;

	/**
	 * 起运时间
	 */
	// private Button Insurance_DepartureTime;

	/**
	 * 下一步
	 */
	// private Button BuyInsurance_TransportationNext_But;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_insurance_transportinfo);
		initView();
	}

	@Override
	public void initView() {
		// Insurance_TransportMode = (Button)
		// findViewById(R.id.Insurance_TransportMode);
		// Insurance_TransportMode.setOnClickListener(this);
		//
		// Insurance_CarTypeBtn = (Button)
		// findViewById(R.id.Insurance_CarTypeBtn);
		// Insurance_CarTypeBtn.setOnClickListener(this);
		//
		// Insurance_TransportOrigin = (Button)
		// findViewById(R.id.Insurance_TransportOrigin);
		// Insurance_TransportOrigin.setOnClickListener(this);
		//
		// Insurance_TransportDestination = (Button)
		// findViewById(R.id.Insurance_TransportDestination);
		// Insurance_TransportDestination.setOnClickListener(this);
		//
		// Insurance_DepartureTime = (Button)
		// findViewById(R.id.Insurance_DepartureTime);
		// Insurance_DepartureTime.setOnClickListener(this);
		//
		// BuyInsurance_TransportationNext_But = (Button)
		// findViewById(R.id.BuyInsurance_TransportationNext_But);
		// BuyInsurance_TransportationNext_But.setOnClickListener(this);
	}

	@Override
	public void onClickListener(View view) {

		switch (view.getId()) {
		case R.id.Insurance_TransportMode:

			break;
		case R.id.Insurance_CarTypeBtn:

			break;
		case R.id.Insurance_TransportOrigin:

			break;
		case R.id.Insurance_TransportDestination:

			break;
		case R.id.Insurance_DepartureTime:

			break;

		default:
			break;
		}

	}
}
