package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.adapter.TabAroundFriendAdapter;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.FindAroundFriendsHandler;
import com.seeyuan.logistics.entity.AroundFriends;
import com.seeyuan.logistics.entity.PersonalPositionInfo;
import com.seeyuan.logistics.jsonparser.AroundFriendsJsonParser;
import com.seeyuan.logistics.map.SearchFriendsMapActivity;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.seeyuan.logistics.pullrefreshview.PullToRefreshListView;
import com.seeyuan.logistics.util.ToastUtil;

public class TabAroundFriendsActivity extends LinearLayout implements
		OnDataReceiveListener {

	private Context context;

	private List<AroundFriends> mDataList = new ArrayList<AroundFriends>();

	private TabAroundFriendAdapter mAdapter;

	private PullToRefreshListView tab_around_friends_refreshview;

	private ListView mListView;

	/**
	 * 地图显示所有好友位置
	 */
	private Button tab_around_friends_location_button;

	public TabAroundFriendsActivity(Context context) {
		super(context);
		this.context = context;
		initView();
		initAdapter();
		initAroundFriendsInfo();
	}

	private void initAroundFriendsInfo() {
		PersonalPositionInfo positionInfo = new PersonalPositionInfo();
		positionInfo.setLatitude("123");
		positionInfo.setLongitude("123");
		positionInfo.setPhone("1");
		FindAroundFriendsHandler friendsHandler = new FindAroundFriendsHandler(
				context, positionInfo);
		friendsHandler.setOnDataReceiveListener(this);
		friendsHandler.startNetWork();
	}

	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.tab_around_friends_location_button:
			doFriendLocation();
			break;

		default:
			break;
		}
	}

	/**
	 * 地图显示好友定位
	 */
	private void doFriendLocation() {
		Intent intent = new Intent(context, SearchFriendsMapActivity.class);
		intent.putParcelableArrayListExtra("FirendPositionInfo",
				(ArrayList<? extends Parcelable>) mDataList);
		context.startActivity(intent);
	}

	public void initView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater
				.inflate(R.layout.activity_tab_around_friends, this);

		tab_around_friends_refreshview = (PullToRefreshListView) view
				.findViewById(R.id.tab_around_friends_refreshview);
		tab_around_friends_refreshview.setOnRefreshListener(mRefreshListener);

		mListView = tab_around_friends_refreshview.getRefreshableView();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});

		tab_around_friends_location_button = (Button) view
				.findViewById(R.id.tab_around_friends_location_button);

	}

	private void initAdapter() {
		// AroundFriends a = new AroundFriends();
		// a.setAuthentication(true);
		// a.setDescription("1111111111111111111111111");
		// a.setUserName("翟小姐");
		// AroundFriends a1 = new AroundFriends();
		// a1.setAuthentication(true);
		// a1.setDescription("222222222222222");
		// a1.setUserName("王小姐");
		// AroundFriends a2 = new AroundFriends();
		// a2.setAuthentication(false);
		// a2.setDescription("3333333333333");
		// a2.setUserName("李小姐");
		// mDataList.add(a);
		// mDataList.add(a1);
		// mDataList.add(a2);
		mAdapter = new TabAroundFriendAdapter(context, mDataList);
		mListView.setAdapter(mAdapter);

	}

	private OnRefreshListener mRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh(int scrollState) {
			if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
				initAroundFriendsInfo();
			} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {

			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		String dataString = null;
		tab_around_friends_refreshview.onRefreshComplete();
		switch (resultCode) {
		case NetWork.FIND_AROUND_FIRENDS_OK:
			parserFriendsInfo(dataString, data);
			break;
		case NetWork.FIND_AROUND_FIRENDS_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	private void parserFriendsInfo(String dataString, Object data) {
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			// mDataList.clear();
			mDataList = AroundFriendsJsonParser
					.parserAroundFriendsInfoList(dataString);
			if (mDataList == null)
				return;
			mAdapter.refresh(mDataList);
			tab_around_friends_location_button.setVisibility(View.VISIBLE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
