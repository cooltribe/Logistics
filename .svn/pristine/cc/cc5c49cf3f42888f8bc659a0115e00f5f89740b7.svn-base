package com.seeyuan.logistics.adapter;

import java.util.List;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.entity.CarsDto;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StartSelectCarAdapter extends BaseAdapter {
	List<CarsDto> list;
	Context context;
	LayoutInflater inflater;
	public StartSelectCarAdapter(Context context,Handler handler, List<CarsDto> list) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh;
		if (null == convertView) {
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_singele_text, parent, false);
			vh.carNum = (TextView) convertView.findViewById(R.id.car_num);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.carNum.setText(list.get(position).getVehicleNum());
		return convertView;
	}
	class ViewHolder{
		TextView carNum;
	}
}
