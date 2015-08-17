package com.seeyuan.logistics.adapter;

import java.util.List;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.CityTypeAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PrivinceAdapter extends BaseAdapter {

	private List<String> mDataList;
	private Context context;

	public PrivinceAdapter(Context context, List<String> mDataList) {
		this.context = context;
		this.mDataList = mDataList;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_province_style, null);
				holder.provinceName = (TextView) convertView
						.findViewById(R.id.item_province_type);
				// 热点提示，如果后期需要改图片，先做好预备工作
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.provinceName.setText(mDataList.get(position).toString());
		}

		return convertView;
	}

	final class ViewHolder {
		private TextView provinceName;

	}
}
