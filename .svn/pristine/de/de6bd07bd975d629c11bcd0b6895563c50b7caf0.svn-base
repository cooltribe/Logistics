package com.seeyuan.logistics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.User;
import com.easemob.util.HanziToPinyin;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.util.CommonUtils;
import com.seeyuan.logistics.util.LogTag;

public class LoginIMServerService extends Service {

	private Context context;
	private String username;
	private String password;
	private SharedPreferences sPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		username = CommonUtils.getUserName(context);
		sPreferences = getSharedPreferences(ConstantPool.LOGISTICS_PREFERENCES,
				Context.MODE_PRIVATE);
		// password = CommonUtils.getPassword(context);
		// 统一环信账号密码
		password = "123456";
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		new Thread(new Runnable() {

			@Override
			public void run() {
				registerIM(username, password);
			}
		}).start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void loginIMServer(String userName, String password) {

		if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
			// 调用sdk登陆方法登陆聊天服务器
			EMChatManager.getInstance().login(userName, password,
					new EMCallBack() {

						@Override
						public void onSuccess() {
							LogTag.d("TAG", "聊天服务器登录成功");
							Editor editor = sPreferences.edit();
							editor.putBoolean("isIMLoginSuccess", true);// 环形登录成功
							editor.commit();
							try {
								// demo中简单的处理成每次登陆都去获取好友username，开发者自己根据情况而定
								List<String> usernames = EMContactManager
										.getInstance().getContactUserNames();
								Map<String, User> userlist = new HashMap<String, User>();
								for (String username : usernames) {
									User user = new User();
									user.setUsername(username);
									setUserHearder(username, user);
									userlist.put(username, user);
								}
								// 添加user"申请与通知"
								User newFriends = new User();
								newFriends
										.setUsername(Constant.NEW_FRIENDS_USERNAME);
								newFriends.setNick("申请与通知");
								newFriends.setHeader("");
								userlist.put(Constant.NEW_FRIENDS_USERNAME,
										newFriends);
								// 添加"群聊"
								User groupUser = new User();
								groupUser.setUsername(Constant.GROUP_USERNAME);
								groupUser.setNick("群聊");
								groupUser.setHeader("");
								userlist.put(Constant.GROUP_USERNAME, groupUser);

								// 存入内存
								ApplicationPool.getInstance().setContactList(
										userlist);
								// 存入db
								UserDao dao = new UserDao(context);
								List<User> users = new ArrayList<User>(userlist
										.values());
								dao.saveContactList(users);

								// 获取群聊列表(群聊里只有groupid和groupname的简单信息),sdk会把群组存入到内存和db中
								EMGroupManager.getInstance()
										.getGroupsFromServer();
								stopSelf();

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onProgress(int progress, String status) {

						}

						@Override
						public void onError(int code, final String message) {
							LogTag.d("TAG", "登录失败,重新登录");
//							loginIMServer(username,
//									LoginIMServerService.this.password);
						}
					});
		}

	}

	/**
	 * 注册IM
	 */
	private void registerIM(String userName, String pwd) {
		// 调用sdk注册方法
		try {
			EMChatManager.getInstance().createAccountOnServer(userName, pwd);
			// 注册成功，登录
			loginIMServer(userName, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage();
			if (errorMsg.indexOf("conflict") != -1) {
				// 用户已经存在，登录
				loginIMServer(userName, pwd);
			} else if (errorMsg.indexOf("EMNetworkUnconnectedException") != -1) {
				
			}
		}
	}

	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param username
	 * @param user
	 */
	protected void setUserHearder(String username, User user) {
		String headerName = null;
		if (!TextUtils.isEmpty(user.getNick())) {
			headerName = user.getNick();
		} else {
			headerName = user.getUsername();
		}
		if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
			user.setHeader("");
		} else if (Character.isDigit(headerName.charAt(0))) {
			user.setHeader("#");
		} else {
			user.setHeader(HanziToPinyin.getInstance()
					.get(headerName.substring(0, 1)).get(0).target.substring(0,
					1).toUpperCase());
			char header = user.getHeader().toLowerCase().charAt(0);
			if (header < 'a' || header > 'z') {
				user.setHeader("#");
			}
		}
	}

	@Override
	public void onDestroy() {
		Log.d("TAG", "关闭环信");
		super.onDestroy();
	}

}
