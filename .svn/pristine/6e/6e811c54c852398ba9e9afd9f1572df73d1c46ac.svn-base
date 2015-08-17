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
import com.seeyuan.logistics.entity.AccountLogDto;
import com.seeyuan.logistics.entity.DealManagerInfo;

/**
 * 交易管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class DealManagerListAdapter extends BaseAdapter {

	private List<AccountLogDto> mDataList;
	private Context context;

	public DealManagerListAdapter(Context context, List<AccountLogDto> mDataList) {
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
						R.layout.item_deal_manager_list, null);
				holder.number = (TextView) convertView
						.findViewById(R.id.deal_manager_number);
				holder.from = (TextView) convertView
						.findViewById(R.id.deal_manager_from);
				holder.to = (TextView) convertView
						.findViewById(R.id.deal_manager_to);
				holder.money = (TextView) convertView
						.findViewById(R.id.deal_manager_money);
				holder.time = (TextView) convertView
						.findViewById(R.id.deal_manager_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final AccountLogDto dealManagerInfo = mDataList.get(position);
			holder.number.setText(TextUtils.isEmpty(dealManagerInfo
					.getRefBillNo()) ? "" : dealManagerInfo.getRefBillNo());
			holder.from.setText(TextUtils.isEmpty(dealManagerInfo
					.getUsernameZc()) ? "" : dealManagerInfo.getUsernameZc());
			holder.to
					.setText(TextUtils.isEmpty(dealManagerInfo.getUsernameZr()) ? ""
							: dealManagerInfo.getUsernameZr());
			holder.money.setText(null == dealManagerInfo.getAmount() ? ""
					: dealManagerInfo.getAmount().toString());
			holder.time.setText(null == dealManagerInfo.getCreatedTime() ? ""
					: dealManagerInfo.getCreatedTime().toString());
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView number;
		private TextView from;
		private TextView to;
		private TextView money;
		private TextView time;

	}

}
