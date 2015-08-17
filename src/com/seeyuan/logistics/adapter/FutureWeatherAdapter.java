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
import com.seeyuan.logistics.entity.FutureWeatherInfo;

public class FutureWeatherAdapter extends BaseAdapter {

	private List<FutureWeatherInfo> mDataList;

	private Context context;

	public FutureWeatherAdapter(List<FutureWeatherInfo> mDataList,
			Context context) {
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
						R.layout.item_future_weather_style, null);
				holder.week = (TextView) convertView
						.findViewById(R.id.item_weather_week);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.item_weather_icon);
				holder.lowTemp = (TextView) convertView
						.findViewById(R.id.item_weather_low);
				holder.highTemp = (TextView) convertView
						.findViewById(R.id.item_weather_high);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.week.setText(mDataList.get(position).getWeek());
			String temperature = mDataList.get(position).getTemperature();
			holder.lowTemp.setText(temperature.substring(
					temperature.indexOf("~") + 1, temperature.length()));
			holder.highTemp.setText(temperature.substring(0,
					temperature.lastIndexOf("~")));
			// holder.icon.setBackgroundResource(selectWeatherIcon(mDataList.get(
			// position).getWeather()));
		}

		return convertView;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	private int selectWeatherIcon(String wetherType) {
		return 0;
	}

	final class ViewHolder {
		TextView week;
		ImageView icon;
		TextView lowTemp;
		TextView highTemp;
	}

}
