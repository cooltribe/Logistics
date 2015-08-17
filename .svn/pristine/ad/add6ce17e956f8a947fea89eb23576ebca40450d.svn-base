package com.seeyuan.logistics.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.entity.AroundCarInfo;
import com.seeyuan.logistics.entity.GoodsSourceInfo;

/**
 * 附近货源适配器
 * 
 * @author Administrator
 * 
 */
public class TabAroundGoodsAdapter extends BaseAdapter {

	private List<GoodsSourceInfo> mDataList;
	private Context context;

	public TabAroundGoodsAdapter(List<GoodsSourceInfo> mDataList,
			Context context) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_around_goods_style, null);

				holder.target = (TextView) convertView
						.findViewById(R.id.item_good_target);
				holder.user = (TextView) convertView
						.findViewById(R.id.item_good_user);
				holder.goodsType = (TextView) convertView
						.findViewById(R.id.item_good_goodsType);
				holder.carType = (TextView) convertView
						.findViewById(R.id.item_good_carType);
				holder.distance = (TextView) convertView
						.findViewById(R.id.item_good_distance);
				holder.time = (TextView) convertView
						.findViewById(R.id.item_good_time);
				holder.call = (ImageView) convertView
						.findViewById(R.id.item_good_call);
				holder.loaction = (ImageView) convertView
						.findViewById(R.id.item_good_car_location);
				holder.star = (RatingBar) convertView
						.findViewById(R.id.item_good_ratingbar);
				holder.authentication = (TextView) convertView
						.findViewById(R.id.item_around_goods_authentication);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.target.setText(mDataList.get(position).getTargetFrom() + "→"
					+ mDataList.get(position).getTargetTo());
			holder.user.setText(mDataList.get(position).getUserName() + ":"
					+ mDataList.get(position).getPhone());
			holder.goodsType.setText("分类:"
					+ mDataList.get(position).getGoodsType() + "     " + "重量:"
					+ mDataList.get(position).getGoodsWeight() + "吨");
			holder.carType.setText("车型:" + mDataList.get(position).getCarType()
					+ "     " + "车长:" + mDataList.get(position).getCarLength()
					+ "米");
			holder.distance.setText(mDataList.get(position).getDistance()
					+ "km");
			holder.time.setText(mDataList.get(position).getTime());
			holder.star.setRating(mDataList.get(position).getStar());
			holder.authentication.setVisibility(mDataList.get(position)
					.isAuthentication() ? View.VISIBLE : View.INVISIBLE);

		}

		return convertView;
	}

	final class ViewHolder {

		TextView target;
		TextView user;
		TextView goodsType;
		TextView carType;
		TextView carLength;
		TextView distance;
		TextView time;
		ImageView loaction;
		ImageView call;
		RatingBar star;
		TextView authentication;

	}
}
