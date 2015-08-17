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
import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 账号设置适配器
 * 
 * @author zhazhaobao
 * 
 */
public class AccountInfoListAdapter extends BaseAdapter {

	private List<AccountDto> mDataList;
	private Context context;

	public AccountInfoListAdapter(Context context, List<AccountDto> mDataList) {
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
						R.layout.item_acount_manager_list, null);
				holder.type = (TextView) convertView
						.findViewById(R.id.account_manager_type);
				holder.name = (TextView) convertView
						.findViewById(R.id.account_manager_name);
				holder.number = (TextView) convertView
						.findViewById(R.id.account_manager_number);
				holder.defaultIcon = (TextView) convertView
						.findViewById(R.id.account_manager_default);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final AccountDto accountInfo = mDataList.get(position);
			holder.number
					.setText(TextUtils.isEmpty(accountInfo.getAccountNum()) ? ""
							: accountInfo.getAccountNum());
			holder.type
					.setText(TextUtils.isEmpty(accountInfo.getAccType()) ? ""
							: CommonUtils.getBankName(accountInfo.getAccType()));
			holder.name.setText(TextUtils.isEmpty(accountInfo.getName()) ? ""
					: accountInfo.getName());
			holder.defaultIcon.setVisibility(CommonUtils
					.getCheckBoxType(accountInfo.getIsDefault()) ? View.VISIBLE
					: View.INVISIBLE);
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView type;
		private TextView name;
		private TextView number;
		private TextView defaultIcon;

	}

}
