package com.seeyuan.logistics.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mesada.nj.pubcontrols.controls.RemoteImageView;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.entity.AroundFriends;

public class TabAroundFriendAdapter extends BaseAdapter {

	private List<AroundFriends> mDataList;

	private Context context;

	public TabAroundFriendAdapter(Context context, List<AroundFriends> mDataList) {
		this.context = context;
		this.mDataList = mDataList;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int positon) {
		return mDataList.get(positon);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	public void refresh(List<AroundFriends> mDataList) {
		this.mDataList = mDataList;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		final ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_around_friends_style, null);
				holder.headerImage = (RemoteImageView) convertView
						.findViewById(R.id.item_around_friends_headerimg);
				holder.userName = (TextView) convertView
						.findViewById(R.id.item_around_friends_username);
				holder.authentication = (TextView) convertView
						.findViewById(R.id.item_around_friends_authentication);
				holder.description = (TextView) convertView
						.findViewById(R.id.item_around_friends_description);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 头像的绘制等图片服务器配置OK 然后进行调配
			holder.headerImage.draw(mDataList.get(position).getHeadImageURL(),
					ConstantPool.DEFAULT_ICON_PATH, false,true);
			holder.userName.setText(mDataList.get(position).getUserName());
			holder.description
					.setText(mDataList.get(position).getDescription());
			holder.authentication.setVisibility(mDataList.get(position)
					.isAuthentication() ? View.VISIBLE : View.INVISIBLE);
		}

		return convertView;
	}

	class ViewHolder {
		private RemoteImageView headerImage;
		private TextView userName;
		private TextView authentication;
		private TextView description;
	}
}
