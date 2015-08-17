package com.seeyuan.logistics.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.entity.GoodsDto;

/**
 * 货源管理adapter
 * 
 * @author zhazhaobao
 * 
 */
public class GoodsManagerAdapter extends BaseAdapter {

	private Context context;

	private List<GoodsDto> mDataList;

	private Handler handler;

	private boolean isAddViewSuccess = false;

	public GoodsManagerAdapter(Context context, Handler handler,
			List<GoodsDto> mDataList) {
		this.context = context;
		this.handler = handler;
		this.mDataList = mDataList;
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

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_goods_manager_list, null);
				holder.contentLayout = (LinearLayout) convertView
						.findViewById(R.id.item_goods_manager_goods_layout);
				holder.contentLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Message msg = handler.obtainMessage();
						msg.what = v.getId();
						msg.arg1 = position;
						msg.sendToTarget();
					}
				});

				holder.titleLayout = (RelativeLayout) convertView
						.findViewById(R.id.item_goods_manager_title_layout);
				holder.titleLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Message msg = handler.obtainMessage();
						msg.what = v.getId();
						msg.arg1 = position;
						msg.sendToTarget();
					}
				});

				holder.goodsName = (TextView) convertView
						.findViewById(R.id.item_goods_manager_goodsname);
				holder.goodsWeight = (TextView) convertView
						.findViewById(R.id.item_goods_manager_weight);
				holder.goodsType = (TextView) convertView
						.findViewById(R.id.item_goods_manager_goodstype);
				holder.searchCar = (Button) convertView
						.findViewById(R.id.item_goods_manager_search_car);
				holder.reedit = (Button) convertView
						.findViewById(R.id.item_goods_manager_line_edit);
				holder.retry = (Button) convertView
						.findViewById(R.id.item_goods_manager_line_retry);
				holder.delete = (Button) convertView
						.findViewById(R.id.item_goods_manager_line_delete);
				holder.goodsFrom = (TextView) convertView
						.findViewById(R.id.item_goods_manager_line_from);
				holder.goodsTo = (TextView) convertView
						.findViewById(R.id.item_goods_manager_line_to);
				holder.carType = (TextView) convertView
						.findViewById(R.id.item_goods_manager_car_type);
				holder.carLength = (TextView) convertView
						.findViewById(R.id.item_goods_manager_car_length);
				holder.validTime = (TextView) convertView
						.findViewById(R.id.item_goods_manager_validtime);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			GoodsDto info = mDataList.get(position);
			holder.goodsName
					.setText(TextUtils.isEmpty(info.getGoodsName()) ? "未知"
							: info.getGoodsName());
			holder.goodsWeight.setText(null == info.getGoodsWeight() ? "未知"
					: String.format(
							context.getResources().getString(
									R.string.goods_weight_hint), info
									.getGoodsWeight().intValue()));
			holder.goodsType
					.setText(TextUtils.isEmpty(info.getGoodsType()) ? "未知"
							: info.getGoodsType());
			holder.goodsFrom.setText(TextUtils.isEmpty(info.getSetout()) ? "未知"
					: info.getSetout());
			holder.goodsTo
					.setText(TextUtils.isEmpty(info.getDestination()) ? "未知"
							: info.getDestination());
			holder.carType.setText(TextUtils.isEmpty(info.getVehType()) ? "未知"
					: info.getVehType());
			holder.carLength
					.setText(TextUtils.isEmpty(info.getVehLegth()) ? "未知"
							: info.getVehLegth());
			holder.validTime.setText(null == info.getValidDeadline() ? "未知"
					: "截止日期:" + new SimpleDateFormat("yyyy-MM-dd").format(info
							.getValidDeadline()));
			holder.searchCar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});
			holder.reedit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});
			holder.retry.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});
			holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.what = v.getId();
					msg.arg1 = position;
					msg.sendToTarget();
				}
			});
		}

		return convertView;
	}

	public class ViewHolder {
		private TextView goodsName;
		private TextView goodsWeight;
		private TextView goodsType;
		private Button searchCar;
		private Button reedit;
		private Button retry;
		private Button delete;
		private TextView goodsFrom;
		private TextView goodsTo;
		private TextView carType;
		private TextView carLength;
		private TextView validTime;

		private LinearLayout contentLayout;
		private RelativeLayout titleLayout;

	}
}
