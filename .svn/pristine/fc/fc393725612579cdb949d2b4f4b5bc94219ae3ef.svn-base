package com.seeyuan.logistics.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.SubmitEvaluateHandler;
import com.seeyuan.logistics.entity.EvaluateInfo;

/**
 * 评价
 * 
 * @author zhazhaobao
 * 
 */
public class EvaluteActivity extends BaseActivity implements OnClickListener,
		OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 赞
	 */
	private Button review_up_btn;
	/**
	 * 赞，个数
	 */
	private int evalute_good_no = 0;

	/**
	 * 踩
	 */
	private Button review_down_btn;
	/**
	 * 踩，个数
	 */
	private int evalute_bad_no = 0;

	private Context context;

	private EditText content_ed;

	private RatingBar ratingBar_evaluate;

	private boolean isPraise = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_evaluate); // 软件activity的布局
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
		defaulttitle_title_tv.setText(R.string.Evaluate_title);

		review_up_btn = (Button) findViewById(R.id.review_up_btn);
		review_up_btn.setText(String.format(
				getResources().getString(R.string.evalute_good_hint), 0));
		review_down_btn = (Button) findViewById(R.id.review_down_btn);
		review_down_btn.setText(String.format(
				getResources().getString(R.string.evalute_bad_hint), 0));
		content_ed = (EditText) findViewById(R.id.content_ed);

		ratingBar_evaluate = (RatingBar) findViewById(R.id.ratingBar_evaluate);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!EvaluteActivity.this.isFinishing()) {
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
		case R.id.review_up_btn:
			doEvaluteGood();
			break;
		case R.id.review_down_btn:
			doEvaluteBad();
			break;
		case R.id.PublishDriverCreditDetail_But:
			doSubmitEvalute();
			break;

		default:
			break;
		}
	}

	/**
	 * 提交评价
	 */
	private void doSubmitEvalute() {
		EvaluateInfo evaluateInfo = new EvaluateInfo();
		evaluateInfo.setContent(content_ed.getText().toString());
		evaluateInfo.setStar(String.valueOf(ratingBar_evaluate.getRating()));
		evaluateInfo.setPraise(isPraise);
		SubmitEvaluateHandler dataHandler = new SubmitEvaluateHandler(context,
				evaluateInfo);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 点赞
	 */
	private void doEvaluteGood() {
		evalute_good_no = ++evalute_good_no > 0 ? evalute_good_no : 0;
		review_up_btn.setText(String.format(
				getResources().getString(R.string.evalute_good_hint),
				evalute_good_no));
		evalute_bad_no = --evalute_bad_no > 0 ? evalute_bad_no : 0;
		review_down_btn.setText(String.format(
				getResources().getString(R.string.evalute_bad_hint),
				evalute_bad_no));
		review_up_btn.setEnabled(false);
		review_down_btn.setEnabled(true);
		isPraise = true;
	}

	/**
	 * 踩
	 */
	private void doEvaluteBad() {
		evalute_bad_no = ++evalute_bad_no > 0 ? evalute_bad_no : 0;
		review_down_btn.setText(String.format(
				getResources().getString(R.string.evalute_bad_hint),
				evalute_bad_no));
		evalute_good_no = --evalute_good_no > 0 ? evalute_good_no : 0;
		review_up_btn.setText(String.format(
				getResources().getString(R.string.evalute_good_hint),
				evalute_good_no));
		review_down_btn.setEnabled(false);
		review_up_btn.setEnabled(true);
		isPraise = false;
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		switch (resultCode) {
		case NetWork.SUBMIT_EVALUATE_OK:

			break;
		case NetWork.SUBMIT_EVALUATE_ERROR:

			break;

		default:
			break;
		}
	}

}
