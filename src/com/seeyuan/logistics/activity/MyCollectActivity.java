package com.seeyuan.logistics.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;

@SuppressWarnings("deprecation")
public class MyCollectActivity extends TabActivity implements OnClickListener,
		OnCheckedChangeListener {

	private RadioGroup mainTab;
	private TabHost mTabHost;

	// 内容Intent
	private Intent mCarIntent;
	private Intent mGoodsIntent;

	private final String TAB_TAG_CAR = "tab_tag_car";
	private final String TAB_TAG_GOODS = "tab_tag_goods";

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_collect);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		mainTab = (RadioGroup) findViewById(R.id.MyCollect_RadioGroup);
		mainTab.setOnCheckedChangeListener(this);

		initView();

		prepareIntent();
		setupIntent();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.MyCollect_Title);
	}

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mCarIntent = new Intent(this, MyCollectCarSourceActivity.class);
		mGoodsIntent = new Intent(this, MyCollectGoodsSourceActivity.class);
	}

	/**
	 * 设置tab内容的intent
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_CAR,
				R.string.MyCollect_CarSource_Title, R.drawable.default_img,
				mCarIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_GOODS,
				R.string.MyCollect_GoodsSource_Title, R.drawable.default_img,
				mGoodsIntent));
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
		case R.id.radio_tab_around_car:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_CAR);
			break;
		case R.id.radio_tab_around_goods:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_GOODS);
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(MyCollectActivity.this);
			break;

		default:
			break;
		}
	}

}
