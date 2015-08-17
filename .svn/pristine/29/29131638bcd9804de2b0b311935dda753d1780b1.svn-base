package com.seeyuan.logistics.customview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;

public class ImageAlertDlialog {

	private Context context;
	private android.app.AlertDialog alertDialog;
	private RemoteImageView imageView;
	private String imagePath;

	public ImageAlertDlialog(Context context, String imagePath, String title) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.imagePath = imagePath;
		alertDialog = new android.app.AlertDialog.Builder(context).create();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = alertDialog.getWindow();
//		window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
		window.setContentView(R.layout.custom_view_image_alertdialog);

		imageView = (RemoteImageView) window.findViewById(R.id.order_goodslist);
		imageView.draw(this.imagePath, ConstantPool.DEFAULT_ICON_PATH, false,
				false);

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
