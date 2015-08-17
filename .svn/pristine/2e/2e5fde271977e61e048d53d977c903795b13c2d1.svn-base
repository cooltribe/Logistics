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

public class NormalTextAdapter extends BaseAdapter {

	private List<String> mDataList;

	private Context context;

	public NormalTextAdapter(List<String> mDataList, Context context) {
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
						R.layout.item_normal_text_style, null);
				holder.normal_text = (TextView) convertView
						.findViewById(R.id.item_normal_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.normal_text.setText(mDataList.get(position).toString());
		}

		return convertView;
	}

	final class ViewHolder {
		TextView normal_text;
	}

}
