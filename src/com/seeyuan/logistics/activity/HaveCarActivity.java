package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarTypeInfo;
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 车源搜索
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class HaveCarActivity extends BaseActivity implements OnClickListener {

	/**
	 * 车源类型
	 */
	private Button SearchCar_CarSourceType;

	/**
	 * 期望流向
	 */
	private TextView SearchCar_Destination;

	/**
	 * 当前位置
	 */
	private TextView SearchCar_Location;

	/**
	 * 清空地理位置
	 */
	private Button SearchCar_From_Empty;

	/**
	 * 清空期望流向
	 */
	private Button SearchCar_To_Empty;

	/**
	 * 车辆类型
	 */
	private Button SearchCar_CarType;

	/**
	 * 车辆长度
	 */
	private Button SearchCar_CarLength;

	/**
	 * 搜索
	 */
	private Button SearchCar_Confirm;

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

	private Context context;

	/**
	 * 车辆类型
	 */
	private final int REFRESH_CAR_TYPE = 1000;

	/**
	 * 车辆长度
	 */
	private final int REFRESH_CAR_LENGTH = 1001;

	/**
	 * 车源类型
	 */
	private final int REFRESH_CAR_SOURCE_TYPE = 1002;

	/**
	 * 期望流向，数据返回
	 */
	private final int DESTINATION_RETURN = 2000;

	/**
	 * 当前位置
	 */
	private final int CURRENT_POSITION_RETURN = 2001;

	private SharedPreferences sPreferences;

	/**
	 * 车源搜索条件
	 */
	private RouteDto routeDto = new RouteDto();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_havecar); // 软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		initView();
		initData();
	}

	@Override
	public void initView() {

		SearchCar_CarSourceType = (Button) findViewById(R.id.SearchCar_CarSourceType);
		// SearchCar_CarSourceType.setOnClickListener(this);
		//
		SearchCar_Destination = (TextView) findViewById(R.id.SearchCar_Destination);
		SearchCar_Destination.setOnClickListener(this);
		SearchCar_Destination.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(s)) {
					SearchCar_To_Empty.setVisibility(View.GONE);
				} else {
					SearchCar_To_Empty.setVisibility(View.VISIBLE);
				}
			}
		});
		// SearchCar_Destination.setOnClickListener(this);
		//
		SearchCar_Location = (TextView) findViewById(R.id.SearchCar_Location);
		SearchCar_Location.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(s)) {
					SearchCar_From_Empty.setVisibility(View.GONE);
				} else {
					SearchCar_From_Empty.setVisibility(View.VISIBLE);
				}
			}
		});
		SearchCar_Location.setOnClickListener(this);
		//
		SearchCar_From_Empty = (Button) findViewById(R.id.searchcar_from_empty);

		SearchCar_To_Empty = (Button) findViewById(R.id.searchcar_to_empty);

		//
		SearchCar_CarType = (Button) findViewById(R.id.SearchCar_CarType);
		// SearchCar_CarType.setOnClickListener(this);
		//
		SearchCar_CarLength = (Button) findViewById(R.id.SearchCar_CarLength);
		// SearchCar_CarLength.setOnClickListener(this);
		//
		SearchCar_Confirm = (Button) findViewById(R.id.SearchCar_Confirm);
		// SearchCar_Confirm.setOnClickListener(this);
		//
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.have_car_hint);

		routeDto.setVehType("");
		routeDto.setVehLegth("");

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		SearchCar_Location.setText(sPreferences.getString("province", "") + "-"
				+ sPreferences.getString("city", ""));
		routeDto.setSetout(SearchCar_Location.getText().toString());
	}

	@Override
	public void onClickListener(View view) {

		switch (view.getId()) {
		case R.id.SearchCar_CarSourceType:
			doSearchCarSourceStyle();
			break;
		case R.id.SearchCar_Destination:
			// ToastUtil.show(context, "期望流向");
			Intent intent = new Intent(HaveCarActivity.this,
					SearchCityActivity.class);
			startActivityForResult(intent, DESTINATION_RETURN);
			break;
		case R.id.SearchCar_Location:
			Intent currentIntent = new Intent(HaveCarActivity.this,
					SearchCityActivity.class);
			startActivityForResult(currentIntent, CURRENT_POSITION_RETURN);
			break;
		case R.id.searchcar_from_empty:
			SearchCar_Location.setText("");
			routeDto.setSetout("");
			break;
		case R.id.searchcar_to_empty:
			SearchCar_Destination.setText("");
			break;
		case R.id.SearchCar_CarLength:
			doSearchCarLength();
			break;
		case R.id.SearchCar_CarType:
			doSearchCarType();
			break;
		case R.id.SearchCar_Confirm:
			doSearchCarListInfo();
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
	 * 执行 获取车源信息详情界面
	 */
	private void doSearchCarListInfo() {

		// 带入参数传递至搜索界面 待处理
		String carType = SearchCar_CarType.getText().toString();
		String carLength = SearchCar_CarLength.getText().toString();
		if (TextUtils.isEmpty(carType))
			carType = getResources().getString(R.string.search_car_type_hint);
		if (TextUtils.isEmpty(carLength))
			carLength = getResources().getString(
					R.string.search_car_length_hint);
		Intent intent = new Intent(HaveCarActivity.this,
				SearchCarInfoActivity.class);
		intent.putExtra("routeDto", routeDto);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;
		case R.id.SearchCar_Location:
			Intent currentIntent = new Intent(HaveCarActivity.this,
					SearchCityActivity.class);
			startActivityForResult(currentIntent, CURRENT_POSITION_RETURN);
			break;
		case R.id.SearchCar_Destination:
			// ToastUtil.show(context, "期望流向");
			Intent intent = new Intent(HaveCarActivity.this,
					SearchCityActivity.class);
			startActivityForResult(intent, DESTINATION_RETURN);
			break;
		default:
			break;
		}
	}

	private Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_CAR_TYPE:
				String typeString = msg.obj.toString();
				SearchCar_CarType.setText(typeString);
				routeDto.setVehType(typeString);
				break;
			case REFRESH_CAR_LENGTH:

				String lengthString = msg.obj.toString();
				SearchCar_CarLength
						.setText(lengthString.equalsIgnoreCase("全部") ? msg.obj
								.toString() : lengthString + "米");

				routeDto.setVehLegth(lengthString);
				break;
			case REFRESH_CAR_SOURCE_TYPE:
				SearchCar_CarSourceType.setText(msg.obj.toString());
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
		typedArray.recycle();
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarTypeInfo indexInfo = new CarTypeInfo();
			indexInfo.setCar_type(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				HaveCarActivity.this);
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
		// Toast.makeText(HaveCarActivity.this, "被点到确定", Toast.LENGTH_LONG)
		// .show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveCarActivity.this, "被点到取消", Toast.LENGTH_LONG)
		// .show();
		// }
		// });

	}

	/**
	 * 车长选择
	 */
	private void doSearchCarLength() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.Search_car_length);
		typedArray.recycle();
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				HaveCarActivity.this);
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
		// Toast.makeText(HaveCarActivity.this, "被点到确定", Toast.LENGTH_LONG)
		// .show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveCarActivity.this, "被点到取消", Toast.LENGTH_LONG)
		// .show();
		// }
		// });

	}

	/**
	 * 车源类型
	 */
	private void doSearchCarSourceStyle() {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.CarSourceType);
		typedArray.recycle();
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				HaveCarActivity.this);
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
				message.what = REFRESH_CAR_SOURCE_TYPE;
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
		// Toast.makeText(HaveCarActivity.this, "被点到确定", Toast.LENGTH_LONG)
		// .show();
		//
		// }
		// });

		// ad.setNegativeButton("取消", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ad.dismiss();
		// Toast.makeText(HaveCarActivity.this, "被点到取消", Toast.LENGTH_LONG)
		// .show();
		// }
		// });

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case DESTINATION_RETURN:
			SearchCar_Destination.setText(data.getStringExtra("place"));
			routeDto.setDestination(data.getStringExtra("place"));
			
			break;
		case CURRENT_POSITION_RETURN:
			SearchCar_Location.setText(data.getStringExtra("place"));
			routeDto.setSetout(data.getStringExtra("place"));
			break;

		default:
			break;
		}
	}

}
