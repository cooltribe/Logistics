package com.seeyuan.logistics.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.entity.AccountSettleDto;
import com.seeyuan.logistics.entity.OrderDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 可结算订单管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class SettlementManager2ListAdapter extends BaseAdapter {

	private List<OrderDto> mDataList;
	private Context context;

	public SettlementManager2ListAdapter(Context context,
			List<OrderDto> mDataList) {
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
						R.layout.item_settlement_manager2_list, null);
				holder.no = (TextView) convertView
						.findViewById(R.id.settlement_manager_order_no);
				holder.money = (TextView) convertView
						.findViewById(R.id.settlement_manager_order_money);
				holder.completeTime = (TextView) convertView
						.findViewById(R.id.settlement_manager_complete_time);
				holder.createTime = (TextView) convertView
						.findViewById(R.id.settlement_manager_create_time);
				holder.payType = (TextView) convertView
						.findViewById(R.id.settlement_manager_pay_type);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final OrderDto orderDto = mDataList.get(position);
			holder.no.setText(TextUtils.isEmpty(orderDto.getOrderNo()) ? ""
					: orderDto.getOrderNo());
			holder.money.setText(null == orderDto.getOrderAmount() ? ""
					: orderDto.getOrderAmount().toString());
			// 完成时间暂无
			// holder.completeTime.setText(null == orderDto.get() ? ""
			// : CommonUtils.parserTimestamp(orderDto.getProcDeadline()));
			// 创建时间暂无
			// holder.createTime.setText(null == orderDto.getSettAmount() ? ""
			// : orderDto.getSettAmount().toString());
			holder.payType
					.setText(TextUtils.isEmpty(orderDto.getPayStatus()) ? ""
							: orderDto.getPayStatus());
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView no;
		private TextView money;
		private TextView completeTime;
		private TextView createTime;
		private TextView payType;
		private TextView arbitration;

	}

}
