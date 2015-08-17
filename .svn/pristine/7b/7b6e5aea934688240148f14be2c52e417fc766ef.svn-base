package com.seeyuan.logistics.provider;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.entity.UserInfo;
import com.seeyuan.logistics.provider.CustomeTables.AccountTask_Table;
import com.seeyuan.logistics.provider.CustomeTables.UserInfoTask_Table;

public class DBOperate {

	private Context context;

	private static DBOperate instance = null;

	private DBOperate(Context c) {
		context = c;
	}

	public static DBOperate getInstance(Context context) {
		if (instance == null) {
			instance = new DBOperate(context);
		}
		return instance;
	}

	/********************* 插入新的数据库信息 ****************/
	public long insertUserinfo(UserInfo userInfo) {

		ContentValues content = UserInfoTask_Table.packetContentValue(userInfo);

		content.put(UserInfoTask_Table.CREATETIME, System.currentTimeMillis());

		Uri newUri = context.getContentResolver().insert(
				UserInfoTask_Table.USERINFO_CONTENT_URI, content);
		String uriString = newUri.toString();
		long id = -1;
		id = Long
				.parseLong(uriString.substring(uriString.lastIndexOf("/") + 1));
		if (id != -1) {
			// LogTag.i("insertNewAppinfo", "insert: " + appInfo.toString());
			return 1;
		}
		return -1;
	}

	public void updateUserInfo(UserInfo userInfo) {

		if (userInfo == null) {
			return;
		}
		// 判断是否存在列表
		UserInfo oldUserInfo = null;
		oldUserInfo = qurryUserInfoByUUID(userInfo.getUuId());

		// 根据判断是否为空，决定是更新数据，还是插入新的条目
		// 插入新的条目
		if (oldUserInfo == null) {
			insertUserinfo(userInfo);
		}
		// 更新
		else {
			ContentValues cv = new ContentValues();
			cv = CustomeTables.UserInfoTask_Table.packetContentValue(userInfo);
			context.getContentResolver().update(
					CustomeTables.UserInfoTask_Table.USERINFO_CONTENT_URI, cv,
					UserInfoTask_Table.UUID + "=?",
					new String[] { userInfo.getUuId() });
		}

	}

	/**
	 * 修改登录状态
	 * 
	 * @param userInfo
	 */
	public void updateUserInfoLoginType(UserInfo userInfo) {
		if (null == userInfo) {
			return;
		}

		ContentValues cv = new ContentValues();
		cv.put(CustomeTables.UserInfoTask_Table.ISLOGIN, userInfo.getIsLogin());
		context.getContentResolver().update(
				CustomeTables.UserInfoTask_Table.USERINFO_CONTENT_URI, cv,
				UserInfoTask_Table.UUID + "=?",
				new String[] { userInfo.getUuId() });

	}

	public long insertAccount(AccountDto accountInfo) {

		ContentValues content = AccountTask_Table
				.packetContentValue(accountInfo);

		content.put(AccountTask_Table.CREATETIME, System.currentTimeMillis());

		Uri newUri = context.getContentResolver().insert(
				AccountTask_Table.ACCOUNT_CONTENT_URI, content);
		String uriString = newUri.toString();
		long id = -1;
		id = Long
				.parseLong(uriString.substring(uriString.lastIndexOf("/") + 1));
		if (id != -1) {
			// LogTag.i("insertNewAppinfo", "insert: " + appInfo.toString());
			return 1;
		}
		return -1;
	}

	public void updateAccount(AccountDto accountInfo) {

		if (accountInfo == null) {
			return;
		}
		// 判断是否存在列表
		AccountDto oldAccountInfo = null;
		oldAccountInfo = qurryAccountByAccountID(accountInfo.getId());
		// oldAccountInfo =
		// qurryAccountByCreateTime(accountInfo.getCreateTime());

		// 根据判断是否为空，决定是更新数据，还是插入新的条目
		// 插入新的条目
		if (oldAccountInfo == null) {
			insertAccount(accountInfo);
		}
		// 更新
		else {
			ContentValues cv = new ContentValues();
			cv = CustomeTables.AccountTask_Table
					.packetContentValue(accountInfo);
			context.getContentResolver().update(
					CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI, cv,
					AccountTask_Table.ACCOUNT_ID + "=?",
					new String[] { accountInfo.getId() });
		}

	}

	/**
	 * 修改账号是否默认状态
	 * 
	 * @param accountInfo
	 *            账号实体类
	 */
	public void changeAccountDefaultType(AccountDto accountInfo) {
		if (null == accountInfo) {
			return;
		}

		List<AccountDto> mDataList = getAllAccount();
		changeAccountAllDefault(mDataList);
		ContentValues cv = new ContentValues();
		cv.put(CustomeTables.AccountTask_Table.DEFAULT_ACCOUNT,
				accountInfo.getIsDefault());
		context.getContentResolver().update(
				CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI, cv,
				AccountTask_Table.ACCOUNT_ID + "=?",
				new String[] { accountInfo.getId() });

	}

	/**
	 * 修改所有账号默认状态为默认
	 * 
	 * @param accountInfo
	 */
	public void changeAccountAllDefault(List<AccountDto> mDataList) {
		if (null == mDataList) {
			return;
		}
		for (AccountDto accountDto : mDataList) {
			ContentValues cv = new ContentValues();
			cv.put(CustomeTables.AccountTask_Table.DEFAULT_ACCOUNT, "N");
			context.getContentResolver().update(
					CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI, cv,
					AccountTask_Table.ACCOUNT_ID + "=?",
					new String[] { accountDto.getId() });
		}
	}

