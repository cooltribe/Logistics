package com.seeyuan.logistics.activity;

import android.app.TabActivity;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
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
 * 信息发布
 * 
 * @author zhazhaobao
 * 
 */
@SuppressWarnings("deprecation")
public class InformationPublishActivity extends TabActivity implements
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
	private Intent mCarIntent;
	private Intent mGoodsIntent;
	private Intent mLineIntent;

	private final String TAB_TAG_CAR = "tab_tag_car";
	private final String TAB_TAG_GOODS = "tab_tag_goods";
	private final String TAB_TAG_LINE = "tab_tag_line";

	private RadioButton radio_tab_car, radio_tab_goods, radio_tab_line;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_informationpublish);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		initView();
		prepareIntent();
		setupIntent();
		setRadioButtonVisible();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.information_publish_hint);

		radio_tab_car = (RadioButton) findViewById(R.id.radio_tab_car);
		radio_tab_goods = (RadioButton) findViewById(R.id.radio_tab_goods);
		radio_tab_line = (RadioButton) findViewById(R.id.radio_tab_line);
	}

	private void setRadioButtonVisible() {
		int memberType = Integer.parseInt(CommonUtils
				.getMemberType(getApplicationContext()));
		switch (memberType) {
		case 2:
			radio_tab_car.setChecked(true);
			radio_tab_goods.setVisibility(View.GONE);
			break;
		case 1:
			radio_tab_goods.setChecked(true);
			radio_tab_car.setVisibility(View.GONE);
			break;
		default:
			break;
		}

	}

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mCarIntent = new Intent(this, TabPublishCarActivity.class);
		mGoodsIntent = new Intent(this, TabPublishGoodsActivity.class);
		mGoodsIntent.putExtra("tag", 1);
		mLineIntent = new Intent(this, TabPublishLineActivity.class);
	}

	/**
	 * 设置tab内容的intent
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_CAR, R.string.tab_publish_car,
				R.drawable.icon_1_n, mCarIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_GOODS,
				R.string.tab_publish_goods, R.drawable.icon_2_n, mGoodsIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_LINE,
				R.string.tab_publish_line, R.drawable.icon_2_n, mLineIntent));
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
		case R.id.radio_tab_car:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_CAR);
			break;
		case R.id.radio_tab_goods:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_GOODS);
			break;
		case R.id.radio_tab_line:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_LINE);
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(InformationPublishActivity.this);
			break;

		default:
			break;
		}
	}
}
