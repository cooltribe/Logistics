package com.seeyuan.logistics.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 附近
 * 
 * @author zhazhaobao
 * 
 */
public class AroundActivity extends Fragment implements OnClickListener,
		OnCheckedChangeListener {

	private int index;
	// 当前fragment的index
	private int currentTabIndex;

	private RadioButton radio_around_car;
	private RadioButton radio_around_goods;
	private RadioButton radio_around_friend;

	/**
	 * 主布局，供界面切换
	 */
	private RelativeLayout fragment_container;

	private Context context;

	private TabAroundCarActivity aroundCar;
	private TabAroundFriendsActivity aroundFriend;
	private TabAroundGoodsActivity aroundGoods;

	private LinearLayout.LayoutParams params;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_around, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity().getApplicationContext();
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		fragment_container = (RelativeLayout) getView().findViewById(
				R.id.fragment_container);
		radio_around_car = (RadioButton) getActivity().findViewById(
				R.id.radio_tab_around_car);
		radio_around_car.setOnClickListener(this);
		radio_around_goods = (RadioButton) getActivity().findViewById(
				R.id.radio_tab_around_goods);
		radio_around_goods.setOnClickListener(this);
		radio_around_friend = (RadioButton) getActivity().findViewById(
				R.id.radio_tab_around_friends);
		radio_around_friend.setOnClickListener(this);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		aroundCar = new TabAroundCarActivity(context);
		aroundFriend = new TabAroundFriendsActivity(context);
		aroundGoods = new TabAroundGoodsActivity(context);

		fragment_container.addView(aroundCar, params);
		// fragment_container.addView(aroundGoods, params);
		// fragment_container.addView(aroundFriend, params);
		// fragment_container.bringChildToFront(aroundCar);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(getActivity());
			break;
		case R.id.radio_tab_around_car:
			index = 0;
			break;
		case R.id.radio_tab_around_goods:
			index = 1;
			break;
		case R.id.radio_tab_around_friends:
			index = 2;
			break;
		default:
			break;
		}
		changeView(index);
	}

	private void changeView(int position) {
		View view = null;
		switch (position) {
		case 0:
			// fragment_container.bringChildToFront(aroundCar);
			fragment_container.removeAllViews();
			fragment_container.addView(aroundCar, params);
			break;
		case 1:
			// fragment_container.bringChildToFront(aroundGoods);
			fragment_container.removeAllViews();
			fragment_container.addView(aroundGoods, params);
			break;
		case 2:
			fragment_container.removeAllViews();
			fragment_container.addView(aroundFriend, params);
			// fragment_container.bringChildToFront(aroundFriend);
			// view = new TabAroundFriendsActivity(context);
			// fragment_container.removeAllViews();
			// fragment_container.addView(view);
			break;

		default:
			break;
		}
	}

	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void onTabClicked(View view) {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}

}
