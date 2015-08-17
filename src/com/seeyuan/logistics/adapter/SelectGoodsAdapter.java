package com.seeyuan.logistics.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.text.TextUtils;
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
import com.seeyuan.logistics.entity.GoodsDto;

/**
 * 司机管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class SelectGoodsAdapter extends BaseAdapter {

	private List<GoodsDto> mDataList = new ArrayList<GoodsDto>();

	private Context context;

	/**
	 * 保存checkbox选中的id
	 */
	private List<Integer> selectedList = new ArrayList<Integer>();

	public SelectGoodsAdapter(List<GoodsDto> mDataList, Context context) {
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
					R.layout.item_select_goods_manager, null);
			holder.goodsName = (TextView) convertView
					.findViewById(R.id.select_goods_type_tv);
			holder.carType = (TextView) convertView
					.findViewById(R.id.select_car_type_tv);
			holder.goodsWeight = (TextView) convertView
					.findViewById(R.id.select_goods_weight_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GoodsDto info = mDataList.get(position);
		holder.goodsName.setText(info.getGoodsName());
		holder.carType.setText("车辆要求:"
				+ (TextUtils.isEmpty(info.getVehType()) ? "未知" : info
						.getVehType()));
		holder.goodsWeight.setText("货物重量:"
				+ (null == info.getGoodsWeight() ? "未知" : (info
						.getGoodsWeight().toString() + "吨")));
		return convertView;
	}

	public List<Integer> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<Integer> selectedList) {
		this.selectedList = selectedList;
	}

	final class ViewHolder {
		private TextView goodsName;
		private TextView carType;
		private TextView goodsWeight;
	}
}
