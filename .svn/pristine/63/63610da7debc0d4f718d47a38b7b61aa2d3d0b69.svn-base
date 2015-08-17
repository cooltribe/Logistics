package com.seeyuan.logistics.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.activity.TabAroundCarActivity;
import com.seeyuan.logistics.entity.AroundCarInfo;
import com.seeyuan.logistics.map.SearchGoodsMapActivity;
import com.seeyuan.logistics.util.CommonUtils;

public class TabAroundCarAdapter extends BaseAdapter {

	private List<AroundCarInfo> mDataList;
	private Context context;

	public TabAroundCarAdapter(List<AroundCarInfo> mDataList, Context context) {
		super();
		this.mDataList = mDataList;
		this.context = context;
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

	public void refresh(List<AroundCarInfo> mDataList) {
		this.mDataList = mDataList;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_around_car_style, null);

				holder.target = (TextView) convertView
						.findViewById(R.id.item_around_target);
				holder.user = (TextView) convertView
						.findViewById(R.id.item_car_user);
				holder.carID = (TextView) convertView
						.findViewById(R.id.item_around_carID);
				holder.carLength = (TextView) convertView
						.findViewById(R.id.item_around_carLength);
				holder.distance = (TextView) convertView
						.findViewById(R.id.item_around_distance);
				holder.time = (TextView) convertView
						.findViewById(R.id.item_around_time);
				holder.loaction = (ImageView) convertView
						.findViewById(R.id.item_around_car_location);
				holder.star = (RatingBar) convertView
						.findViewById(R.id.item_car_ratingbar);
				holder.authentication = (TextView) convertView
						.findViewById(R.id.item_around_car_authentication);
				holder.call = (ImageView) convertView
						.findViewById(R.id.item_around_car_call);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.target
					.setText("期望流向:" + mDataList.get(position).getTarget());
			holder.user.setText(mDataList.get(position).getUsrName() + ":"
					+ mDataList.get(position).getPhoneNo());
			holder.carID.setText("车牌:"
					+ CommonUtils.encryptionString(mDataList.get(position)
							.getCarID(), 2) + "     " + "车型:"
					+ mDataList.get(position).getCarStyle());
			holder.carLength.setText("车长:"
					+ mDataList.get(position).getCarLength() + "米" + "     "
					+ "载重:" + mDataList.get(position).getCarWeigth() + "吨");
			holder.distance.setText("距离:"
					+ mDataList.get(position).getDistance() + "km");
			holder.time.setText(mDataList.get(position).getTime());
			holder.star.setRating(mDataList.get(position).getStar());
			holder.authentication.setVisibility(mDataList.get(position)
					.isAuthentication() ? View.VISIBLE : View.INVISIBLE);
			holder.loaction.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {

					Intent intent = new Intent();
					intent.setClass(context, SearchGoodsMapActivity.class);
//					intent.putExtra("longitude", mDataList.get(position)
//							.getLongitude());// 经度
//					intent.putExtra("latitude", mDataList.get(position)
//							.getLatitude());// 维度
					intent.putExtra("aroundCarInfo", mDataList.get(position));
					context.startActivity(intent);

				}
			});
			holder.call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					CommonUtils.makeingCalls(context, mDataList.get(position)
							.getPhoneNo());
				}
			});

		}

		return convertView;
	}

	final class ViewHolder {

		ImageView imageIcon;
		TextView description;
		TextView target;
		TextView user;
		TextView carID;
		TextView carStyle;
		TextView carLength;
		TextView carWeigth;
		TextView distance;
		TextView time;
		ImageView loaction;
		RatingBar star;
		TextView authentication;
		ImageView call;

	}
}
