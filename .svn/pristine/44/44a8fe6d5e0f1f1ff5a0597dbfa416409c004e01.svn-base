package com.seeyuan.logistics.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.entity.RouteDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 车源适配器
 * 
 * @author zhazhaobao
 * 
 */
public class CarSourceListAdapter extends BaseAdapter {

	private List<RouteDto> mDataList;
	private Context context;

	public CarSourceListAdapter(Context context, List<RouteDto> mDataList) {
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
	public View getView(final int position, View convertView,
			ViewGroup viewGroup) {

		ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_car_source_list1, null);
				holder.from = (TextView) convertView
						.findViewById(R.id.CarSourceItem_from);
				holder.to = (TextView) convertView
						.findViewById(R.id.CarSourceItem_to);
				holder.carPlate = (TextView) convertView
						.findViewById(R.id.CarSourceItem_CarPlate);
				holder.carLength = (TextView) convertView
						.findViewById(R.id.CarSourceItem_CarLength);
				holder.carType = (TextView) convertView
						.findViewById(R.id.CarSourceItem_CarType);
				holder.carWeight = (TextView) convertView
						.findViewById(R.id.CarSourceItem_CarWeight);
				holder.userName = (TextView) convertView
						.findViewById(R.id.CarSourceItem_Name);
				holder.userPhone = (TextView) convertView
						.findViewById(R.id.CarSourceItem_Phone);
				holder.location = (TextView) convertView
						.findViewById(R.id.CarSourceItem_Location);
				holder.publicTime = (TextView) convertView
						.findViewById(R.id.CarSourceItem_PublishDate);
				holder.call = (Button) convertView
						.findViewById(R.id.CarSourceItem_CallUp);
				holder.star = (RatingBar) convertView
						.findViewById(R.id.CarSourceItem_Level);
				holder.save = (ImageView) convertView
						.findViewById(R.id.CarSourceItem_Save);
				holder.notary = (ImageView) convertView
						.findViewById(R.id.CarSourceItem_Notar);
				holder.protect = (ImageView) convertView
						.findViewById(R.id.CarSourceItem_insurance);
				holder.statue = (TextView) convertView
						.findViewById(R.id.CarSourceItem_Status);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final RouteDto routeDto = mDataList.get(position);
			holder.from.setText(TextUtils.isEmpty(routeDto.getSetout()) ? "未知"
					: routeDto.getSetout());
			holder.to
					.setText(TextUtils.isEmpty(routeDto.getDestination()) ? "未知"
							: routeDto.getDestination());
			// holder.save
			// .setVisibility(mDataList.get(position).isSave() ? View.VISIBLE
			// : View.GONE);
			// holder.notary
			// .setVisibility(mDataList.get(position).isNotary() ? View.VISIBLE
			// : View.GONE);
			// holder.protect
			// .setVisibility(mDataList.get(position).isProtect() ? View.VISIBLE
			// : View.GONE);
			holder.carPlate
					.setText(TextUtils.isEmpty(routeDto.getVehicleNum()) ? "未知"
							: routeDto.getVehicleNum());
			holder.carLength
					.setText(TextUtils.isEmpty(routeDto.getVehLegth()) ? "未知"
							: routeDto.getVehLegth() + "米");
			holder.carType
					.setText(TextUtils.isEmpty(routeDto.getVehType()) ? "未知"
							: routeDto.getVehType());
			holder.carWeight.setText(null == routeDto.getCarCapacity() ? "未知"
					: String.valueOf(routeDto.getCarCapacity()) + "吨");
			holder.userName
					.setText(TextUtils.isEmpty(routeDto.getUserName()) ? "未知"
							: routeDto.getUserName());
			holder.userPhone
					.setText(TextUtils.isEmpty(routeDto.getUserMobile()) ? "未知"
							: CommonUtils.encryptionString(
									routeDto.getUserMobile(), 4));
			holder.location
					.setText(TextUtils.isEmpty(routeDto.getUserAddress()) ? "未知"
							: routeDto.getUserAddress());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			holder.publicTime.setText(null == routeDto.getCreatingTime() ? "未知"
					: "发布时间:"
							+ simpleDateFormat.format(routeDto
									.getCreatingTime()));
			holder.statue
					.setText(TextUtils.isEmpty(routeDto.getStatus()) ? "未知"
							: CommonUtils.getLineTypeTitle(routeDto.getType()));
			// holder.star.setRating(routeDto.getStar());
			holder.call.setVisibility(TextUtils.isEmpty(routeDto
					.getUserMobile()) ? View.GONE : View.VISIBLE);
			holder.call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					CommonUtils.makeingCalls(context, routeDto.getUserMobile());
				}
			});
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView from;
		private TextView to;
		private ImageView save;
		private ImageView notary;
		private ImageView protect;
		private TextView carPlate;
		private TextView carLength;
		private TextView carType;
		private TextView carWeight;
		private TextView userName;
		private TextView userPhone;
		private TextView location;
		private TextView publicTime;
		private ImageView locationType;// 待处理，左边显示类型
		private Button call;
		private RatingBar star;
		private TextView statue;

	}

}
