package com.seeyuan.logistics.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.R.color;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.entity.DriverDto;
import com.seeyuan.logistics.util.CommonUtils;

/**
 * 司机管理适配器
 * 
 * @author zhazhaobao
 * 
 */
public class DriverManagerAdapter extends BaseAdapter {

	private List<DriverDto> mDataList = new ArrayList<DriverDto>();

	private Context context;

	/**
	 * 保存checkbox选中的id
	 */
	private List<Integer> selectedList = new ArrayList<Integer>();

	public DriverManagerAdapter(List<DriverDto> mDataList, Context context) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (null != mDataList) {

			// 没有重用converView。这样很取巧。不过没有找到合适的方法，暂且如此。待后期研究
			// if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_driver_manager, null);
			holder.layout = (RelativeLayout) convertView
					.findViewById(R.id.drivier_manager_layout);
			holder.headerImg = (RemoteImageView) convertView
					.findViewById(R.id.driver_header_iv);
			holder.userName = (TextView) convertView
					.findViewById(R.id.driver_name_tv);
			holder.userPhone = (TextView) convertView
					.findViewById(R.id.driver_phone_tv);
			holder.userType = (TextView) convertView
					.findViewById(R.id.driver_type_tv);
			holder.userTypeHint = (TextView) convertView
					.findViewById(R.id.driver_type_hint_tv);
			holder.addNewDriver = (ImageView) convertView
					.findViewById(R.id.driver_add_iv);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.driver_check_ck);
			// convertView.setTag(holder);
			// } else {
			// holder = (ViewHolder) convertView.getTag();
			// }
			if (mDataList.get(position).isAddNewDriver()) {
				holder.headerImg.setVisibility(View.GONE);
				holder.userName.setVisibility(View.GONE);
				holder.userPhone.setVisibility(View.GONE);
				holder.userType.setVisibility(View.GONE);
				holder.userTypeHint.setVisibility(View.GONE);
				holder.checkBox.setVisibility(View.GONE);
				holder.addNewDriver.setVisibility(View.VISIBLE);
				holder.layout
						.setBackgroundResource(R.drawable.manager_button_selector2);
			} else {
				DriverDto info = mDataList.get(position);
				holder.headerImg.draw(null == info.getFace() ? null : info
						.getFace().getHeaderImgURL(),
						ConstantPool.DEFAULT_ICON_PATH, false, true);
				holder.userName.setText(context.getResources().getString(
						R.string.name_hint)
						+ info.getDriverName());
				holder.userPhone.setText(context.getResources().getString(
						R.string.phone_hint)
						+ info.getDriverTel());
				String userType = info.getDriverType();
				holder.userType.setTextColor(CommonUtils.getColor(context,
						userType));
				holder.userType.setText(CommonUtils.getTypeTitle(userType));
				holder.checkBox.setVisibility(info.isEditMode() ? View.VISIBLE
						: View.GONE);
				if (!info.isEditMode()) {
					holder.checkBox.setChecked(false);
				}
				holder.checkBox
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									selectedList.add(position);
								} else {
									if (selectedList.equals(String
											.valueOf(position))) {
										selectedList.remove(String
												.valueOf(position));
									}
								}
							}
						});
			}
		}

		return convertView;
	}

	public List<Integer> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<Integer> selectedList) {
		this.selectedList = selectedList;
	}

	final class ViewHolder {
		private RelativeLayout layout;
		private RemoteImageView headerImg;
		private TextView userName;
		private TextView userPhone;
		private TextView userType;
		private TextView userTypeHint;
		private ImageView addNewDriver;
		private CheckBox checkBox;
	}

}
