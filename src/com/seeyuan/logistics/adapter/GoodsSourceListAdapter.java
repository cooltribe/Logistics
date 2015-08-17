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
import com.seeyuan.logistics.entity.GoodsDto;
import com.seeyuan.logistics.util.CommonUtils;

public class GoodsSourceListAdapter extends BaseAdapter {

	private List<GoodsDto> mDataList;
	private Context context;

	public GoodsSourceListAdapter(Context context, List<GoodsDto> mDataList) {
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
						R.layout.item_goods_source_list1, null);
				holder.targetFrom = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_Start);
				holder.targetTo = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_End);
				holder.star = (RatingBar) convertView
						.findViewById(R.id.ratingBar);
				holder.star.setEnabled(false);
				holder.goodsType = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_GoodsName);
				holder.weight = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_GoodsWeight);
				holder.carType = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_GoodsType);
				holder.carLength = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_GoodsLen);
				holder.userName = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_Contacts);
				holder.phone = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_Contacts_Phone);
				holder.time = (TextView) convertView
						.findViewById(R.id.GoodsSourceItem_PublishDate);
				holder.assurance = (ImageView) convertView
						.findViewById(R.id.GoodsSourceItem_protect);
				holder.call = (Button) convertView
						.findViewById(R.id.GoodsSourceItem_CallPhone_But);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final GoodsDto goodsDto = mDataList.get(position);
			holder.targetFrom
					.setText(TextUtils.isEmpty(goodsDto.getSetout()) ? "未知"
							: goodsDto.getSetout());
			holder.targetTo
					.setText(TextUtils.isEmpty(goodsDto.getDestination()) ? "未知"
							: goodsDto.getDestination());
			// holder.star.setRating(goodsDto.getStar());
			holder.goodsType
					.setText(TextUtils.isEmpty(goodsDto.getGoodsType()) ? "未知"
							: goodsDto.getGoodsType());
			holder.weight.setText(null == goodsDto.getGoodsWeight() ? "未知"
					: String.valueOf(goodsDto.getGoodsWeight()) + "吨");
			holder.carType
					.setText(TextUtils.isEmpty(goodsDto.getVehType()) ? "未知"
							: goodsDto.getVehType());
			holder.carLength
					.setText(TextUtils.isEmpty(goodsDto.getVehLegth()) ? "未知"
							: goodsDto.getVehLegth() + "米");
			holder.userName
					.setText(TextUtils.isEmpty(goodsDto.getContactName()) ? "未知"
							: goodsDto.getContactName());
			holder.phone.setText(CommonUtils.encryptionString(
					goodsDto.getmPhone(), 4));
			SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
			String time = "未知";
			// try {
			// time = data.format(goodsDto.getDeliveryDateF()) + "至"
			// + data.format(goodsDto.getDeliveryDateT());
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// holder.time.setText(time);
			holder.time.setText(null == goodsDto.getDeliveryDateT() ? "未知"
					: "截止日期:"
							+ data.format(goodsDto.getDeliveryDateT())
									.toString());
			// holder.assurance.setVisibility(goodsDto
			// .isAssurance() ? View.VISIBLE : View.GONE);
			holder.call.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					CommonUtils.makeingCalls(context, goodsDto.getmPhone());
				}
			});
		}

		return convertView;
	}

	public class ViewHolder {

		private TextView targetFrom;
		private TextView targetTo;
		private RatingBar star;
		private TextView goodsType;
		private TextView weight;
		private TextView carType;
		private TextView carLength;
		private TextView userName;
		private TextView phone;
		private TextView time;
		private Button call;
		private ImageView assurance;
	}

}
