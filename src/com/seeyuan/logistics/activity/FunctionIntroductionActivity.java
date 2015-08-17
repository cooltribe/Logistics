package com.seeyuan.logistics.activity;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomButtonsController;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 功能介绍
 * 
 * @author zhazhaobao
 * 
 */
public class FunctionIntroductionActivity extends BaseActivity implements
		OnClickListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	/**
	 * 内容展示webview
	 */
	private WebView web_content;

	private String WEB_PATH = "http://192.168.2.32:8080/ep";

	private boolean isLoadResources = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_product_help);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
		initWebView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.function_update);

		web_content = (WebView) findViewById(R.id.web_content);

	}

	/**
	 * 初始化WebView
	 */
	private void initWebView() {
		web_content.loadUrl(WEB_PATH);
		web_content.setBackgroundColor(Color.WHITE);
		web_content.getSettings().setDefaultTextEncodingName("UTF-8");
		web_content.getSettings().setSupportZoom(true);
		web_content.getSettings().supportMultipleWindows();
		web_content.getSettings().setJavaScriptEnabled(true);
		web_content.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		web_content.getSettings().setUseWideViewPort(true);
		// 自适应屏幕
		web_content.getSettings().setLayoutAlgorithm(
				LayoutAlgorithm.SINGLE_COLUMN);
		web_content.getSettings().setLoadWithOverviewMode(true);
		web_content.clearView();

		// WebViewClient负责截获并修改加载网页过程中的各种事件。﻿首先给mWebView设置一个新的WebViewClient，
		// 然后重写函数shouldOverrideUrlLoading﻿。这么做的原因保证点击WebView插件中的Url链接的时候，
		// 仍然是在WebView插件中显示页面，而不是调用系统的网络浏览器。
		web_content.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			// 以下是在WebViewClient中，截获并且修改其它的事件行为的代码。
			// 例子代码中截获的事件包括网页加载前，加载后，错误，重复提交，加载资源等。
			// 比如，发生网页错误的时候，可以用自己的错误信息取代浏览器插件的错误提示。
			@Override
			public void onLoadResource(WebView view, String url) {
				if (!isLoadResources) {
					return;
				} else {
					super.onLoadResource(view, url);
				}
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				ToastUtil.show(context, "网页错误: " + errorCode + " 网页不可用");
			}

			public void onFormResubmission(WebView view, Message dontResend,
					Message resend) {

				ToastUtil.show(context, "不可重复提交，按Back回到上级网页");
			}

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
			}

			public void onPageFinished(WebView view, String url) {
			}
		});
		// WebChromeClient负责处理Javascript的对话框，网站图标，加载进度条等。﻿下面的代码，在Activity上添加一个加载网页的进度条
		web_content.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				FunctionIntroductionActivity.this.setProgress(progress * 100);
			}
		});
	}

	/**
	 * 实现放大缩小控件隐藏
	 * 
	 * @param view
	 */
	public void setZoomControlGone(View view, boolean isVisible) {
		Class classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(
					isVisible ? View.VISIBLE : View.GONE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.web_back_button:
			if (web_content.canGoBack())
				web_content.goBack();
			break;
		case R.id.web_forward_button:
			if (web_content.canGoForward())
				web_content.goForward();
			break;
		case R.id.web_refresh_button:
			web_content.reload();
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(FunctionIntroductionActivity.this);
			break;

		default:
			break;
		}
	}
}
