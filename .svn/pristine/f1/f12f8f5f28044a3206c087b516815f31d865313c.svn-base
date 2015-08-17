package com.seeyuan.logistics.customview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.seeyuan.logistics.R;

public class ProgressAlertDialog {

	private Context context;
	private android.app.AlertDialog alertDialog;
	private ImageView prgress_bar;

	public ProgressAlertDialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		alertDialog = new android.app.AlertDialog.Builder(context).create();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = alertDialog.getWindow();
		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alertDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				alertDialog.dismiss();
				return false;
				//如果不允许按键操作，return true
//				return true;
			}
		});
		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
		window.setContentView(R.layout.custom_view_progress_alertdialog);

		prgress_bar = (ImageView) window.findViewById(R.id.progress_bar);
		AnimationDrawable animationDrawable = (AnimationDrawable) prgress_bar
				.getBackground();
		animationDrawable.start();
		
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		alertDialog.dismiss();
	}

	public void show() {
		alertDialog.show();
	}

}
