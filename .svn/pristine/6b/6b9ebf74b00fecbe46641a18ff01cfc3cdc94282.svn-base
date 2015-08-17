package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.easemob.chatuidemo.activity.ChatActivity;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.ProgressAlertDialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.CollectSourceHandler;
import com.seeyuan.logistics.datahandler.PlaceAnOrderHandler;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 货源详情
 * 
 * @author zhazhaobao
 * 
 */
public class GoodsSourceDetailActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {
	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private ImageView maintitle_comfirm_iv;

	private GoodsDto mGoodsSourceInfo;

	/**
	 * 货物名称
	 */
	private TextView GoodsSourceDetails_GoodsInfo;

	/**
	 * 从哪里来
	 */
	private TextView GoodsSourceDetails_Start;
	/**
	 * 到哪里去
	 */
	private TextView GoodsSourceDetails_End;

	/**
	 * 货物规格
	 */
	private TextView GoodsSourceDetails_GoodsStandard;

	/**
	 * 车辆信息
	 */
	private TextView GoodsSourceDetails_CarInfo;

	/**
	 * 运输方式
	 */
	private TextView GoodsSourceDetails_TransportMode;
	/**
	 * 期望运费
	 */
	private TextView GoodsSourceDetails_ExpectFreight;
	/**
	 * 货物编号
	 */
	private TextView GoodsSourceDetails_ExpectFreight_number;

	/**
	 * 联系人
	 */
	private TextView GoodsSourceDetails_Contacts;

	/**
	 * 联系电话
	 */
	private TextView GoodsSourceDetails_Tel_Text;

	/**
	 * 发货日期
	 */
	private TextView GoodsSourceDetails_PublishDate;

	/**
	 * 信用等级
	 */
	private RatingBar CarSourceDetails_Leve;

	/**
	 * 公司名称
	 */
	private TextView comany_name;

	/**
	 * 备注
	 */
	private TextView CarSourceDetails_Notes;

	private Context context;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 1000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 1001;

	private ProgressAlertDialog progressDialog;

