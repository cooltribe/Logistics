package com.seeyuan.logistics.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import io.yunba.android.manager.YunBaManager;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chatuidemo.activity.ChatAllHistoryFragment;
import com.easemob.chatuidemo.db.InviteMessgeDao;
import com.easemob.chatuidemo.db.UserDao;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.entity.CarLengthInfo;
import com.seeyuan.logistics.entity.CarsDto;
import com.seeyuan.logistics.entity.LocationDto;
import com.seeyuan.logistics.entity.PdaPagination;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.jsonparser.CarSourceJsonParser;
import com.seeyuan.logistics.receiver.YunbaReceiver;
import com.seeyuan.logistics.service.BDLocationService;
import com.seeyuan.logistics.service.CarBDLocationService;
import com.seeyuan.logistics.service.CheckUpdateService;
import com.seeyuan.logistics.service.LoginIMServerService;
import com.seeyuan.logistics.service.ShowIMNotification;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.NotificaionUtil;
import com.seeyuan.logistics.util.ToastUtil;
import com.seeyuan.logistics.xmlparser.HttpUtil;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener, OnClickListener {

	private RadioButton[] mTabs;
	private HomePageActivity homepage;
	// private ChatHistoryFragment chatHistoryFragment;
	private AroundActivity around;
	private ChatAllHistoryFragment chat;
	private Fragment[] fragments;
	private int index;
	// 当前fragment的index
	private int currentTabIndex;

	private RadioButton radio_homepage;
	private RadioButton radio_around;
	private RadioButton radio_chat;

	/**
	 * 返回键
	 */
	private ImageView maintitle_back;

	/**
	 * 更多其他操作
	 */
	private ImageView maintitle_more;

	/**
	 * 2次退出记录时间
	 */
	private long mKeyTime;

	private Context context;

	// 账号在别处登录
	private boolean isConflict = false;
	private NewMessageBroadcastReceiver msgReceiver;
	private InviteMessgeDao inviteMessgeDao;
	private UserDao userDao;

	private TextView unreadLabel;

	private YunbaReceiver yunbaReceiver;
	private SharedPreferences sPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		initView();
		initIMManager();
		ApplicationPool.setServerIp(context);
		if (getIntent().getExtras().getInt("myTag") == 1) {
			startCarServer();
		}
		startBDService();
		
		// startIMNotificationService();
		CommonUtils.addActivity(this);
		// Intent imIntent = new Intent(MainActivity.this,
		// LoginIMServerService.class);
		// startService(imIntent);
		YunBaManager.resume(context);
	}
	/**
	 * 车辆信息服务
	 */
	private void startCarServer(){
		Intent intent = new Intent(MainActivity.this, CarBDLocationService.class);
		startService(intent);
	}
	/**
	 * 个人位置信息
	 */
	private void startBDService() {
		Intent intent = new Intent(MainActivity.this, BDLocationService.class);
		startService(intent);
	}
	
	

	private void startIMNotificationService() {
		Intent intent = new Intent(MainActivity.this, ShowIMNotification.class);
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(new Intent(MainActivity.this, CarBDLocationService.class));
		sPreferences.edit().remove("mobile").remove("carsNum").commit();
		stopService(new Intent(MainActivity.this, BDLocationService.class));
		stopService(new Intent(MainActivity.this, LoginIMServerService.class));
		// stopService(new Intent(MainActivity.this, ShowIMNotification.class));
		// 注销广播接收者
		try {
			unregisterReceiver(msgReceiver);
			unregisterReceiver(ackMessageReceiver);
			unregisterReceiver(yunbaReceiver);
			YunBaManager.stop(context);
			NotificaionUtil.clearNotification(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		View maintitle = (View) findViewById(R.id.maintitle);
		maintitle_back = (ImageView) maintitle
				.findViewById(R.id.maintitle_back);
		maintitle_back.setOnClickListener(this);

		maintitle_more = (ImageView) findViewById(R.id.maintitle_more);
		maintitle_more.setOnClickListener(this);

		radio_homepage = (RadioButton) findViewById(R.id.radio_homepage);
		radio_homepage.setPadding(ApplicationPool.screenWidth / 9, 0, 0, 0);
		radio_around = (RadioButton) findViewById(R.id.radio_around);
		radio_around.setPadding(ApplicationPool.screenWidth / 9, 0, 0, 0);

		radio_chat = (RadioButton) findViewById(R.id.radio_chat);
		radio_chat.setPadding(ApplicationPool.screenWidth / 9, 0, 0, 0);

		unreadLabel = (TextView) findViewById(R.id.unread_msg_number);

		mTabs = new RadioButton[3];
		mTabs[0] = radio_homepage;
		mTabs[1] = radio_around;
		mTabs[2] = radio_chat;
		// 把第一个tab设为选中状态
		mTabs[0].setSelected(true);
		mTabs[0].setClickable(true);

		// 显示所有人消息记录的fragment
		chat = new ChatAllHistoryFragment();
		homepage = new HomePageActivity();
		around = new AroundActivity();
		fragments = new Fragment[] { homepage, around, chat };
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homepage)
				.add(R.id.fragment_container, chat).hide(chat).show(homepage)
				.commit();
	}

	private void initIMManager() {
		inviteMessgeDao = new InviteMessgeDao(this);
		userDao = new UserDao(this);
		// 注册一个接收消息的BroadcastReceiver
		msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(101);
		registerReceiver(msgReceiver, intentFilter);

		// 注册一个ack回执消息的BroadcastReceiver
		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getAckMessageBroadcastAction());
		ackMessageIntentFilter.setPriority(101);
		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

		// 注册一个离线消息的BroadcastReceiver
		// IntentFilter offlineMessageIntentFilter = new
		// IntentFilter(EMChatManager.getInstance()
		// .getOfflineMessageBroadcastAction());
		// registerReceiver(offlineMessageReceiver, offlineMessageIntentFilter);

		// setContactListener监听联系人的变化等
		// EMContactManager.getInstance().setContactListener(new
		// MyContactListener());
		// 注册一个监听连接状态的listener
		// EMChatManager.getInstance().addConnectionListener(new
		// MyConnectionListener());
		// 注册群聊相关的listener
		// EMGroupManager.getInstance().addGroupChangeListener(new
		// MyGroupChangeListener());
		// 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
		EMChat.getInstance().setAppInited();

		yunbaReceiver = new YunbaReceiver();
		IntentFilter yunbaFilter = new IntentFilter(
				"io.yunba.android.MESSAGE_RECEIVED_ACTION");
		yunbaFilter.addCategory("com.seeyuan.logistics");
		registerReceiver(yunbaReceiver, yunbaFilter);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back:
			doSecendBack();
			break;
		case R.id.maintitle_more:
			doMoreControl();
			break;

		default:
			break;
		}
	}

	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.radio_homepage:
			index = 0;
			break;
		case R.id.radio_around:
			index = 1;
			break;
		case R.id.radio_chat:
			index = 2;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	/**
	 * 执行更多操作
	 */
	private void doMoreControl() {

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			doSecendBack();
			return false;
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 2次退出
	 */
	private void doSecendBack() {
		if (MainActivity.this.isFinishing()) {
			return;
		}
		if (currentTabIndex != 0) {
			currentTabIndex = 0;
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[1]).hide(fragments[2]).show(fragments[0])
					.commit();
			radio_around.setChecked(false);
			radio_chat.setChecked(false);
			radio_homepage.setChecked(true);
		} else {
			long currentTime = System.currentTimeMillis();
			if (currentTime - mKeyTime > 2000) {
				mKeyTime = currentTime;
				ToastUtil.show(context, R.string.Secend_Back_hint);
			} else {
				CommonUtils.finishAllActivity();
			}
		}
	}

	/**
	 * 新消息广播接收者
	 * 
	 * 
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent) {
			// 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看
			// showNotification(intent);
			// 消息id
			String msgId = intent.getStringExtra("msgid");

			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			// EMMessage message =
			// EMChatManager.getInstance().getMessage(msgId);
			// 刷新bottom bar消息未读数
			updateUnreadLabel();
			if (currentTabIndex == 0) {
				// 当前页面如果为聊天历史页面，刷新此页面
				if (chat != null) {
					chat.refresh();
				}
			}
			// 注销广播，否则在ChatActivity中会收到这个广播
			abortBroadcast();
		}
	}

	/**
	 * 刷新未读消息数
	 */
	public void updateUnreadLabel() {
		int count = getUnreadMsgCountTotal();
		if (count > 0) {
			unreadLabel.setText(String.valueOf(count));
			unreadLabel.setVisibility(View.VISIBLE);
		} else {
			unreadLabel.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 获取未读消息数
	 * 
	 * @return
	 */
	public int getUnreadMsgCountTotal() {
		int unreadMsgCountTotal = 0;
		unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
		return unreadMsgCountTotal;
	}

	/**
	 * 消息回执BroadcastReceiver
	 */
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isAcked = true;
				}
			}
			abortBroadcast();
		}
	};
	private List<CarsDto> carsList;

	/***
	 * 好友变化listener
	 * 
	 */
	// private class MyContactListener implements EMContactListener {
	//
	// @Override
	// public void onContactAdded(List<String> usernameList) {
	// // 保存增加的联系人
	// Map<String, User> localUsers =
	// DemoApplication.getInstance().getContactList();
	// Map<String, User> toAddUsers = new HashMap<String, User>();
	// for (String username : usernameList) {
	// User user = setUserHead(username);
	// // 暂时有个bug，添加好友时可能会回调added方法两次
	// if (!localUsers.containsKey(username)) {
	// userDao.saveContact(user);
	// }
	// toAddUsers.put(username, user);
	// }
	// localUsers.putAll(toAddUsers);
	// // 刷新ui
	// if (currentTabIndex == 1)
	// contactListFragment.refresh();
	//
	// }
	//
	//
	// @Override
	// public void onContactDeleted(final List<String> usernameList) {
	// // 被删除
	// Map<String, User> localUsers =
	// DemoApplication.getInstance().getContactList();
	// for (String username : usernameList) {
	// localUsers.remove(username);
	// userDao.deleteContact(username);
	// inviteMessgeDao.deleteMessage(username);
	// }
	// runOnUiThread(new Runnable() {
	// public void run() {
	// //如果正在与此用户的聊天页面
	// if (ChatActivity.activityInstance != null &&
	// usernameList.contains(ChatActivity.activityInstance.getToChatUsername()))
	// {
	// Toast.makeText(MainActivity.this,
	// ChatActivity.activityInstance.getToChatUsername()+"已把你从他好友列表里移除",
	// 1).show();
	// ChatActivity.activityInstance.finish();
	// }
	// updateUnreadLabel();
	// }
	// });
	// // 刷新ui
	// if (currentTabIndex == 1)
	// contactListFragment.refresh();
	//
	// }
	//
	// @Override
	// public void onContactInvited(String username, String reason) {
	// // 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不要重复提醒
	// List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
	// for (InviteMessage inviteMessage : msgs) {
	// if (inviteMessage.getGroupId() == null &&
	// inviteMessage.getFrom().equals(username)) {
	// inviteMessgeDao.deleteMessage(username);
	// }
	// }
	// // 自己封装的javabean
	// InviteMessage msg = new InviteMessage();
	// msg.setFrom(username);
	// msg.setTime(System.currentTimeMillis());
	// msg.setReason(reason);
	// Log.d(TAG, username + "请求加你为好友,reason: " + reason);
	// // 设置相应status
	// msg.setStatus(InviteMesageStatus.BEINVITEED);
	// notifyNewIviteMessage(msg);
	//
	// }
	//
	// @Override
	// public void onContactAgreed(String username) {
	// List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
	// for (InviteMessage inviteMessage : msgs) {
	// if (inviteMessage.getFrom().equals(username)) {
	// return;
	// }
	// }
	// // 自己封装的javabean
	// InviteMessage msg = new InviteMessage();
	// msg.setFrom(username);
	// msg.setTime(System.currentTimeMillis());
	// Log.d(TAG, username + "同意了你的好友请求");
	// msg.setStatus(InviteMesageStatus.BEAGREED);
	// notifyNewIviteMessage(msg);
	//
	// }
	//
	// @Override
	// public void onContactRefused(String username) {
	// // 参考同意，被邀请实现此功能,demo未实现
	//
	// }
	//
	// }

	/**
	 * 连接监听listener
	 * 
	 */
	// private class MyConnectionListener implements ConnectionListener {
	//
	// @Override
	// public void onConnected() {
	// chatHistoryFragment.errorItem.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void onDisConnected(String errorString) {
	// if (errorString != null && errorString.contains("conflict")) {
	// // 显示帐号在其他设备登陆dialog
	// showConflictDialog();
	// } else {
	// chatHistoryFragment.errorItem.setVisibility(View.VISIBLE);
	// if(NetUtils.hasNetwork(MainActivity.this))
	// chatHistoryFragment.errorText.setText("连接不到聊天服务器");
	// else
	// chatHistoryFragment.errorText.setText("当前网络不可用，请检查网络设置");
	//
	// }
	// }
	//
	// @Override
	// public void onReConnected() {
	// chatHistoryFragment.errorItem.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void onReConnecting() {
	// }
	//
	// @Override
	// public void onConnecting(String progress) {
	// }
	//
	// }

	/**
	 * MyGroupChangeListener
	 */
	// private class MyGroupChangeListener implements GroupChangeListener {
	//
	// @Override
	// public void onInvitationReceived(String groupId, String groupName, String
	// inviter, String reason) {
	// boolean hasGroup = false;
	// for(EMGroup group : EMGroupManager.getInstance().getAllGroups()){
	// if(group.getGroupId().equals(groupId)){
	// hasGroup = true;
	// break;
	// }
	// }
	// if(!hasGroup)
	// return;
	//
	// // 被邀请
	// EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
	// msg.setChatType(ChatType.GroupChat);
	// msg.setFrom(inviter);
	// msg.setTo(groupId);
	// msg.setMsgId(UUID.randomUUID().toString());
	// msg.addBody(new TextMessageBody(inviter + "邀请你加入了群聊"));
	// // 保存邀请消息
	// EMChatManager.getInstance().saveMessage(msg);
	// // 提醒新消息
	// EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();
	//
	// runOnUiThread(new Runnable() {
	// public void run() {
	// updateUnreadLabel();
	// // 刷新ui
	// if (currentTabIndex == 0)
	// chatHistoryFragment.refresh();
	// if
	// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
	// {
	// GroupsActivity.instance.onResume();
	// }
	// }
	// });
	//
	// }
	//
	// @Override
	// public void onInvitationAccpted(String groupId, String inviter, String
	// reason) {
	//
	// }
	//
	// @Override
	// public void onInvitationDeclined(String groupId, String invitee, String
	// reason) {
	//
	// }
	//
	// @Override
	// public void onUserRemoved(String groupId, String groupName) {
	// // 提示用户被T了，demo省略此步骤
	// // 刷新ui
	// runOnUiThread(new Runnable() {
	// public void run() {
	// try {
	// updateUnreadLabel();
	// if (currentTabIndex == 0)
	// chatHistoryFragment.refresh();
	// if
	// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
	// {
	// GroupsActivity.instance.onResume();
	// }
	// } catch (Exception e) {
	// Log.e("###", "refresh exception " + e.getMessage());
	// }
	//
	// }
	// });
	// }
	//
	// @Override
	// public void onGroupDestroy(String groupId, String groupName) {
	// // 群被解散
	// // 提示用户群被解散,demo省略
	// // 刷新ui
	// runOnUiThread(new Runnable() {
	// public void run() {
	// updateUnreadLabel();
	// if (currentTabIndex == 0)
	// chatHistoryFragment.refresh();
	// if
	// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
	// {
	// GroupsActivity.instance.onResume();
	// }
	// }
	// });
	//
	// }
	//
	// @Override
	// public void onApplicationReceived(String groupId, String groupName,
	// String applyer, String reason) {
	// // 用户申请加入群聊
	// InviteMessage msg = new InviteMessage();
	// msg.setFrom(applyer);
	// msg.setTime(System.currentTimeMillis());
	// msg.setGroupId(groupId);
	// msg.setGroupName(groupName);
	// msg.setReason(reason);
	// Log.d(TAG, applyer + " 申请加入群聊：" + groupName);
	// msg.setStatus(InviteMesageStatus.BEAPPLYED);
	// notifyNewIviteMessage(msg);
	// }
	//
	// @Override
	// public void onApplicationAccept(String groupId, String groupName, String
	// accepter) {
	// //加群申请被同意
	// EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
	// msg.setChatType(ChatType.GroupChat);
	// msg.setFrom(accepter);
	// msg.setTo(groupId);
	// msg.setMsgId(UUID.randomUUID().toString());
	// msg.addBody(new TextMessageBody(accepter + "同意了你的群聊申请"));
	// // 保存同意消息
	// EMChatManager.getInstance().saveMessage(msg);
	// // 提醒新消息
	// EMNotifier.getInstance(getApplicationContext()).notifyOnNewMsg();
	//
	// runOnUiThread(new Runnable() {
	// public void run() {
	// updateUnreadLabel();
	// // 刷新ui
	// if (currentTabIndex == 0)
	// chatHistoryFragment.refresh();
	// if
	// (CommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName()))
	// {
	// GroupsActivity.instance.onResume();
	// }
	// }
	// });
	// }
	//
	// @Override
	// public void onApplicationDeclined(String groupId, String groupName,
	// String decliner, String reason) {
	// //加群申请被拒绝，demo未实现
	// }
	//
	// }

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!isConflict) {
			updateUnreadLabel();
			// updateUnreadAddressLable();
			EMChatManager.getInstance().activityResumed();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

	}
}
