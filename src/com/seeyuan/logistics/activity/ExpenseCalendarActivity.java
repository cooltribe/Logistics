package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;

/**
 * 消费记录
 * 
 * @author Administrator
 * 
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class ExpenseCalendarActivity extends BaseActivity implements
		OnClickListener {

	private Context context;

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_expense_calender); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局

		context = getApplicationContext();
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.Personal_ExpenseCalendar_Title);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!ExpenseCalendarActivity.this.isFinishing()) {
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.RentExpenseCalendar_But:
			doMonthlyRent();

			break;
		case R.id.LocationExpenseCalendar_But:
			doLocationRent();
			break;

		case R.id.IDCardExpenseCalendar_But:
			doIDCardRent();
			break;

		default:
			break;
		}
	}

	/**
	 * 身份验证消费
	 */
	private void doIDCardRent() {
		Intent intent = new Intent(ExpenseCalendarActivity.this,
				IDCardRentActivity.class);
		startActivity(intent);
	}

	/**
	 * 精准定位消费
	 */
	private void doLocationRent() {
		Intent intent = new Intent(ExpenseCalendarActivity.this,
				LocationRentActivity.class);
		startActivity(intent);
	}

	/**
	 * 月租消费
	 */
	private void doMonthlyRent() {
		Intent intent = new Intent(ExpenseCalendarActivity.this,
				MonthlyRentActivity.class);
		startActivity(intent);
	}

}
