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
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 结算管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class SettlementManagerListAdapter extends BaseAdapter {

	private List<AccountSettleDto> mDataList;
	private Context context;

	public SettlementManagerListAdapter(Context context,
			List<AccountSettleDto> mDataList) {
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
						R.layout.item_settlement_manager_list, null);
				holder.no = (TextView) convertView
						.findViewById(R.id.settlement_manager_number);
				holder.money = (TextView) convertView
						.findViewById(R.id.settlement_manager_money);
				holder.type = (TextView) convertView
						.findViewById(R.id.settlement_manager_type);
				holder.expected = (TextView) convertView
						.findViewById(R.id.settlement_manager_expected);
				holder.actual = (TextView) convertView
						.findViewById(R.id.settlement_manager_actual);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final AccountSettleDto accountSettleDto = mDataList.get(position);
			holder.no
					.setText(TextUtils.isEmpty(accountSettleDto.getBillNo()) ? ""
							: accountSettleDto.getBillNo());
			holder.type
					.setText(TextUtils.isEmpty(accountSettleDto.getStatus()) ? ""
							: CommonUtils.getSettlementType(accountSettleDto
									.getStatus()));
			holder.expected
					.setText(null == accountSettleDto.getProcDeadline() ? ""
							: CommonUtils.parserTimestamp(accountSettleDto
									.getProcDeadline()));
			holder.money.setText(null == accountSettleDto.getSettAmount() ? ""
					: accountSettleDto.getSettAmount().toString());
			holder.actual
					.setText(null == accountSettleDto.getLastModifyTime() ? ""
							: CommonUtils.parserTimestamp(accountSettleDto
									.getLastModifyTime()));
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView no;
		private TextView money;
		private TextView type;
		private TextView expected;
		private TextView actual;

	}

}
