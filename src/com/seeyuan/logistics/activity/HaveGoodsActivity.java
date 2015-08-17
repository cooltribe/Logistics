package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarTypeInfo;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 有货
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class HaveGoodsActivity extends Activity implements OnClickListener {

	/**
	 * 从哪里出发
	 */
	private Button SearchGoods_Start;

	/**
	 * 到哪里去
	 */
	private Button SearchVehicle_Price_End;

	/**
	 * 车辆类型
	 */
	private Button SearchVehicle_Price_CarType;

	/**
	 * 车辆长度
	 */
	private Button SearchVehicle_Price_CarLength;

	/**
	 * 搜索
	 */
	private Button VehiceSearch_Select_But;

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 地址切换
	 */
	private ImageButton SearchVehicle_Price_But;

	private Context context;

	private final int REFRESH_CAR_TYPE = 1000;

	private final int REFRESH_CAR_LENGTH = 1001;

	/**
	 * 从哪里出发，数据返回
	 */
	private final int END_RETURN = 2000;

	/**
	 * 到哪里去，数据返回
	 */
	private final int START_RETURN = 2001;
	
	/**
	 * 货源搜索条件
	 */
	private GoodsDto goodsDto = new GoodsDto();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_havegoods); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局

		context = getApplicationContext();
		initView();
		CommonUtils.addActivity(this);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		SearchGoods_Start = (Button) findViewById(R.id.SearchGoods_Start);
		// SearchGoods_Start.setOnClickListener(this);

		SearchVehicle_Price_End = (Button) findViewById(R.id.SearchVehicle_Price_End);
		// SearchVehicle_Price_End.setOnClickListener(this);

		SearchVehicle_Price_CarType = (Button) findViewById(R.id.SearchVehicle_Price_CarType);
		// SearchVehicle_Price_CarType.setOnClickListener(this);

		SearchVehicle_Price_CarLength = (Button) findViewById(R.id.SearchVehicle_Price_CarLength);
		// SearchVehicle_Price_CarLength.setOnClickListener(this);

		VehiceSearch_Select_But = (Button) findViewById(R.id.VehiceSearch_Select_But);
		// VehiceSearch_Select_But.setOnClickListener(this);

		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.have_goods_hint);

		SearchVehicle_Price_But = (ImageButton) findViewById(R.id.SearchVehicle_Price_But);
		SearchVehicle_Price_But.setOnClickListener(this);
		
		goodsDto.setVehLegth("");
		goodsDto.setVehType("");

	}

	/**
	 * 
	 * 点击事件的另一种使用方法。无需过多研究，只需要知道有这么一种方法提供使用
	 * 
	 * @param view
	 */
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.SearchGoods_Start:
			// ToastUtil.show(context, "从哪里出发");
			Intent startIntent = new Intent(HaveGoodsActivity.this,
					SearchCityActivity.class);
			startActivityForResult(startIntent, START_RETURN);
			break;
		case R.id.SearchVehicle_Price_End:
			// ToastUtil.show(context, "到哪里去");
			Intent endIntent = new Intent(HaveGoodsActivity.this,
					SearchCityActivity.class);
			startActivityForResult(endIntent, END_RETURN);
			break;
		case R.id.SearchVehicle_Price_CarType:
			// ToastUtil.show(context, "车辆类型");
			doSearchCarType();
			break;
		case R.id.SearchVehicle_Price_CarLength:
			// ToastUtil.show(context, "车辆长度");
			doSearchCarLength();
			break;
		case R.id.VehiceSearch_Select_But:
			doSearchSelectButton();
			break;
		case R.id.Search_Call_But:
			CommonUtils.makeingCalls(context,
					getResources().getString(R.string.Service_phone));
			break;
		default:
			break;
		}
	}

	/**
	 * 搜索查询
	 */
	private void doSearchSelectButton() {
		// 带入参数传递至搜索界面 待处理
		Intent intent = new Intent(HaveGoodsActivity.this,
				SearchGoodsInfoActivity.class);
		intent.putExtra("goodsDto", goodsDto);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!HaveGoodsActivity.this.isFinishing()) {
				finish();
			}
			break;
		case R.id.SearchVehicle_Price_But:
			doPriceExchange();

			break;
		}
	}

	/**
	 * 地址切换
	 */
	private void doPriceExchange() {
		if (SearchGoods_Start.getText().length() != 0
				|| SearchVehicle_Price_End.getText().length() != 0) {
			String start = SearchGoods_Start.getText().toString();
			String end = SearchVehicle_Price_End.getText().toString();
			SearchGoods_Start.setText(end);
			SearchVehicle_Price_End.setText(start);
			goodsDto.setSetout(end);
			goodsDto.setDestination(start);
		}
	}

	@SuppressWarnings("unused")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_CAR_TYPE:
				String typeString = msg.obj.toString();
				SearchVehicle_Price_CarType.setText(msg.obj.toString());
				goodsDto.setVehType(typeString);
				break;
			case REFRESH_CAR_LENGTH:

				String lengthString = msg.obj.toString();
				SearchVehicle_Price_CarLength.setText(lengthString
						.equalsIgnoreCase("全部") ? msg.obj.toString()
						: lengthString + "米");

				goodsDto.setVehLegth(lengthString);
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 车型选择
	 */
	private void doSearchCarType() {

		List<CarTypeInfo> mDataList = new ArrayList<CarTypeInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.CarType_Str);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarTypeInfo indexInfo = new CarTypeInfo();
			indexInfo.setCar_type(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				HaveGoodsActivity.this);
		ad.setTitle(getResources()
				.getString(R.string.PublishInfo_CarTypes_Hint));
		ad.setListContentForCarType(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_CAR_TYPE;
				message.obj = ((TextView) view.findViewById(R.id.item_car_type))
						.getText();
				myHandler.sendMessage(message);

			}
		});
		// ad.setPositiveButton("确定", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveGoodsActivity.this, "被点到确定",
		// Toast.LENGTH_LONG).show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveGoodsActivity.this, "被点到取消",
		// Toast.LENGTH_LONG).show();
		// }
		// });
		typedArray.recycle();
	}

	/**
	 * 车长选择
	 */
	private void doSearchCarLength() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Search_car_length);

		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				HaveGoodsActivity.this);
		ad.setTitle(getResources().getString(R.string.PublishInfo_Carlen_Hint));
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = REFRESH_CAR_LENGTH;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
		// ad.setPositiveButton("确定", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveGoodsActivity.this, "被点到确定",
		// Toast.LENGTH_LONG).show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveGoodsActivity.this, "被点到取消",
		// Toast.LENGTH_LONG).show();
		// }
		// });
		typedArray.recycle();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case END_RETURN:
			SearchVehicle_Price_End.setText(data.getStringExtra("place"));
			Log.i("目的", data.getStringExtra("place"));
			goodsDto.setDestination(data.getStringExtra("place"));
			break;
		case START_RETURN:
			SearchGoods_Start.setText(data.getStringExtra("place"));
			Log.i("出发", data.getStringExtra("place"));
			goodsDto.setSetout(data.getStringExtra("place"));
			
			
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
