package com.seeyuan.logistics.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 我的订单。管理
 * 
 * @author zhazhaobao
 * 
 */
@SuppressWarnings("deprecation")
public class MyOrderManagerActivity extends TabActivity implements
		OnClickListener, OnCheckedChangeListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private RadioGroup mainTab;
	private TabHost mTabHost;

	// 内容Intent
	private Intent mAllIntent;
	private Intent mBargainIntent;
	private Intent mExecuteIntent;
	private Intent mEvaluteIntent;
	private Intent mCompleteIntent;

	private final String TAB_TAG_ALL = "tab_tag_all";
	private final String TAB_TAG_BARGAIN = "tab_tag_bargain";
	private final String TAB_TAG_EXECUTE = "tab_tag_execute";
	private final String TAB_TAG_EVALUTE = "tab_tag_evalute";
	private final String TAB_TAG_COMPLETE = "tab_tag_complete";

	private int position;

	private RadioButton radio_tab_all_order;
	private RadioButton radio_tab_bargain;
	private RadioButton radio_tab_execute;
	private RadioButton radio_tab_evaluate;
	private RadioButton radio_tab_complete;

	private Context context;

	/**
	 * 是否正常操作进入 判断界面的跳转效果
	 */
	private boolean isNomalGetIn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_my_order_manager); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		context = getApplicationContext();
		mainTab.setOnCheckedChangeListener(this);
		position = getIntent().getIntExtra("position", -1);
		isNomalGetIn = getIntent().getBooleanExtra("isNomalGetIn", true);
		initView();
		prepareIntent();
		setupIntent();
		jump2PistionTab(position);
	}

	private void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.my_order_hint);

		radio_tab_all_order = (RadioButton) findViewById(R.id.radio_tab_all_order);
		radio_tab_bargain = (RadioButton) findViewById(R.id.radio_tab_bargain);
		radio_tab_execute = (RadioButton) findViewById(R.id.radio_tab_execute);
		radio_tab_evaluate = (RadioButton) findViewById(R.id.radio_tab_evaluate);
		radio_tab_complete = (RadioButton) findViewById(R.id.radio_tab_complete);

	}

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mAllIntent = new Intent(this, AllOrderActivity.class);
		mBargainIntent = new Intent(this, BargainOrderActivity.class);
		mExecuteIntent = new Intent(this, ExecuteOrderActivity.class);
		mEvaluteIntent = new Intent(this, EvaluateOrderActivity.class);
		mCompleteIntent = new Intent(this, CompleteOrderActivity.class);
	}

	/**
	 * 设置tab内容的intent
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_ALL, R.string.tab_publish_car,
				R.drawable.icon_1_n, mAllIntent));
		localTabHost
				.addTab(buildTabSpec(TAB_TAG_BARGAIN,
						R.string.tab_publish_goods, R.drawable.icon_2_n,
						mBargainIntent));
		localTabHost
				.addTab(buildTabSpec(TAB_TAG_EXECUTE,
						R.string.tab_publish_line, R.drawable.icon_2_n,
						mExecuteIntent));
		// localTabHost
		// .addTab(buildTabSpec(TAB_TAG_EVALUTE,
		// R.string.tab_publish_line, R.drawable.icon_2_n,
		// mEvaluteIntent));
		localTabHost
				.addTab(buildTabSpec(TAB_TAG_COMPLETE,
						R.string.tab_publish_line, R.drawable.icon_2_n,
						mCompleteIntent));
	}

	/**
	 * 构建TabHost的Tab页
	 * 
	 * @param tag
	 *            标记
	 * @param resLabel
	 *            标签
	 * @param resIcon
	 *            图标
	 * @param content
	 *            该tab展示的内容
	 * @return 一个tab
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_tab_all_order:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_ALL);
			break;
		case R.id.radio_tab_bargain:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_BARGAIN);
			break;
		case R.id.radio_tab_execute:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_EXECUTE);
			break;
		case R.id.radio_tab_evaluate:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_EVALUTE);
			break;
		case R.id.radio_tab_complete:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_COMPLETE);
			break;
		}
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			back();
			break;
		}
	}

	private void back() {
		if (isNomalGetIn) {
			CommonUtils.finishActivity(this);
			return;
		}
		Intent intent = null;
		int memberType = Integer.parseInt(CommonUtils.getMemberType(context));
		switch (memberType) {
		case 2:// 个人车主
			intent = new Intent(MyOrderManagerActivity.this,
					PersonalInformationActivity.class);
			break;
		case 3:// 货主
		case 1:// 企业
			intent = new Intent(MyOrderManagerActivity.this,
					PersonalInformation2Activity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
		CommonUtils.finishActivity(this);
	}

	private void jump2PistionTab(int position) {
		if (position == -1)
			return;
		switch (position) {
		case R.id.contingent_car_owner:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_BARGAIN);
			radio_tab_bargain.setChecked(true);
			break;
		case R.id.contingent_goods_owner:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_EXECUTE);
			radio_tab_execute.setChecked(true);
			break;
		case R.id.contingent_evaluate_owner:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_EVALUTE);
			radio_tab_evaluate.setChecked(true);
			break;
		case R.id.contingent_complete_owner:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_COMPLETE);
			radio_tab_complete.setChecked(true);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			back();
			return false;
		}
		return super.dispatchKeyEvent(event);
	}

}
