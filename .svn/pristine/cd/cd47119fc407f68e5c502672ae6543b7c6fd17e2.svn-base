package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.entity.UserInfo;
import com.seeyuan.logistics.provider.DBOperate;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 
 * 主界面，
 * 
 * @author zhazhaobao
 * 
 */
public class HomePageActivity extends Fragment implements OnClickListener {

	private Context mContext;

	/**
	 * 有货
	 */
	private RelativeLayout layout_havegoods;
	/**
	 * 有车
	 */
	private RelativeLayout layout_havecar;
	/**
	 * 信息发布
	 */
	private RelativeLayout layout_information;
	/**
	 * 货运担保
	 */
	private RelativeLayout layout_insurance;

	/**
	 * 个人中心
	 */
	private RelativeLayout layout_persional_information;

	private SharedPreferences sPreferences;
	private DBOperate dbOperate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_homepage, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity().getApplicationContext();
		sPreferences = getActivity().getSharedPreferences(
				ConstantPool.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE);
		dbOperate = DBOperate.getInstance(mContext);
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		layout_havegoods = (RelativeLayout) getView().findViewById(
				R.id.homepage_havegood);
		layout_havegoods.setOnClickListener(this);

		layout_havecar = (RelativeLayout) getView().findViewById(
				R.id.homepage_havecar);
		layout_havecar.setOnClickListener(this);

		layout_information = (RelativeLayout) getView().findViewById(
				R.id.homepage_information);
		layout_information.setOnClickListener(this);

		// layout_insurance = (RelativeLayout)
		// findViewById(R.id.homepage_insurance);
		// layout_insurance.setOnClickListener(this);

		layout_persional_information = (RelativeLayout) getView().findViewById(
				R.id.homepage_personal_information);
		layout_persional_information.setOnClickListener(this);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				ToastUtil.show(mContext, "登录超时,请重新登录");
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		};

	};

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.homepage_havegood:
			CommonUtils.jump2interface(mContext, HaveGoodsActivity.class);
			break;
		case R.id.homepage_havecar:
			CommonUtils.jump2interface(mContext, HaveCarActivity.class);
			break;
		case R.id.homepage_information:
			CommonUtils.jump2interface(mContext,
					InformationPublishActivity.class);
			break;
		case R.id.homepage_personal_information:
			doJump2PersonalCenter();
			break;
		default:
			break;
		}
	}

	private void doJump2PersonalCenter() {

		String uuid = CommonUtils.getUUID(mContext);
		UserInfo userInfo = null;
		try {
			userInfo = dbOperate.getUesrInfoByUUID(uuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == userInfo || userInfo.getIsLogin().equalsIgnoreCase("N")) {
			myHandler.sendEmptyMessage(1);
			return;
		}
		if (userInfo.getMemberType() == 2) {
			CommonUtils.jump2interface(mContext,
					PersonalInformationActivity.class);
		} else {
			CommonUtils.jump2interface(mContext,
					PersonalInformation2Activity.class);
		}
	}
}
