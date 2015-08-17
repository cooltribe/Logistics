package com.seeyuan.logistics.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.util.ToastUtil;

public class CityTypeAdapter extends BaseAdapter {

	private List<String> mDataList;
	private Context context;
	private Activity activity;
	private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
	private List<String> selectedCityList = new ArrayList<String>();
	private boolean isCanMultipleChoice = false;
	private int mulitpleChoiceMaxNum = 7;
	private int currentMultipleNum = 0;
	private String currentPlace;

	public CityTypeAdapter(Context context, Activity activity,
			List<String> mDataList) {
		this.context = context;
		this.mDataList = mDataList;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void clear() {
		if (mDataList == null)
			return;
		mDataList.clear();
	}

	public List<String> getmDataList() {
		return mDataList;
	}

	public void setmDataList(List<String> mDataList) {
		this.mDataList = mDataList;
	}

	public boolean isCanMultipleChoice() {
		return isCanMultipleChoice;
	}

	public void setCanMultipleChoice(boolean isCanMultipleChoice) {
		this.isCanMultipleChoice = isCanMultipleChoice;
	}

	public int getMulitpleChoiceMaxNum() {
		return mulitpleChoiceMaxNum;
	}

	public void setMulitpleChoiceMaxNum(int mulitpleChoiceMaxNum) {
		this.mulitpleChoiceMaxNum = mulitpleChoiceMaxNum;
	}

	public String getCurrentPlace() {
		return currentPlace;
	}

	public void setCurrentPlace(String currentPlace) {
		this.currentPlace = currentPlace;
	}

	public List<String> getSelectedCityList() {
		return selectedCityList;
	}

	public void setSelectedCityList(List<String> selectedCityList) {
		this.selectedCityList = selectedCityList;
	}

	@Override
	public View getView(final int position, View convertView,
			ViewGroup viewGroup) {

		final ViewHolder holder;
		if (null != mDataList) {

			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_city_style, null);
				holder.cityName = (TextView) convertView
						.findViewById(R.id.item_city_type);
				holder.checkBox = (CheckBox) convertView
						.findViewById(R.id.item_city_checkbok);
				// 热点提示，如果后期需要改图片，先做好预备工作
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 找到需要选中的条目
			if (isCheckMap != null && isCheckMap.containsKey(position)) {
				holder.checkBox.setChecked(isCheckMap.get(position));
			} else {
				holder.checkBox.setChecked(false);
			}

			holder.cityName.setText(mDataList.get(position).toString());
			holder.cityName.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String cityName = mDataList.get(position).toString();
					if (!holder.checkBox.isChecked()) {
						holder.checkBox.setChecked(true);
						isCheckMap.put(position, true);
						doItemOnClicked(cityName, position/* , holder.checkBox */);
					} else {
						holder.checkBox.setChecked(false);
						// 取消选中的则剔除
						currentMultipleNum--;
						isCheckMap.remove(position);
						selectedCityList.remove(cityName);
					}
				}
			});

			// holder.checkBox.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View view) {
			// String cityName = mDataList.get(position).toString();
			// if (holder.checkBox.isChecked()) {
			// isCheckMap.put(position, true);
			// doItemOnClicked(cityName, position, holder.checkBox);
			// } else {
			// // 取消选中的则剔除
			// currentMultipleNum--;
			// isCheckMap.remove(position);
			// selectedCityList.remove(cityName);
			// }
			// }
			// });

		}

		return convertView;
	}

	final class ViewHolder {
		private TextView cityName;
		private CheckBox checkBox;
	}

	/**
	 * 点击item事件处理,单选和多选的处理
	 */
	private void doItemOnClicked(String place, int position/*
															 * , CheckBox
															 * checkBox
															 */) {
		Intent intent = new Intent();
		// 将选中的放入hashmap中
		currentMultipleNum++;
		// 多选传递listview数据，单选传输string数据

		if (isCanMultipleChoice) {

			if (currentMultipleNum > mulitpleChoiceMaxNum) {
				currentMultipleNum--;
				// checkBox.setChecked(false);
				String toastString = context.getResources().getString(
						R.string.max_place_select_hint1)
						+ mulitpleChoiceMaxNum
						+ context.getResources().getString(
								R.string.max_place_select_hint2);
				ToastUtil.show(context, toastString);
			} else {
				currentPlace = currentPlace + "-" + place;
				selectedCityList.add(currentPlace);
			}
		} else {
			String city = place;
			String privice = currentPlace;
			if (!place.equalsIgnoreCase(currentPlace))
				currentPlace = currentPlace + "-" + place;
			intent.putExtra("place", currentPlace);
			intent.putExtra("city", city);
			intent.putExtra("privice", privice);
			activity.setResult(Activity.RESULT_OK, intent);
			activity.finish();
		}
	}
}
