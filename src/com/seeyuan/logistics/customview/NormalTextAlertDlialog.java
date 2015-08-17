package com.seeyuan.logistics.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 自定义文字提示AlertDialog
 * 
 * @author zhazhaobao
 * 
 */
public class NormalTextAlertDlialog {

	private Context context;
	private Dialog alertDialog;
	private TextView single_select_title;
	private TextView hint_text;
	private Button order_price_ensure;
	private Button order_price_cancel;

	public NormalTextAlertDlialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		alertDialog = new Dialog(context);
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.custom_view_normal_text_alertdialog);
		window.setBackgroundDrawable(new ColorDrawable(0));
		single_select_title = (TextView) window
				.findViewById(R.id.single_select_title);
		hint_text = (TextView) window.findViewById(R.id.order_price);
		order_price_ensure = (Button) window
				.findViewById(R.id.order_price_ensure);
		order_price_cancel = (Button) window
				.findViewById(R.id.order_price_cancel);

	}

	public void setTitle(String title) {
		single_select_title.setText(title);
	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		alertDialog.dismiss();
	}

	public void setEidtTextListener(TextWatcher watcher) {
		hint_text.addTextChangedListener(watcher);
	}

	public void setLeftButtonListener(OnClickListener listener) {
		order_price_ensure.setOnClickListener(listener);
	}

	public void setRightButtonListener(OnClickListener listener) {
		order_price_cancel.setOnClickListener(listener);
	}

	public String getEditContent() {
		return TextUtils.isEmpty(hint_text.getText()) ? "" : hint_text
				.getText().toString();
	}

	public void setContent(String content) {
		hint_text.setText(content);
	}
}
