package com.seeyuan.logistics.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.seeyuan.logistics.R;

/**
 * 货运担保
 * 
 * @author zhazhaobao
 * 
 */
@SuppressWarnings("deprecation")
public class InsuranceActivity extends TabActivity implements OnClickListener,
		OnCheckedChangeListener {

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
	private Intent mAccountIntent;
	private Intent mAssurerIntent;
	private Intent mGoodsinfoIntent;
	private Intent mInsuranceInfoIntent;
	private Intent mTransportInfoIntent;

	private final static String TAB_TAG_ACCOUNT = "tab_tag_account";
	private final static String TAB_TAG_ASSURER = "tab_tag_assurer";
	private final static String TAB_TAG_GOODSINFO = "tab_tag_goodsinfo";
	private final static String TAB_TAG_INSURANCESINFO = "tab_tag_insurancesinfo";
	private final static String TAB_TAG_TRANSPORTINFO = "tab_tag_transportsinfo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_insurance);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
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
		defaulttitle_title_tv.setText(R.string.insurance_hint);
	}

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mAccountIntent = new Intent(this, TabInsuranceAccoundActivity.class);
		mAssurerIntent = new Intent(this, TabInsuranceAssurerActivity.class);
		mGoodsinfoIntent = new Intent(this, TabInsuranceGoodsInfoActivity.class);
		mInsuranceInfoIntent = new Intent(this,
				TabInsuranceInsuranceInfoActivity.class);
		mTransportInfoIntent = new Intent(this,
				TabInsuranceTransportInfoActivity.class);
	}

	/**
	 * 设置tab内容的intent
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_ACCOUNT,
				R.string.tab_insurance_accound, R.drawable.icon_1_n,
				mAccountIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_ASSURER,
				R.string.tab_insurance_assurer, R.drawable.icon_2_n,
				mAssurerIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_GOODSINFO,
				R.string.tab_insurance_goodsinfo, R.drawable.icon_2_n,
				mGoodsinfoIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_INSURANCESINFO,
				R.string.tab_insurance_insuranceinfo, R.drawable.icon_2_n,
				mInsuranceInfoIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_TRANSPORTINFO,
				R.string.tab_insurance_transportinfo, R.drawable.icon_2_n,
				mTransportInfoIntent));
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
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!InsuranceActivity.this.isFinishing()) {
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_tab_accountinfo:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_ACCOUNT);
			break;
		case R.id.radio_tab_assurer:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_ASSURER);
			break;
		case R.id.radio_tab_goodsinfo:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_GOODSINFO);
			break;
		case R.id.radio_tab_insuranceinfo:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_INSURANCESINFO);
			break;
		case R.id.radio_tab_transportinfo:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_TRANSPORTINFO);
			break;
		}
	}
}
