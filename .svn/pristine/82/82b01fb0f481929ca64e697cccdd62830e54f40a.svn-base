package com.seeyuan.logistics.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.seeyuan.logistics.entity.AccountDto;
import com.seeyuan.logistics.entity.UserInfo;

/**
 * 所有的表结构在这里定义 CustomeTables.java
 * 
 * @author zhazhaobao
 */
public final class CustomeTables {

	public static final String AUTHORITY = "com.seeyuan.logistics.logisticsprovider";

	/**
	 * This class cannot be instantiated
	 */
	private CustomeTables() {
	}

	public static final class UserInfoTask_Table implements BaseColumns {
		/**
		 * This class cannot be instantiated
		 */
		private UserInfoTask_Table() {
		}

		/**
		 * The content:// style URL for this table
		 */
		public static final Uri USERINFO_CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/userinfo_table");

		/**
		 * The default sort order for this table
		 */
		public static final String DEFAULT_SORT_ORDER = " createTime ASC";

		/**
		 * 会员级别
		 */
		public static final String LEVEL_ID = "LEVEL_ID";

		public static final String TYPE_ID = "TYPE_ID";
		/**
		 * 会员类型
		 */
		public static final String MEMBER_TYPE = "MEMBER_TYPE";

		/**
		 * 身份标示符
		 */
		public static final String UUID = "UUID";
		/**
		 * 会员用户名
		 */
		public static final String USER_NAME = "USER_NAME";
		/**
		 * 邮件地址
		 */
		public static final String EMAIL = "EMAIL";
		/**
		 * 邮件是否验证 Y 已验证 N 未验证
		 */
		public static final String IS_EMAIL_PROVEN = "IS_EMAIL_PROVEN";
		/**
		 * 手机
		 */
		public static final String MOBILE = "MOBILE";
		/**
		 * 密码
		 */
		public static final String PASSWORD = "PASSWORD";
		/**
		 * 注册时间
		 */
		public static final String REGISTER_TIME = "REGISTER_TIME";
		/**
		 * 昵称
		 */
		public static final String NICKNAME = "NICKNAME";
		/**
		 * 性别 0女 1男
		 */
		public static final String SEX = "SEX";
		/**
		 * 出生日期
		 */
		public static final String BIRTHDAY = "BIRTHDAY";
		/**
		 * 省
		 */
		public static final String PROVINCE = "PROVINCE";
		/**
		 * 市
		 */
		public static final String CITY = "CITY";
		/**
		 * 区
		 */
		public static final String REGION = "REGION";
		/**
		 * 详细地址
		 */
		public static final String ADDRESS = "ADDRESS";
		/**
		 * 邮编
		 */
		public static final String ZIP = "ZIP";
		/**
		 * 固定电话
		 */
		public static final String TEL = "TEL";
		/**
		 * QQ
		 */
		public static final String QQ = "QQ";
		/**
		 * 微信号
		 */
		public static final String WECHAT = "WECHAT";
		/**
		 * 个人资料完善程度
		 */
		public static final String INFO_COMPL = "INFO_COMPL";
		/**
		 * 推荐人
		 */
		public static final String REFEREE_NAME = "REFEREE_NAME";
		/**
		 * 推荐人手机号
		 */
		public static final String REFEREE_MOBILE = "REFEREE_MOBILE";
		/**
		 * 头像URL
		 */
		public static final String FACE = "FACE";
		/**
		 * 注册IP
		 */
		public static final String REGISTER_IP = "REGISTER_IP";
		/**
		 * 最后一次发送激活邮件时间
		 */
		public static final String LAST_SEND_EMAIL = "LAST_SEND_EMAIL";
		/**
		 * 最近位置_纬度
		 */
		public static final String LAST_LAT = "LAST_LAT";
		/**
		 * 最近位置_经度
		 */
		public static final String LAST_LNG = "LAST_LNG";

