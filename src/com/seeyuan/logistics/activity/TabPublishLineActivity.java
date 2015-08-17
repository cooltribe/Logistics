package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.PublishLineSourceHandler;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarTypeInfo;
import com.seeyuan.logistics.entity.LineSourceInfo;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 发布专线
 * 
 * @author zhazhaobao
 * 
 */
/**
 * @author Administrator
 * 
 */
public class TabPublishLineActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 始发地
	 */
	private Button publish_line_from;

	/**
	 * 目的地
	 */
	private Button publish_line_to;

	/**
	 * 始发地
	 */
	private final int REFRESH_FROM = 1001;

	/**
	 * 目的地
	 */
	private final int REFRESH_TO = 1002;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_publish_line); // 软件activity的布局
		context = getApplicationContext();
		initView();
	}

	@Override
	public void initView() {
		publish_line_from = (Button) findViewById(R.id.publish_line_from);
		publish_line_to = (Button) findViewById(R.id.publish_line_to);

	}

	private Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_FROM:
				publish_line_from.setText(msg.obj.toString());
				break;
			case REFRESH_TO:
				publish_line_to.setText(msg.obj.toString());
				break;

			default:
				break;
			}
		};
	};

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.publish_line_to:
			doLineFrom();
			break;
		case R.id.publish_line_from:
			doLineTo();
			break;
		case R.id.publish_line_ensure:
			String result = isCanPublisLine();
			if (result.equalsIgnoreCase("成功")) {
				doSubmitPulishLine();
			} else {
				ToastUtil.show(context, result);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 提交发布专线
	 */
	private void doSubmitPulishLine() {

		// PublishLineSourceHandler dataHandler = new PublishLineSourceHandler(
		// context, lineSourceInfo);
		// dataHandler.setOnDataReceiveListener(this);
		// dataHandler.startNetWork();
	}

	/**
	 * 是否可以发送专线
	 * 
	 * @return
	 */
	private String isCanPublisLine() {
		Filter lineFrom = new LineFrom();
		Filter lineTo = new LineTo();
		lineFrom.setNext(lineTo);

		String result = lineFrom.doFilter(publish_line_from.getText()
				.toString(), publish_line_to.getText().toString());
		return result;
	}

	abstract class Filter {

		Filter next = null;

		public Filter getNext() {

			return next;

		}

		public void setNext(Filter next) {

			this.next = next;

		}

		public String doFilter(String lineFrom, String lineTo) {

			if (next == null) {
				return "成功";
			} else
				return next.doFilter(lineFrom, lineTo);

		}

	}

	class LineFrom extends Filter {
		@Override
		public String doFilter(String lineFrom, String lineTo) {
			if (TextUtils.isEmpty(lineFrom)) {
				return "请输入正确的始发地";
			} else
				return super.doFilter(lineFrom, lineTo);
		}
	}

	class LineTo extends Filter {
		@Override
		public String doFilter(String lineFrom, String lineTo) {
			if (TextUtils.isEmpty(lineTo)) {
				return "请输入正确的目的地";
			} else
				return super.doFilter(lineFrom, lineTo);
		}
	}

	/**
	 * 始发地
	 */
	private void doLineFrom() {

	}

	/**
	 * 目的地
	 */
	private void doLineTo() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REFRESH_FROM:
			publish_line_from.setText(data.getStringExtra("place"));
			break;
		case REFRESH_TO:
			publish_line_to.setText(data.getStringExtra("place"));
			break;

		default:
			break;
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		switch (resultCode) {
		case NetWork.PUBLISH_LINE_SOURCE_OK:

			break;
		case NetWork.PUBLISH_LINE_SOURCE_ERROR:

			break;

		default:
			break;
		}
	}

}