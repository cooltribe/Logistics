package com.seeyuan.logistics.service;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chatuidemo.db.InviteMessgeDao;
import com.easemob.chatuidemo.db.UserDao;
import com.seeyuan.logistics.R;
import com.seeyuan.logistics.application.ConstantPool;

public class ShowIMNotification extends Service {

	// 账号在别处登录
	private boolean isConflict = false;
	private NewMessageBroadcastReceiver msgReceiver;
	private InviteMessgeDao inviteMessgeDao;
	private UserDao userDao;
	private Context context;

	private NotificationManager notificationManager = null;
	private Notification notification = new Notification();
	private RemoteViews mRemoteViews;
	private Builder builder = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		initIMManager();
		initNotification();
	}

	private void initIMManager() {
		inviteMessgeDao = new InviteMessgeDao(this);
		userDao = new UserDao(this);
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
	}

	private void initNotification() {

		mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification);
		mRemoteViews.setImageViewResource(R.id.image,
				android.R.drawable.stat_sys_download_done);

		builder = new Notification.Builder(ShowIMNotification.this);
		builder.setContent(mRemoteViews);
		builder.setOngoing(true);// 设置不可消失
		builder.setSmallIcon(R.drawable.icon)// 设置状态栏里面的图标（小图标）
												// 　　　　　　　　　　　　　　　　　　　　.setLargeIcon(BitmapFactory.decodeResource(res,
												// R.drawable.i5))//下拉下拉列表里面的图标（大图标）
												// 　　　　　　　.setTicker("this is bitch!")
												// //设置状态栏的显示的信息
				.setWhen(System.currentTimeMillis())// 设置时间发生时间
				.setAutoCancel(false)// 设置可以清除
				.setContentTitle("This is ContentTitle")// 设置下拉列表里的标题
				.setContentText("this is ContentText");// 设置上下文内容
		notification = builder.getNotification();// 获取一个Notification
		// updateNotification.flags |= Notification.FLAG_ONGOING_EVENT;
		// updateNotification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
		// 设置通知栏显示内容
		// updateNotification.icon = R.drawable.star_on;

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

			// 注销广播，否则在ChatActivity中会收到这个广播
			/**
			 * 发送Notification提醒。
			 */
			notificationManager.notify(ConstantPool.DEFAULT_NOTIFICATION_ID,
					notification);
			abortBroadcast();
		}
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
}
