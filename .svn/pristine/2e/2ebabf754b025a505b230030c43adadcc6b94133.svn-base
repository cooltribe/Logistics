package com.seeyuan.logistics.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.entity.CarLengthInfo;

public class SearchCarLengthAdapter extends BaseAdapter {

	private List<CarLengthInfo> mDataList;

	private Context context;

	public SearchCarLengthAdapter(List<CarLengthInfo> mDataList, Context context) {
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
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_car_length_style, null);
				holder.car_length = (TextView) convertView
						.findViewById(R.id.item_car_length);
				// 热点提示，如果后期需要改图片，先做好预备工作
				holder.car_length_sign = (ImageView) convertView
						.findViewById(R.id.item_car_length_hot);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.car_length.setText(mDataList.get(position).getCar_Length());
			holder.car_length_sign.setVisibility(mDataList.get(position)
					.isCarSignVisible() ? View.VISIBLE : View.GONE);
		}

		return convertView;
	}

	final class ViewHolder {
		TextView car_length;
		ImageView car_length_sign;
		boolean isSignVisible = false;
	}

}
