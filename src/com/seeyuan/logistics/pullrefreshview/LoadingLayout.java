package com.seeyuan.logistics.pullrefreshview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;

public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView headerImage;
	// private final ProgressBar headerProgress;
	private final ImageView headerProgress;
	private final TextView headerText;
	private TextView headerCurTime;
	private SimpleDateFormat formatter;
	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;
	private boolean isHeaderImageVisible = false;
	private boolean isHeaderCurTimeVisible = false;
	private SharedPreferences mSharedPreferences;
	private final Animation rotateAnimation, resetRotateAnimation;

	public LoadingLayout(Context context, final int mode, String releaseLabel,
			String pullLabel, String refreshingLabel,
			boolean isHeaderImageVisible, boolean isHeaderCurTimeVisible) {
		super(context);
//		mSharedPreferences = context.getSharedPreferences(
//				NewCarDefine.SHAREDPREFERENCES, Context.MODE_PRIVATE);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, this);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
//		String curTime = mSharedPreferences.getString("curTime", "");
		headerCurTime = (TextView) header
				.findViewById(R.id.pull_to_refresh_curtime);
//		headerCurTime.setText(curTime);
		headerImage = (ImageView) header
				.findViewById(R.id.pull_to_refresh_image);
		// headerProgress = (ProgressBar) header
		// .findViewById(R.id.pull_to_refresh_progress);
		formatter = new SimpleDateFormat("MM-dd HH:mm");
		headerProgress = (ImageView) header
				.findViewById(R.id.pull_to_refresh_progress);

		final Interpolator interpolator = new LinearInterpolator();
		rotateAnimation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;

		switch (mode) {
		case 0:
			break;
		case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
			headerImage.setImageResource(R.drawable.pull_refresh_down);
			break;
		case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
			headerImage.setImageResource(R.drawable.pull_refresh_up);
			break;
		default:
			break;
		}
		this.isHeaderImageVisible = isHeaderImageVisible;
		if (isHeaderImageVisible) {
			headerImage.setVisibility(View.VISIBLE);
		} else {
			headerImage.setVisibility(View.GONE);
		}
		this.isHeaderCurTimeVisible = isHeaderCurTimeVisible;
		if (isHeaderCurTimeVisible) {
			headerCurTime.setVisibility(View.VISIBLE);
		} else {
			headerCurTime.setVisibility(View.GONE);
		}
	}

	public void reset() {
		headerText.setText(pullLabel);
		if (isHeaderImageVisible) {
			headerImage.setVisibility(View.VISIBLE);
		} else {
			headerImage.setVisibility(View.GONE);
		}
		headerProgress.setVisibility(View.GONE);
	}

	public void releaseToRefresh() {
		headerText.setText(releaseLabel);
		headerImage.setBackgroundResource(R.drawable.pull_refresh_up);
		// headerImage.clearAnimation();
		// headerImage.startAnimation(rotateAnimation);
	}

	public void releaseTofreshDown() {
		headerText.setText(releaseLabel);
		headerImage.setBackgroundResource(R.drawable.pull_refresh_down);
	}

	/**
	 * @param style
	 *            0:上拉 headerLayout，1下拉 footerLayout
	 */
	public void releaseHitRefresh(int style) {
		if (style == 0) {
			headerText.setText("下拉可以加载");
			// headerImage.setImageResource(R.drawable.pull_refresh_up);
			headerImage.setBackgroundResource(R.drawable.pull_refresh_down);
		}
		if (style == 1) {
			headerText.setText("上拉可以加载");
			headerImage.setBackgroundResource(R.drawable.pull_refresh_up);
		}
		// headerImage.clearAnimation();
		// headerImage.startAnimation(rotateAnimation);
	}

	public void setPullLabel(String pullLabel) {
		this.pullLabel = pullLabel;
	}

	public void refreshing() {
		headerText.setText(refreshingLabel);
		// headerImage.clearAnimation();
		headerImage.setVisibility(View.GONE);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		headerCurTime.setText("上次更新:" + formatter.format(curDate));
//		Editor editor = mSharedPreferences.edit();
//		editor.putString("curTime", "上次更新:" + formatter.format(curDate));
//		editor.commit();
		headerProgress.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable = (AnimationDrawable) headerProgress
				.getBackground();
		animationDrawable.start();
	}

	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	public void pullToRefresh() {
		headerText.setText(pullLabel);
		// headerImage.clearAnimation();
		// headerImage.startAnimation(resetRotateAnimation);
	}

	public void setTextColor(int color) {
		headerText.setTextColor(color);
	}

}