		/**
		 * 最后位置_详细地址
		 */
		public static final String LAST_LOCATION = "LAST_LOCATION";
		/**
		 * 当月登录次数
		 */
		public static final String LOGIN_COUNT = "LOGIN_COUNT";
		/**
		 * 最后登录时间
		 */
		public static final String LASTLOGIN_TIME = "LASTLOGIN_TIME";
		/**
		 * 最近登录IP
		 */
		public static final String LOGIN_IP = "LOGIN_IP";
		/**
		 * 识别号
		 */
		public static final String PIN_CODE = "PIN_CODE";
		/**
		 * 账号状态:关联数据字典，TYPE_CODE='MEMBER_STATUS'
		 */
		public static final String STATUS = "STATUS";
		/**
		 * 备注
		 */
		public static final String REMARK = "REMARK";

		public static final String CREATETIME = "CREATETIME";

		public static final String ISLOGIN = "ISLOGIN";

		/**
		 * 从Cursor中提取一个对象实例
		 * 
		 * @param cursor
		 * @return
		 */
		public static UserInfo packetUserInfoFormCursor(Cursor cursor) {
			UserInfo temp = new UserInfo();
			if (cursor != null) {
				int levelIDIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LEVEL_ID);
				int typeIDIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.TYPE_ID);
				int memberTypeIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.MEMBER_TYPE);
				int uuIdIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.UUID);
				int userNameIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.USER_NAME);
				int emailIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.EMAIL);
				int isEmailProvenIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.IS_EMAIL_PROVEN);
				int moblieIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.MOBILE);
				int passwordIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.PASSWORD);
				int registerTimeIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.REGISTER_TIME);
				int nickNameIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.NICKNAME);
				int sexIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.SEX);
				int birthdayIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.BIRTHDAY);
				int provinceIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.PROVINCE);
				int cityIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.CITY);
				int regionIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.REGION);
				int addressIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.ADDRESS);
				int zipIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.ZIP);
				int telIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.TEL);
				int qqIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.QQ);
				int wechatIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.WECHAT);
				int infoComplIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.INFO_COMPL);
				int refereeNameIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.REFEREE_NAME);
				int refereeMobileIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.REFEREE_MOBILE);
				int faceIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.FACE);
				int registerIPIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.REGISTER_IP);
				int lastSendEmailIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LAST_SEND_EMAIL);
				int lastLatIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LAST_LAT);
				int lastLngIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LAST_LNG);
				int lastLocIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LAST_LOCATION);
				int loginCountIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LOGIN_COUNT);
				int lastLoginTimeIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LASTLOGIN_TIME);
				int loginIPIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.LOGIN_IP);
				int pinCodeIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.PIN_CODE);
				int statusIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.STATUS);
				int remarkIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.REMARK);
				int createTimeIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.CREATETIME);
				int isLoginIndex = cursor
						.getColumnIndex(CustomeTables.UserInfoTask_Table.ISLOGIN);

				temp.setLEVEL_ID(cursor.getInt(levelIDIndex));
				temp.setTYPE_ID(cursor.getLong(typeIDIndex));
				temp.setMemberType(cursor.getLong(memberTypeIndex));
				temp.setUuId(cursor.getString(uuIdIndex));
				temp.setUSER_NAME(cursor.getString(userNameIndex));
				temp.setEMAIL(cursor.getString(emailIndex));
				temp.setIS_EMAIL_PROVEN(cursor.getString(isEmailProvenIndex));
				temp.setMOBILE(cursor.getString(moblieIndex));
				temp.setPASSWORD(cursor.getString(passwordIndex));
				temp.setREGISTER_TIME(cursor.getString(registerTimeIndex));
				temp.setNICKNAME(cursor.getString(nickNameIndex));
				temp.setSEX(cursor.getString(sexIndex));
				temp.setBIRTHDAY(cursor.getString(birthdayIndex));
				temp.setPROVINCE(cursor.getString(provinceIndex));
				temp.setCITY(cursor.getString(cityIndex));
				temp.setREGION(cursor.getString(regionIndex));
				temp.setADDRESS(cursor.getString(addressIndex));
				temp.setZIP(cursor.getString(zipIndex));
				temp.setTEL(cursor.getString(telIndex));
				temp.setQQ(cursor.getString(qqIndex));
				temp.setWECHAT(cursor.getString(wechatIndex));
				temp.setINFO_COMPL(cursor.getString(infoComplIndex));
				temp.setREFEREE_NAME(cursor.getString(refereeNameIndex));
				temp.setREFEREE_MOBILE(cursor.getString(refereeMobileIndex));
				temp.setFACE(cursor.getString(faceIndex));
				temp.setREGISTER_IP(cursor.getString(registerIPIndex));
				temp.setLAST_SEND_EMAIL(cursor.getString(lastSendEmailIndex));
				temp.setLAST_LAT(cursor.getString(lastLatIndex));
				temp.setLAST_LNG(cursor.getString(lastLngIndex));
				temp.setLAST_LOCATION(cursor.getString(lastLocIndex));
				temp.setLOGIN_COUNT(cursor.getString(loginCountIndex));
				temp.setLASTLOGIN_TIME(cursor.getString(lastLoginTimeIndex));
				temp.setLOGIN_IP(cursor.getString(loginIPIndex));
				temp.setPIN_CODE(cursor.getString(pinCodeIndex));
				temp.setSTATUS(cursor.getString(statusIndex));
				temp.setREMARK(cursor.getString(remarkIndex));
				temp.setCREATETIME(cursor.getString(createTimeIndex));
				temp.setIsLogin(cursor.getString(isLoginIndex));
				return temp;
			}
			return null;
		}

		/**
		 * 从对象转化为键值对，方便写入数据库
		 * 
		 * @param contacts
		 * @return
		 */
		public static ContentValues packetContentValue(UserInfo task) {
			ContentValues content = new ContentValues();

			content.put(CustomeTables.UserInfoTask_Table.TYPE_ID,
					task.getTYPE_ID());
			content.put(CustomeTables.UserInfoTask_Table.MEMBER_TYPE,
					task.getMemberType());
			content.put(CustomeTables.UserInfoTask_Table.LEVEL_ID,
					task.getLEVEL_ID());
			content.put(CustomeTables.UserInfoTask_Table.UUID, task.getUuId());
			content.put(CustomeTables.UserInfoTask_Table.USER_NAME,
					task.getUSER_NAME());
			content.put(CustomeTables.UserInfoTask_Table.EMAIL, task.getEMAIL());
			content.put(CustomeTables.UserInfoTask_Table.IS_EMAIL_PROVEN,
					task.getIS_EMAIL_PROVEN());
			content.put(CustomeTables.UserInfoTask_Table.MOBILE,
					task.getMOBILE());
			content.put(CustomeTables.UserInfoTask_Table.PASSWORD,
					task.getPASSWORD());
			content.put(CustomeTables.UserInfoTask_Table.REGISTER_TIME,
					task.getREGISTER_TIME());
			content.put(CustomeTables.UserInfoTask_Table.NICKNAME,
					task.getNICKNAME());
			content.put(CustomeTables.UserInfoTask_Table.SEX, task.getSEX());
			content.put(CustomeTables.UserInfoTask_Table.BIRTHDAY,
					task.getBIRTHDAY());
			content.put(CustomeTables.UserInfoTask_Table.PROVINCE,
					task.getPROVINCE());
			content.put(CustomeTables.UserInfoTask_Table.CITY, task.getCITY());
			content.put(CustomeTables.UserInfoTask_Table.REGION,
					task.getREGION());
			content.put(CustomeTables.UserInfoTask_Table.ADDRESS,
					task.getADDRESS());
			content.put(CustomeTables.UserInfoTask_Table.ZIP, task.getZIP());
			content.put(CustomeTables.UserInfoTask_Table.TEL, task.getTEL());
			content.put(CustomeTables.UserInfoTask_Table.QQ, task.getQQ());
			content.put(CustomeTables.UserInfoTask_Table.WECHAT,
					task.getWECHAT());
			content.put(CustomeTables.UserInfoTask_Table.INFO_COMPL,
					task.getINFO_COMPL());
			content.put(CustomeTables.UserInfoTask_Table.REFEREE_NAME,
					task.getREFEREE_NAME());
			content.put(CustomeTables.UserInfoTask_Table.REFEREE_MOBILE,
					task.getREFEREE_MOBILE());
			content.put(CustomeTables.UserInfoTask_Table.FACE, task.getFACE());
			content.put(CustomeTables.UserInfoTask_Table.REGISTER_IP,
					task.getREGISTER_IP());
			content.put(CustomeTables.UserInfoTask_Table.LAST_SEND_EMAIL,
					task.getLAST_SEND_EMAIL());
			content.put(CustomeTables.UserInfoTask_Table.LAST_LAT,
					task.getLAST_LAT());
			content.put(CustomeTables.UserInfoTask_Table.LAST_LNG,
					task.getLAST_LNG());
			content.put(CustomeTables.UserInfoTask_Table.LAST_LOCATION,
					task.getLAST_LOCATION());
			content.put(CustomeTables.UserInfoTask_Table.LOGIN_COUNT,
					task.getLOGIN_COUNT());
			content.put(CustomeTables.UserInfoTask_Table.LASTLOGIN_TIME,
					task.getLASTLOGIN_TIME());
			content.put(CustomeTables.UserInfoTask_Table.LOGIN_IP,
					task.getLOGIN_IP());
			content.put(CustomeTables.UserInfoTask_Table.PIN_CODE,
					task.getPIN_CODE());
			content.put(CustomeTables.UserInfoTask_Table.STATUS,
					task.getSTATUS());
			content.put(CustomeTables.UserInfoTask_Table.REMARK,
					task.getREMARK());
			content.put(CustomeTables.UserInfoTask_Table.ISLOGIN,
					task.getIsLogin());

			return content;
		}
	}

	public static final class AccountTask_Table implements BaseColumns {

		/**
		 * This class cannot be instantiated
		 */
		private AccountTask_Table() {
		}

		/**
		 * The content:// style URL for this table
		 */
		public static final Uri ACCOUNT_CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/account_table");

		/**
		 * The default sort order for this table
		 */
		public static final String DEFAULT_SORT_ORDER = " createTime ASC";

		public static final String CREATETIME = "CREATETIME";

		public static final String ACCOUNT_ID = "ACCOUNT_ID";

		/**
		 * 账号类型
		 */
		public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";

		/**
		 * 账号类型
		 */
		public static final String ACCOUNT_NAME = "ACCOUNT_NAME";

		/**
		 * 账号号码
		 */
		public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";

		/**
		 * 是否默认账号
		 */
		public static final String DEFAULT_ACCOUNT = "DEFAULT_ACCOUNT";

		/**
		 * 账号全部名称
		 */
		public static final String SHOW_NAME = "SHOW_NAME";

		/**
		 * 从Cursor中提取一个对象实例
		 * 
		 * @param cursor
		 * @return
		 */
		public static AccountDto packetAccountInfoFormCursor(Cursor cursor) {
			AccountDto temp = new AccountDto();
			if (cursor != null) {
				int accountID = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.ACCOUNT_ID);
				int createTime = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.CREATETIME);
				int accountType = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.ACCOUNT_TYPE);
				int accountName = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.ACCOUNT_NAME);
				int accountNumber = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.ACCOUNT_NUMBER);
				int defaultAccount = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.DEFAULT_ACCOUNT);
				int showName = cursor
						.getColumnIndex(CustomeTables.AccountTask_Table.SHOW_NAME);

				temp.setAccountID(cursor.getString(accountID));
				temp.setCreateTime(cursor.getString(createTime));
				temp.setAccType(cursor.getString(accountType));
				temp.setName(cursor.getString(accountName));
				temp.setAccountNum(cursor.getString(accountNumber));
				temp.setIsDefault(cursor.getString(defaultAccount));
				temp.setShowName(cursor.getString(showName));
				return temp;
			}
			return null;
		}

		/**
		 * 从对象转化为键值对，方便写入数据库
		 * 
		 * @param contacts
		 * @return
		 */
		public static ContentValues packetContentValue(AccountDto task) {
			ContentValues content = new ContentValues();
			content.put(CustomeTables.AccountTask_Table.ACCOUNT_ID,
					task.getId());
			content.put(CustomeTables.AccountTask_Table.CREATETIME,
					task.getCreateTime());
			content.put(CustomeTables.AccountTask_Table.ACCOUNT_TYPE,
					task.getAccType());
			content.put(CustomeTables.AccountTask_Table.ACCOUNT_NAME,
					task.getName());
			content.put(CustomeTables.AccountTask_Table.ACCOUNT_NUMBER,
					task.getAccountNum());
			content.put(CustomeTables.AccountTask_Table.DEFAULT_ACCOUNT,
					task.getIsDefault());
			content.put(CustomeTables.AccountTask_Table.SHOW_NAME,
					task.getShowName());

			return content;
		}

	}

}