	private LinearLayout GoodsSourceDetails_Option_Layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_goods_source_details); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		mGoodsSourceInfo = (GoodsDto) getIntent().getSerializableExtra(
				"GoodsSourceInfo");
		initView();
		CommonUtils.addActivity(this);
	}

	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;

			default:
				break;
			}
		}
	};

	private void showProgress() {
		if (progressDialog == null) {
			progressDialog = new ProgressAlertDialog(this);
		} else {
			progressDialog.show();
		}
	}

	private void dismissProgress() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.GoodsSourceDetails_Tel_But:
			CommonUtils.makeingCalls(context, mGoodsSourceInfo.getmPhone());
			break;
		case R.id.GoodsSourceDetails_Complaints_But:
			doPlaceAnOrder();
			break;
		case R.id.GoodsSourceDetails_Chat_But:
			doChat();
			break;

		default:
			break;
		}
	}

	/**
	 * 发起聊天
	 */
	private void doChat() {

		if (mGoodsSourceInfo.getContactName().equals(
				CommonUtils.getUserName(context)))
			ToastUtil.show(context, "不可以和自己聊天");
		else {
			// 进入聊天页面
			Intent intent = new Intent(GoodsSourceDetailActivity.this,
					ChatActivity.class);
			intent.putExtra("userId", mGoodsSourceInfo.getContactName());
			startActivity(intent);
		}

	}

	/**
	 * 下单
	 */
	private void doPlaceAnOrder() {
		Intent intent = new Intent(GoodsSourceDetailActivity.this,
				PlaceAnGoodsOrderActivity.class);
		intent.putExtra("goodsInfo", mGoodsSourceInfo);
		startActivity(intent);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.GoodsSourceDetails_Title);

		GoodsSourceDetails_GoodsInfo = (TextView) findViewById(R.id.GoodsSourceDetails_GoodsInfo);
		GoodsSourceDetails_GoodsInfo.setText(TextUtils.isEmpty(mGoodsSourceInfo
				.getGoodsName()) ? "未知" : mGoodsSourceInfo.getGoodsName());

		GoodsSourceDetails_Start = (TextView) findViewById(R.id.GoodsSourceDetails_Start);
		GoodsSourceDetails_Start.setText(TextUtils.isEmpty(mGoodsSourceInfo
				.getSetout()) ? "未知" : mGoodsSourceInfo.getSetout());

		GoodsSourceDetails_End = (TextView) findViewById(R.id.GoodsSourceDetails_End);
		GoodsSourceDetails_End.setText(TextUtils.isEmpty(mGoodsSourceInfo
				.getDestination()) ? "未知" : mGoodsSourceInfo.getDestination());

		GoodsSourceDetails_GoodsStandard = (TextView) findViewById(R.id.GoodsSourceDetails_GoodsStandard);
		// 货物格式缺少
		// GoodsSourceDetails_GoodsStandard.setText(mGoodsSourceInfo
		// .getGoodsStandard());

		GoodsSourceDetails_CarInfo = (TextView) findViewById(R.id.GoodsSourceDetails_CarInfo);
		GoodsSourceDetails_CarInfo.setText(TextUtils.isEmpty(mGoodsSourceInfo
				.getVehType()) ? "未知" : mGoodsSourceInfo.getVehType());

		GoodsSourceDetails_TransportMode = (TextView) findViewById(R.id.GoodsSourceDetails_TransportMode);
		GoodsSourceDetails_TransportMode.setText(TextUtils
				.isEmpty(mGoodsSourceInfo.getDisMode()) ? "未知"
				: mGoodsSourceInfo.getDisMode());

		GoodsSourceDetails_ExpectFreight = (TextView) findViewById(R.id.GoodsSourceDetails_ExpectFreight);
		GoodsSourceDetails_ExpectFreight.setText(null == mGoodsSourceInfo
				.getCost() ? "未知" : String.valueOf(mGoodsSourceInfo.getCost()));

		GoodsSourceDetails_ExpectFreight_number = (TextView) findViewById(R.id.GoodsSourceDetails_ExpectFreight_number);
		// GoodsSourceDetails_ExpectFreight_number.setText(mGoodsSourceInfo
		// .getGoodsNumber());
		GoodsSourceDetails_Contacts = (TextView) findViewById(R.id.GoodsSourceDetails_Contacts);
		GoodsSourceDetails_Contacts.setText(TextUtils.isEmpty(mGoodsSourceInfo
				.getContactName()) ? "未知" : mGoodsSourceInfo.getContactName());

		GoodsSourceDetails_Tel_Text = (TextView) findViewById(R.id.GoodsSourceDetails_Tel_Text);
		GoodsSourceDetails_Tel_Text.setText(CommonUtils.encryptionString(
				mGoodsSourceInfo.getmPhone(), 4));

		GoodsSourceDetails_PublishDate = (TextView) findViewById(R.id.GoodsSourceDetails_PublishDate);
		GoodsSourceDetails_PublishDate.setText(null == mGoodsSourceInfo
				.getDeliveryDateF()
				&& null == mGoodsSourceInfo.getDeliveryDateT() ? "未知"
				: CommonUtils.parserData(mGoodsSourceInfo.getDeliveryDateF())
						+ "→"
						+ CommonUtils.parserData(mGoodsSourceInfo
								.getDeliveryDateT()));

		CarSourceDetails_Leve = (RatingBar) findViewById(R.id.CarSourceDetails_Leve);
		// 信用等级，暂无
		// CarSourceDetails_Leve.setRating(mGoodsSourceInfo.getStar());

		comany_name = (TextView) findViewById(R.id.comany_name);
		comany_name
				.setText(TextUtils.isEmpty(mGoodsSourceInfo.getCompanyName()) ? "未知"
						: mGoodsSourceInfo.getCompanyName());

		CarSourceDetails_Notes = (TextView) findViewById(R.id.CarSourceDetails_Notes);
		CarSourceDetails_Notes.setText(TextUtils.isEmpty(mGoodsSourceInfo
				.getRemark()) ? "暂无" : mGoodsSourceInfo.getRemark());

		GoodsSourceDetails_Option_Layout = (LinearLayout) findViewById(R.id.GoodsSourceDetails_Option_Layout);
		GoodsSourceDetails_Option_Layout
				.setVisibility(setOptionVisible() ? View.VISIBLE : View.GONE);

	}

	private boolean setOptionVisible() {
		int memberType = Integer.parseInt(CommonUtils.getMemberType(context));
		boolean isVisible = false;
		switch (memberType) {
		case 2:// 个人车主
			isVisible = true;
			break;
		case 3:// 企业
		case 1:// 货主
			isVisible = false;
			break;
		default:
			break;
		}
		return isVisible;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!GoodsSourceDetailActivity.this.isFinishing()) {
				finish();
			}
			break;
		case R.id.maintitle_comfirm_iv:
			doCollectSource();
			break;

		default:
			break;
		}
	}

	/**
	 * 收藏货源
	 */
	private void doCollectSource() {
		// 搜藏货源借口等待调试
		CollectSourceHandler dataHandler = new CollectSourceHandler(context,
				"1");
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		switch (resultCode) {
		case NetWork.COLLECT_SOURCE_OK:
			ToastUtil.show(context,
					getResources().getString(R.string.collected_hint));
			maintitle_comfirm_iv.setBackgroundResource(R.drawable.collect_icon);
			break;
		case NetWork.COLLECT_SOURCE_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

}
