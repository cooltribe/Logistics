package com.seeyuan.logistics.customview;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.seeyuan.logistics.R;

/**
 * 自定义单选AlertDialog
 * 
 * @author zhazhaobao
 * 
 */
public class SingleSelectAlertDlialog2 {

	private Activity activity;
	private LinearLayout single_select_button_layout;
	private Button first_btn;
	private Button secend_btn;
	private Button third_btn;
	private View convertView;
	private boolean isDialogVisible = false;
	private ViewGroup mViewGroup;

	public SingleSelectAlertDlialog2(Activity activity) {
		// this.context = context;
		this.activity = activity;
		initView();
	}

	private void initView() {
		convertView = LayoutInflater.from(activity).inflate(
				R.layout.custom_view_single_select_alertdialog2, null);
		android.view.WindowManager.LayoutParams params = new android.view.WindowManager.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		single_select_button_layout = (LinearLayout) convertView
				.findViewById(R.id.single_select_button_layout);
		first_btn = (Button) convertView
				.findViewById(R.id.single_select_alertdialog2_first_btn);
		secend_btn = (Button) convertView
				.findViewById(R.id.single_select_alertdialog2_secend_btn);
		third_btn = (Button) convertView
				.findViewById(R.id.single_select_alertdialog2_third_btn);
		activity.addContentView(convertView, params);
		mViewGroup = ((ViewGroup) convertView.getParent());
	}

	/**
	 * 为第一个按钮添加文字
	 * 
	 * @param content
	 */
	public void setFirstButtonContent(String content) {
		first_btn.setText(content);
	}

	/**
	 * 为第一个按钮添加监听
	 * 
	 * @param listener
	 */
	public void setFirstButtonOnClickListener(OnClickListener listener) {
		first_btn.setOnClickListener(listener);
	}

	/**
	 * 为第二个按钮添加文字
	 * 
	 * @param content
	 */
	public void setSecendButtonContent(String content) {
		secend_btn.setText(content);
	}

	/**
	 * 为第二个按钮添加监听
	 * 
	 * @param listener
	 */
	public void setSecendButtonOnClickListener(OnClickListener listener) {
		secend_btn.setOnClickListener(listener);
	}

	/**
	 * 为第三个按钮添加文字
	 * 
	 * @param content
	 */
	public void setThirdButtonContent(String content) {
		third_btn.setText(content);
	}

	/**
	 * 为第三个按钮添加监听
	 * 
	 * @param listener
	 */
	public void setThirdButtonOnClickListener(OnClickListener listener) {
		third_btn.setOnClickListener(listener);
	}

	private Handler myHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			single_select_button_layout.setVisibility(View.GONE);
			isDialogVisible = false;
			mViewGroup.removeView(convertView);
			mViewGroup.setEnabled(true);
		};
	};

	/**
	 * 关闭对话框
	 */
	public void dismiss() {

		single_select_button_layout.setAnimation(AnimationUtils.loadAnimation(
				activity, R.anim.down_out));
		myHandler.sendEmptyMessageDelayed(0, 450);
	}

	/**
	 * 显示对话框
	 */
	public void showDialog() {
		isDialogVisible = true;
		mViewGroup.setEnabled(false);
		single_select_button_layout.setVisibility(View.VISIBLE);
		single_select_button_layout.setAnimation(AnimationUtils.loadAnimation(
				activity, R.anim.down_in));
	}

	public boolean isDialogVisible() {
		return isDialogVisible;
	}

	public void setDialogVisible(boolean isDialogVisible) {
		this.isDialogVisible = isDialogVisible;
	}

}
