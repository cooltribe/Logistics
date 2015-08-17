package com.seeyuan.logistics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContact;
import com.easemob.chat.EMGroup;
import com.easemob.chatuidemo.activity.ChatActivity;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.CollectSourceHandler;
import com.seeyuan.logistics.entity.CarSourceInfo;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 车源详情
 * 
 * @author zhazhaobao
 * 
 */
public class CarSourceDetailActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 收藏
	 */
	private ImageView mark_iv;

	private Context context;

	private RouteDto carSourceInfo;

	/**
	 * 信用标普
	 */
	private RatingBar carSourceDetails_Level;

	/**
	 * 储
	 */
	private ImageView CarSourceDetails_Save;

	/**
	 * 公
	 */
	private ImageView CarSourceDetails_Notar;

	/**
	 * 车牌号码
	 */
	private TextView CarSourceDetails_CarPlateNumber;

	/**
	 * 车辆信息
	 */
	private TextView CarSourceDetails_CarInfo;

	/**
	 * 车辆状态
	 */
	private TextView CarSourceDetails_CarStatus;

	/**
	 * 车源类型
	 */
	private TextView CarSourceDetails_CarSourceType;

	/**
	 * 当前位置
	 */
	private TextView CarSourceDetails_Location;

	/**
	 * 当前位置,定位时间
	 */
	private TextView CarSourceDetails_LocationTime;

	/**
	 * 期望流向
	 */
	private TextView CarSourceDetails_ExpectedFlow;

	/**
	 * 联系人
	 */
	private TextView CarSourceDetails_Contacts;

	/**
	 * 联系电话
	 */
	private TextView CarSourceDetails_Tel;

	/**
	 * 备注
	 */
	private TextView CarSourceDetails_Notes;

	/**
	 * 公证数
	 */
	private TextView CarSourceDetails_Credit;

	private LinearLayout CarSourceDetails_Button_Normal_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_car_source_details);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		carSourceInfo = (RouteDto) getIntent().getSerializableExtra(
				"carSourceInfo");
		initView();

	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.CarSourceDetails_Title);

		mark_iv = (ImageView) findViewById(R.id.maintitle_comfirm_iv);
		mark_iv.setVisibility(View.GONE);
		mark_iv.setBackgroundResource(R.drawable.collect_icon2);
		mark_iv.setOnClickListener(this);

		carSourceDetails_Level = (RatingBar) findViewById(R.id.CarSourceDetails_Leve);
		// carSourceDetails_Level.setRating(carSourceInfo.getStar());

		CarSourceDetails_Save = (ImageView) findViewById(R.id.CarSourceDetails_Save);
		// CarSourceDetails_Save
		// .setVisibility(carSourceInfo.isSave() ? View.VISIBLE
		// : View.GONE);

		CarSourceDetails_Notar = (ImageView) findViewById(R.id.CarSourceDetails_Notar);
		// CarSourceDetails_Notar
		// .setVisibility(carSourceInfo.isNotary() ? View.VISIBLE
		// : View.GONE);

		CarSourceDetails_CarPlateNumber = (TextView) findViewById(R.id.CarSourceDetails_CarPlateNumber);
		CarSourceDetails_CarPlateNumber.setText(CommonUtils.encryptionString(
				carSourceInfo.getVehicleNum(), 2));

		CarSourceDetails_CarInfo = (TextView) findViewById(R.id.CarSourceDetails_CarInfo);
		CarSourceDetails_CarInfo.setText(carSourceInfo.getVehType() + " "
				+ carSourceInfo.getVehLegth() + "米  "
				+ carSourceInfo.getCarCapacity() + "吨");

		CarSourceDetails_CarStatus = (TextView) findViewById(R.id.CarSourceDetails_CarStatus);
		CarSourceDetails_CarStatus.setText(carSourceInfo.getStatus());

		CarSourceDetails_CarSourceType = (TextView) findViewById(R.id.CarSourceDetails_CarSourceType);
