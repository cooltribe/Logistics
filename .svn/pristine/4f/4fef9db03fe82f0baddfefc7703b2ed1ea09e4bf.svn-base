package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.TabAroundGoodsAdapter;
import com.seeyuan.logistics.entity.GoodsSourceInfo;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;

public class TabAroundGoodsActivity extends LinearLayout implements
		OnItemClickListener {

	private PullToRefreshListView tab_around_goods_refreshview;

	private Context context;

	private ListView mListView;

	public TabAroundGoodsActivity(Context context) {
		super(context);
		this.context = context;
		initView();
		initAdapter();
	}

	public void onClickListener(View view) {
		// TODO Auto-generated method stub

	}

	public void initView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.activity_tab_around_goods, this);
		tab_around_goods_refreshview = (PullToRefreshListView) view
				.findViewById(R.id.tab_around_goods_refreshview);
		tab_around_goods_refreshview.setOnRefreshListener(mOnrefreshListener);

	}

	/**
	 * 初始化适配器
	 */
	private void initAdapter() {

		// GoodsSourceInfo a = new GoodsSourceInfo();
		List<GoodsSourceInfo> mDataList = new ArrayList<GoodsSourceInfo>();
		// a.setTargetFrom("南京");
		// a.setTargetTo("上海");
		// a.setUserName("赵先生");
		// a.setPhone("123456789");
		// a.setStar(3);
		// a.setGoodsType("设备");
		// a.setGoodsWeight("20");
		// a.setCarType("平板车");
		// a.setCarLength("20");
		// a.setDistance("200");
		// a.setTime("2014.7.21");
		//
		// GoodsSourceInfo a1 = new GoodsSourceInfo();
		// a1.setTargetFrom("上海");
		// a1.setTargetTo("深圳");
		// a1.setUserName("钱先生");
		// a1.setPhone("123456789");
		// a1.setStar(2);
		// a1.setGoodsType("设备");
		// a1.setGoodsWeight("10");
		// a1.setCarType("平板车");
		// a1.setCarLength("10");
		// a1.setDistance("500");
		// a1.setTime("2014.7.21");
		//
		// GoodsSourceInfo a2 = new GoodsSourceInfo();
		// a2.setTargetFrom("南京");
		// a2.setTargetTo("天津");
		// a2.setUserName("孙先生");
		// a2.setPhone("123456789");
		// a2.setStar(4);
		// a2.setGoodsType("设备");
		// a2.setGoodsWeight("40");
		// a2.setCarType("平板");
		// a2.setCarLength("50");
		// a2.setDistance("700米");
		// a2.setTime("2014.7.21");
		//
		// mDataList.add(a);
		// mDataList.add(a1);
		// mDataList.add(a2);
		TabAroundGoodsAdapter adapter = new TabAroundGoodsAdapter(mDataList,
				context);

		mListView = tab_around_goods_refreshview.getRefreshableView();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
	}

	OnRefreshListener mOnrefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				tab_around_goods_refreshview.onRefreshComplete();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
				tab_around_goods_refreshview.onRefreshComplete();
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}
}
