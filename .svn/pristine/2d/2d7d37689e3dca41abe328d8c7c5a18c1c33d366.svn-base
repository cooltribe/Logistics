package com.seeyuan.logistics.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seeyuan.logistics.R;

public class SearchPopWindowAdapter extends BaseAdapter{

	private List<String> mDataList;
	
	private Context context;
	
	public SearchPopWindowAdapter(List<String> mDataList, Context context) {
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_pop_window, null);
				holder.popwindow_content = (TextView) convertView
						.findViewById(R.id.popwindow_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.popwindow_content.setText(mDataList.get(position).toString());
		}

		return convertView;
	}
	final class ViewHolder {
		TextView popwindow_content;
	}
}
