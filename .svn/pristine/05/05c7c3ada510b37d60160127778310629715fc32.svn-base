package com.seeyuan.logistics.customview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.NormalTextAdapter;
import com.seeyuan.logistics.adapter.SearchCarLengthAdapter;
import com.seeyuan.logistics.adapter.SearchCarTypeAdapter;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarTypeInfo;

/**
 * 自定义单选AlertDialog
 * 
 * @author zhazhaobao
 * 
 */
public class SingleSelectAlertDlialog {

	private Context context;
	private android.app.AlertDialog alertDialog;
	private TextView single_select_title;
	private ListView single_select_content_lv;
	private LinearLayout single_select_button_layout;
	private List<CarTypeInfo> mDataList;
	private SearchCarTypeAdapter carTypeAdapter;
	private SearchCarLengthAdapter carLengthAdapter;
	private NormalTextAdapter normalAdapter;

	public SingleSelectAlertDlialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		alertDialog = new android.app.AlertDialog.Builder(context).create();
		alertDialog.show();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.custom_view_single_select_alertdialog);
		single_select_title = (TextView) window
				.findViewById(R.id.single_select_title);
		single_select_content_lv = (ListView) window
				.findViewById(R.id.single_select_content_lv);
		single_select_button_layout = (LinearLayout) window
				.findViewById(R.id.single_select_button_layout);
		mDataList = new ArrayList<CarTypeInfo>();
	}

	/**
	 * 添加liastview内容
	 */
	public void setListContentForCarType(List<CarTypeInfo> mDataList) {

		carTypeAdapter = new SearchCarTypeAdapter(mDataList, context);
		single_select_content_lv.setAdapter(carTypeAdapter);

	}

	public void setListContentForCarLength(List<CarLengthInfo> mDataList) {
		carLengthAdapter = new SearchCarLengthAdapter(mDataList, context);
		single_select_content_lv.setAdapter(carLengthAdapter);
	}

	public void setListContentForNormalText(List<String> mDataList) {
		normalAdapter = new NormalTextAdapter(mDataList, context);
		single_select_content_lv.setAdapter(normalAdapter);
	}

	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {

		single_select_content_lv.setOnItemClickListener(listener);
	}

	public void setTitle(String title) {
		single_select_title.setText(title);
	}

	/**
	 * 设置按钮
	 * 
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(String text,
			final View.OnClickListener listener) {
		Button button = new Button(context);
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		// button.setBackgroundResource(R.drawable.alertdialog_button);
		button.setText(text);
		button.setTextColor(Color.WHITE);
		button.setTextSize(20);
		button.setOnClickListener(listener);
		single_select_button_layout.addView(button);
	}

	/**
	 * 设置按钮
	 * 
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(String text,
			final View.OnClickListener listener) {
		Button button = new Button(context);
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		button.setLayoutParams(params);
		// button.setBackgroundResource(R.drawable.alertdialog_button);
		button.setText(text);
		button.setTextColor(Color.WHITE);
		button.setTextSize(20);
		button.setOnClickListener(listener);
		if (single_select_button_layout.getChildCount() > 0) {
			params.setMargins(20, 0, 0, 0);
			button.setLayoutParams(params);
			single_select_button_layout.addView(button, 1);
		} else {
			button.setLayoutParams(params);
			single_select_button_layout.addView(button);
		}

	}

	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		alertDialog.dismiss();
	}

}
