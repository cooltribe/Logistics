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
import com.seeyuan.logistics.entity.SettleAccountsDetailDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 结算订单明细管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class SettlementDetailManagerListAdapter extends BaseAdapter {

	private List<SettleAccountsDetailDto> mDataList;
	private Context context;

	public SettlementDetailManagerListAdapter(Context context,
			List<SettleAccountsDetailDto> mDataList) {
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
						R.layout.item_settlement_detail_list, null);
				holder.no = (TextView) convertView
						.findViewById(R.id.settlement_detail_order_no);
				holder.money = (TextView) convertView
						.findViewById(R.id.settlement_detail_order_money);
				holder.type = (TextView) convertView
						.findViewById(R.id.settlement_detail_order_type);
				holder.content = (TextView) convertView
						.findViewById(R.id.settlement_detail_order_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final SettleAccountsDetailDto settleAccountSDetialDto = mDataList
					.get(position);
			holder.no.setText(TextUtils.isEmpty(settleAccountSDetialDto
					.getOrderNo()) ? "" : settleAccountSDetialDto.getOrderNo());
			holder.type.setText(TextUtils.isEmpty(settleAccountSDetialDto
					.getStatus()) ? "" : CommonUtils
					.getSettlementType(settleAccountSDetialDto.getStatus()));
			holder.money.setText(null == settleAccountSDetialDto
					.getAftSettAmount() ? "" : settleAccountSDetialDto
					.getAftSettAmount().toString());
			holder.content.setText(TextUtils.isEmpty(settleAccountSDetialDto
					.getGoodsSetout())
					&& TextUtils.isEmpty(settleAccountSDetialDto
							.getGoodsDestination()) ? ""
					: settleAccountSDetialDto.getGoodsSetout() + "→"
							+ settleAccountSDetialDto.getGoodsDestination());
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView no;
		private TextView money;
		private TextView type;
		private TextView content;

	}

}
