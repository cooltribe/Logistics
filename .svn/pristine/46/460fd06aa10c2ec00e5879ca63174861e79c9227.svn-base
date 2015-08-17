package com.seeyuan.logistics.customview;

import android.content.Context;
import android.content.DialogInterface.OnKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.location.f;
import com.seeyuan.logistics.R;

/**
 * 自定义单选AlertDialog
 * 
 * @author zhazhaobao
 * 
 */
public class UpdateAppAlertDlialog {

	private Context context;
	private android.app.AlertDialog alertDialog;

	private TextView single_select_title;
	private Button update_app_ensure;
	private Button update_app_cancel;
	private ProgressBar update_progress;
	private TextView update_content;

	public UpdateAppAlertDlialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		alertDialog = new android.app.AlertDialog.Builder(context).create();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = alertDialog.getWindow();
		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.show();
		window.setContentView(R.layout.custom_view_update_app_alertdialog);
		single_select_title = (TextView) window
				.findViewById(R.id.single_select_title);
		update_app_ensure = (Button) window
				.findViewById(R.id.update_app_ensure);
		update_app_cancel = (Button) window
				.findViewById(R.id.update_app_cancel);
		update_progress = (ProgressBar) window
				.findViewById(R.id.update_progress);
		update_progress.setMax(100);
		update_content = (TextView) window.findViewById(R.id.update_content);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		alertDialog.dismiss();
	}

	/**
	 * title提示
	 * 
	 * @param content
	 */
	public void setTitleContent(String content) {
		single_select_title.setText(content);
	}

	/**
	 * 返回键监听
	 * 
	 * @param listener
	 */
	public void setOnKeyListener(OnKeyListener listener) {
		alertDialog.setOnKeyListener(listener);
	}

	/**
	 * 添加确认键监听
	 * 
	 * @param listener
	 */
	public void setEnsureButtonListener(OnClickListener listener) {
		update_app_ensure.setOnClickListener(listener);
	}

	/**
	 * 添加返回键监听
	 * 
	 * @param listener
	 */
	public void setCancelButtonListener(OnClickListener listener) {
		update_app_cancel.setOnClickListener(listener);
	}

	public void setCancelButtonVisible(boolean visible) {
		update_app_cancel.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置主题内容
	 * 
	 * @param content
	 */
	public void setUpdateContent(String content) {
		update_content.setText(content);
	}

	public void setProgress(int progress) {
		update_progress.setProgress(progress);
	}

	public void setButtonEnable(boolean enable) {
		update_app_ensure.setEnabled(enable ? true : false);
		update_app_cancel.setEnabled(enable ? true : false);
	}

}