	/**************************** end 授权应用数据库操作 **********************************/

	/************************** 获取数据库数据操作 *************************************/

	public UserInfo getUesrInfoByUUID(String uuID) {
		UserInfo userInfo = null;
		if (TextUtils.isEmpty(uuID)) {
			return null;
		}

		Cursor cursor = context.getContentResolver().query(
				CustomeTables.UserInfoTask_Table.USERINFO_CONTENT_URI, null,
				CustomeTables.UserInfoTask_Table.UUID + "=?",
				new String[] { uuID }, null);
		// cursor一定要判空
		if (cursor != null) {
			while (cursor.moveToNext()) {
				userInfo = CustomeTables.UserInfoTask_Table
						.packetUserInfoFormCursor(cursor);
			}
			cursor.close();
		}
		return userInfo;

	}

	public UserInfo getUesrInfoByUserName(String userName) {
		UserInfo userInfo = null;
		if (TextUtils.isEmpty(userName)) {
			return null;
		}

		Cursor cursor = context.getContentResolver().query(
				CustomeTables.UserInfoTask_Table.USERINFO_CONTENT_URI, null,
				CustomeTables.UserInfoTask_Table.USER_NAME + "=?",
				new String[] { userName }, null);
		// cursor一定要判空
		if (cursor != null) {
			while (cursor.moveToNext()) {
				userInfo = CustomeTables.UserInfoTask_Table
						.packetUserInfoFormCursor(cursor);
			}
			cursor.close();
		}
		return userInfo;

	}

	/**
	 * 获取所有的账号信息
	 * 
	 * @return
	 */
	public List<AccountDto> getAllAccount() {
		List<AccountDto> list = new ArrayList<AccountDto>();

		Cursor cursor = context.getContentResolver().query(
				CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI, null,
				null, null, null);
		// cursor一定要判空
		if (cursor != null) {

			while (cursor.moveToNext()) {
				AccountDto temc = CustomeTables.AccountTask_Table
						.packetAccountInfoFormCursor(cursor);

				list.add(temc);
			}

			// cursor一定要关闭
			cursor.close();
		}
		return list;

	}

	/************************** end获取数据库数据操作 *************************************/

	/************************** 查询数据库数据操作 *************************************/
	/**
	 * 查询方法实例
	 * 
	 * @param userid
	 * @return
	 */
	public UserInfo qurryUserInfoByUUID(String uuid) {
		UserInfo userInfo = null;
		if (TextUtils.isEmpty(uuid)) {
			return null;
		}

		Cursor cursor = context.getContentResolver().query(
				CustomeTables.UserInfoTask_Table.USERINFO_CONTENT_URI, null,
				UserInfoTask_Table.UUID + "=?", new String[] { uuid }, null);
		// cursor一定要判空
		if (cursor != null) {

			while (cursor.moveToNext()) {
				userInfo = CustomeTables.UserInfoTask_Table
						.packetUserInfoFormCursor(cursor);

			}
			// cursor一定要关闭
			cursor.close();
			return userInfo;
		}
		return null;

	}

	/**
	 * 查询方法实例
	 * 
	 * @param accountid
	 * @return
	 */
	public AccountDto qurryAccountByAccountID(String accountID) {
		AccountDto accountInfo = null;
		if (TextUtils.isEmpty(accountID)) {
			return null;
		}

		Cursor cursor = context.getContentResolver().query(
				CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI, null,
				AccountTask_Table.ACCOUNT_ID + "=?",
				new String[] { accountID }, null);
		// cursor一定要判空
		if (cursor != null) {

			while (cursor.moveToNext()) {
				accountInfo = CustomeTables.AccountTask_Table
						.packetAccountInfoFormCursor(cursor);

			}
			// cursor一定要关闭
			cursor.close();
			return accountInfo;
		}
		return null;

	}

	/**
	 * 查询方法实例
	 * 
	 * @param accountid
	 * @return
	 */
	public AccountDto qurryAccountByCreateTime(String createTime) {
		AccountDto accountInfo = null;
		if (TextUtils.isEmpty(createTime)) {
			return null;
		}

		Cursor cursor = context.getContentResolver().query(
				CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI, null,
				AccountTask_Table.CREATETIME + "=?",
				new String[] { createTime }, null);
		// cursor一定要判空
		if (cursor != null) {

			while (cursor.moveToNext()) {
				accountInfo = CustomeTables.AccountTask_Table
						.packetAccountInfoFormCursor(cursor);

			}
			// cursor一定要关闭
			cursor.close();
			return accountInfo;
		}
		return null;

	}

	/************************** end查询数据库数据操作 *************************************/

	/************************** 删除数据库数据操作 ****************************************/
	public int deleteAccountByAccountID(String accountID) {
		// 根据id删除
		int rows = context.getContentResolver().delete(
				CustomeTables.AccountTask_Table.ACCOUNT_CONTENT_URI,
				CustomeTables.AccountTask_Table.ACCOUNT_ID + " =? ",
				new String[] { accountID });

		return rows;
	}

}