//		CarSourceDetails_CarSourceType
//				.setText(carSourceInfo.getCarSourceType());

		CarSourceDetails_Location = (TextView) findViewById(R.id.CarSourceDetails_Location);
		CarSourceDetails_Location.setText(TextUtils.isEmpty(carSourceInfo
				.getUserAddress()) ? "未知" : carSourceInfo.getUserAddress());

		CarSourceDetails_LocationTime = (TextView) findViewById(R.id.CarSourceDetails_LocationTime);
		// CarSourceDetails_LocationTime.setText(carSourceInfo.getLocationTime());

		CarSourceDetails_ExpectedFlow = (TextView) findViewById(R.id.CarSourceDetails_ExpectedFlow);
		CarSourceDetails_ExpectedFlow.setText(TextUtils.isEmpty(carSourceInfo
				.getDestination()) ? "未知" : carSourceInfo.getDestination());

		CarSourceDetails_Contacts = (TextView) findViewById(R.id.CarSourceDetails_Contacts);
		CarSourceDetails_Contacts.setText(TextUtils.isEmpty(carSourceInfo
				.getUserName()) ? "未知" : carSourceInfo.getUserName());

		CarSourceDetails_Tel = (TextView) findViewById(R.id.CarSourceDetails_Tel);
		CarSourceDetails_Tel.setText(CommonUtils.encryptionString(
				carSourceInfo.getUserMobile(), 4));

		CarSourceDetails_Notes = (TextView) findViewById(R.id.CarSourceDetails_Notes);
		CarSourceDetails_Notes.setText(TextUtils.isEmpty(carSourceInfo
				.getRemark()) ? "暂无" : carSourceInfo.getRemark());

		CarSourceDetails_Credit = (TextView) findViewById(R.id.CarSourceDetails_Credit);
		// CarSourceDetails_Credit.setText(String.format(
		// getResources().getString(R.string.Notary_Number),
		// carSourceInfo.getNotaryNo()));

		CarSourceDetails_Button_Normal_layout = (LinearLayout) findViewById(R.id.CarSourceDetails_Button_Normal_layout);
		CarSourceDetails_Button_Normal_layout
				.setVisibility(setOptionVisible() ? View.VISIBLE : View.GONE);
	}

	private boolean setOptionVisible() {
		int memberType = Integer.parseInt(CommonUtils.getMemberType(context));
		boolean isVisible = false;
		switch (memberType) {
		case 2:// 个人车主
			isVisible = false;
			break;
		case 3:// 企业
		case 1:// 货主
			isVisible = true;
			break;
		default:
			break;
		}
		return isVisible;
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.CarSourceDetails_CallUp:
			CommonUtils.makeingCalls(context,
					getResources().getString(R.string.Service_phone));
			break;
		case R.id.CarSourceDetails_PublishGoods:
			// 下单
			doPlaceAnOrder();
			break;
		case R.id.CarSourceDetails_Evalute:
			// 拨打电话
			// doEvalute();
			CommonUtils.makeingCalls(context,
					getResources().getString(R.string.Service_phone));
			break;
		case R.id.CarSourceDetails_Location_But:
			// 精准定位
			break;
		case R.id.CarSourceDetails_chat:
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

		if (carSourceInfo.getUserName()
				.equals(CommonUtils.getUserName(context)))
			ToastUtil.show(context, "不可以和自己聊天");
		else {
			// 进入聊天页面
			Intent intent = new Intent(CarSourceDetailActivity.this,
					ChatActivity.class);
			intent.putExtra("userId", carSourceInfo.getUserName());
			startActivity(intent);
		}

	}

	/**
	 * 下单
	 */
	private void doPlaceAnOrder() {
		Intent intent = new Intent(CarSourceDetailActivity.this,
				PlaceAnCarOrderActivity.class);
		intent.putExtra("carInfo", carSourceInfo);
		startActivity(intent);
	}

	/**
	 * 评价
	 */
	private void doEvalute() {
		Intent intent = new Intent(CarSourceDetailActivity.this,
				EvaluteActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(CarSourceDetailActivity.this);
			break;
		case R.id.maintitle_comfirm_iv:
			doRemark();
			break;

		default:
			break;
		}
	}

	/**
	 * 收藏
	 */
	private void doRemark() {
		CollectSourceHandler dataHandler = new CollectSourceHandler(context,
				"2");
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		switch (resultCode) {
		case NetWork.COLLECT_SOURCE_OK:
			mark_iv.setBackgroundResource(R.drawable.collect_icon);
			ToastUtil.show(context,
					getResources().getString(R.string.collected_hint));
			break;
		case NetWork.COLLECT_SOURCE_ERROR:

			break;

		default:
			break;
		}
	}

}
