package com.seeyuan.logistics.activity;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.seeyuan.logistics.R;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class MainActivityForActionBar extends TabActivity implements
		OnCheckedChangeListener {
	private RadioGroup mainTab;
	private TabHost mTabHost;

	// 内容Intent
	private Intent mHomeIntent;
	private Intent mAroundIntent;

	private final static String TAB_TAG_HOME = "tab_tag_home";
	private final static String TAB_TAG_AROUND = "tab_tag_around";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		forceShowOverflowMenu();
		initActionBar();
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		prepareIntent();
		setupIntent();
	}

	/**
	 * 准备tab的内容Intent
	 */
	private void prepareIntent() {
		mHomeIntent = new Intent(this, HomePageActivity.class);
		mAroundIntent = new Intent(this, AroundActivity.class);
	}

	/**
	 * 设置tab内容的intent
	 */
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.main_home,
				R.drawable.icon_1_n, mHomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_AROUND, R.string.main_around,
				R.drawable.icon_2_n, mAroundIntent));
	}

	/**
	 * 初始化actionBar
	 */
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.show();
	}
	
	private void forceShowOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			Toast.makeText(this, "Menu Item refresh selected",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_about:
			Toast.makeText(this, "Menu Item about selected", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.action_edit:
			Toast.makeText(this, "Menu Item edit selected", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.action_search:
			Toast.makeText(this, "Menu Item search selected",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_help:
			Toast.makeText(this, "Menu Item  settings selected",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
		case R.id.radio_homepage:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			break;
		case R.id.radio_around:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_AROUND);
			break;
		}
	}

}
