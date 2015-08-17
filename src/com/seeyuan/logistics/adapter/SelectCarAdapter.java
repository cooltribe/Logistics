package com.seeyuan.logistics.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.R.color;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 司机管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class SelectCarAdapter extends BaseAdapter {

	private List<CarsDto> mDataList = new ArrayList<CarsDto>();

	private Context context;

	/**
	 * 保存checkbox选中的id
	 */
	private List<Integer> selectedList = new ArrayList<Integer>();

	public SelectCarAdapter(List<CarsDto> mDataList, Context context) {
		this.mDataList = mDataList;
		this.context = context;
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

			// 没有重用converView。这样很取巧。不过没有找到合适的方法，暂且如此。待后期研究
			// if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_select_car_manager, null);
			holder.carPlate = (TextView) convertView
					.findViewById(R.id.select_car_plate_tv);
			holder.carType = (TextView) convertView
					.findViewById(R.id.select_car_type_tv);
			holder.mainLayout = (RelativeLayout) convertView
					.findViewById(R.id.car_manager_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CarsDto info = mDataList.get(position);
		holder.carPlate.setText("车牌:" + info.getVehicleNum());
		String userType = info.getIsChecked();
		holder.carType.setTextColor(CommonUtils.getColor(context, userType));
		holder.carType.setText(CommonUtils.getTypeTitle(userType));

		return convertView;
	}

	public List<Integer> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<Integer> selectedList) {
		this.selectedList = selectedList;
	}

	final class ViewHolder {
		private TextView carPlate;
		private TextView carType;
		private RelativeLayout mainLayout;
	}

}
