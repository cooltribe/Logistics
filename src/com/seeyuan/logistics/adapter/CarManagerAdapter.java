package com.seeyuan.logistics.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.R.color;
import com.seeyuan.logistics.activity.MainActivity;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.entity.CarManagerInfo;
import com.seeyuan.logistics.entity.CarManagerLineInfo;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.service.CarBDLocationService;
import com.seeyuan.logistics.util.CommonUtils;

public class CarManagerAdapter extends BaseAdapter {

	private Context context;

	private List<CarsDto> mDataList;

	private Handler handler;

	private boolean isAddViewSuccess = false;

	private final int EDIT_LINE_CODE = 10000;
	private SharedPreferences sPreferences;

	/**
	 * 保存checkbox选中的id
	 */
	private List<Integer> selectedList = new ArrayList<Integer>();

	public CarManagerAdapter(Context context, Handler handler,
			List<CarsDto> mDataList) {
		this.context = context;
		this.handler = handler;
		this.mDataList = mDataList;
		sPreferences = context.getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_car_manager_list, null);
				holder.carPlate = (TextView) convertView
						.findViewById(R.id.item_car_manager_carname);
				holder.carType = (TextView) convertView
						.findViewById(R.id.item_car_manager_cartype);
				holder.localCar = (Button) convertView.findViewById(R.id.item_car_manager_local);
				holder.addLine = (Button) convertView
						.findViewById(R.id.item_car_manager_add_line);
				holder.searchGoods = (Button) convertView
						.findViewById(R.id.item_car_manager_search_goods);
				holder.lineInfoLayout = (LinearLayout) convertView
						.findViewById(R.id.item_car_manager_line_layout);
				holder.emptyLine = (TextView) convertView
						.findViewById(R.id.item_car_manager_line_empty);
				holder.mainTitleLayout = (RelativeLayout) convertView
						.findViewById(R.id.item_car_manager_layout);
				holder.checkBoxLayout = (LinearLayout) convertView
						.findViewById(R.id.item_car_manager_check_layout);
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.item_car_manager_checkbox);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final CarsDto info = mDataList.get(position);
			holder.carPlate.setText(info.getVehicleNum());
			String carType = info.getIsChecked();

			holder.carType.setTextColor(CommonUtils.getColor(context, carType));
			holder.carType.setText(CommonUtils.getTypeTitle(carType));
			holder.mainTitleLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});
			if (sPreferences.getString("carsNum", "").equals(mDataList.get(position).getVehicleNum())) {
				holder.localCar.setVisibility(View.GONE);
			} else {
				holder.localCar.setVisibility(View.VISIBLE);
			}
			holder.localCar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					context.stopService(new Intent(context, CarBDLocationService.class));
//					sPreferences.edit().remove("carsNum").commit();
					sPreferences.edit().putString("carsNum", mDataList.get(position).getVehicleNum()).commit();
					context.startService(new Intent(context, CarBDLocationService.class));
					notifyDataSetChanged();
				}
			});
			holder.addLine.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});

			holder.searchGoods.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});
			if (info.getRoutes().size() > 0) {
				if (holder.lineInfoLayout.getChildCount() != info.getRoutes()
						.size()) {
					holder.lineInfoLayout.setVisibility(View.VISIBLE);
					holder.emptyLine.setVisibility(View.GONE);
					doAddContent(info, holder, position);
				}
			} else {
				holder.lineInfoLayout.setVisibility(View.GONE);
				holder.emptyLine.setVisibility(View.VISIBLE);
			}
			holder.checkBoxLayout
					.setVisibility(info.isEditMode() ? View.VISIBLE : View.GONE);
			if (!info.isEditMode()) {
				holder.checkBox.setChecked(false);
			}
			if (info.isEditMode()) {
				holder.checkBoxLayout.setAnimation(AnimationUtils
						.loadAnimation(context, R.anim.left_in));
			} else {
				holder.checkBoxLayout.setAnimation(AnimationUtils
						.loadAnimation(context, R.anim.left_out));
			}
			holder.checkBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								selectedList.add(position);
							} else {
								if (selectedList.equals(String
										.valueOf(position))) {
									selectedList.remove(String
											.valueOf(position));
								}
							}
						}
					});
		}

		return convertView;
	}

	private void doAddContent(CarsDto carManagerInfo, ViewHolder holder,
			final int position) {
		List<RouteDto> mDataList = carManagerInfo.getRoutes();
		int size = mDataList.size();
		for (int i = 0; i < size; i++) {
			final RouteDto info = mDataList.get(i);
			View layout = LayoutInflater.from(context).inflate(
					R.layout.item_car_manager_list_line, null);
			((TextView) layout.findViewById(R.id.item_car_manager_line_from))
					.setText(info.getSetout());
			((TextView) layout.findViewById(R.id.item_car_manager_line_to))
					.setText(info.getDestination());
			((TextView) layout.findViewById(R.id.item_car_manager_line_type))
					.setText(context.getResources().getString(
							R.string.line_type_hint)
							+ ":"
							+ CommonUtils.parserLineType(info.getType())
							+ (null == info.getValidDeadline() ? "" : ("["
									+ CommonUtils.parserTimestamp(info
											.getValidDeadline()) + "]")));
			final int linePosition = i;
			((Button) layout.findViewById(R.id.item_car_manager_line_edit))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Message msg = handler.obtainMessage();
							msg.what = v.getId();
							msg.arg1 = linePosition;
							msg.arg2 = position;
							msg.sendToTarget();
						}
					});
			((Button) layout.findViewById(R.id.item_car_manager_line_delete))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Message msg = handler.obtainMessage();
							msg.what = v.getId();
							msg.arg1 = linePosition;
							msg.arg2 = position;
							msg.sendToTarget();
						}
					});
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = EDIT_LINE_CODE;
					msg.arg1 = linePosition;
					msg.arg2 = position;
					msg.sendToTarget();
				}
			});
			holder.lineInfoLayout.addView(layout);
		}
		isAddViewSuccess = true;
	}

	public List<Integer> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<Integer> selectedList) {
		this.selectedList = selectedList;
	}

	public class ViewHolder {
		private TextView carPlate;
		private TextView carType;
		private Button localCar;
		private Button addLine;
		private Button searchGoods;
		private LinearLayout lineInfoLayout;
		private TextView emptyLine;
		private RelativeLayout mainTitleLayout;
		private LinearLayout checkBoxLayout;
		private CheckBox checkBox;

	}
}
