package com.seeyuan.logistics.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.seeyuan.logistics.provider.CustomeTables.AccountTask_Table;
import com.seeyuan.logistics.provider.CustomeTables.UserInfoTask_Table;

/**
 * 数据管理类 ContentProvider
 * 
 * @author 査赵保
 * 
 */
public class LogisticsProvider extends ContentProvider {

	/**
	 * 数据库标示
	 */
	private static final String TAG = "LogisticsProvider";

	/**
	 * 数据库名
	 */
	private static final String DATABASE_NAME = "logitics.db";

	private static final String TABLE_USERINFO = "userinfo_table";

	private static final String TABLE_ACCOUNT = "account_table";

	/**
	 * 数据库版本 ，重大修改 版本号+1
	 */
	private static final int DATABASE_VERSION = 7;

	private static final int USERINFOTASK = 1;
	private static final int USERINFOTASK_ID = 2;

	private static final int ACCOUNTTASK = 3;
	private static final int ACCOUNTTASK_ID = 4;

	/**
	 * 创建URI匹配器
	 */
	private static final UriMatcher sUriMatcher;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * 创建数据表
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			String userInfoSQL = "create TABLE if not exists userinfo_table("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "TYPE_ID INTEGER,"//
					+ "MEMBER_TYPE varchar(32),"// 会员类型
					+ "LEVEL_ID INTEGER,"// 会员级别
					+ "UUID varchar(64),"// UUID
					+ "USER_NAME varchar(64),"// 会员用户名
					+ "EMAIL varchar(64),"// 邮件地址
					+ "IS_EMAIL_PROVEN varchar(1),"// 邮件是否验证 Y 已验证 N 未验证
					+ "MOBILE varchar(32),"// 手机
					+ "PASSWORD varchar(32),"// 密码
					+ "REGISTER_TIME timestamp,"// 注册时间
					+ "NICKNAME varchar(64),"// 昵称
					+ "SEX varchar(1),"// 性别 0女 1男
					+ "BIRTHDAY varchar,"// 出生日期
					+ "PROVINCE varchar(32),"// 省
					+ "CITY varchar(32),"// 市
					+ "REGION varchar(32),"// 区
					+ "ADDRESS varchar(64),"// 详细地址
					+ "ZIP varchar(32),"// 邮编
					+ "TEL varchar(32),"// 固定电话
					+ "QQ varchar(32),"// QQ
					+ "WECHAT varchar(32),"// 微信号
					+ "INFO_COMPL varchar(1),"// 个人资料完善程度
					+ "REFEREE_NAME varchar(64),"// 推荐人
					+ "REFEREE_MOBILE varchar(32),"// 推荐人手机号
					+ "FACE varchar(64),"// 头像URL
					+ "REGISTER_IP varchar(32),"// 注册IP
					+ "LAST_SEND_EMAIL timestamp,"// 最后一次发送激活邮件时间
					+ "LAST_LAT decimal(15,4),"// 最近位置_纬度
					+ "LAST_LNG decimal(15,4),"// 最近位置_经度
					+ "LAST_LOCATION decimal(15,4),"// 最近位置_详细地址
					+ "LOGIN_COUNT INTEGER,"// 当月登录次数
					+ "LASTLOGIN_TIME timestamp,"// 最后登录时间
					+ "LOGIN_IP varchar(32),"// 最近登录IP
					+ "PIN_CODE varchar(32),"// 识别号
					+ "STATUS varchar(1),"// 账号状态:关联数据字典，TYPE_CODE='MEMBER_STATUS'
					+ "ISLOGIN varchar(1),"// 是否登录 Y/N
					+ "CREATETIME varchar(32),"// 创建时间
					+ "REMARK TEXT);"// 备注
			;
			String accountSQL = "create TABLE if not exists account_table("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "CREATETIME varchar(32),"// 创建时间
					+ "ACCOUNT_ID varchar(32),"// 唯一标示符
					+ "ACCOUNT_TYPE varchar(32),"// 账号类型
					+ "ACCOUNT_NAME varchar(32),"// 账号名称
					+ "DEFAULT_ACCOUNT varchar(32),"// 是否默认账号
					+ "SHOW_NAME varchar(32),"// 账号全部名称
					+ "ACCOUNT_NUMBER varchar(32));"// 账号号码
			;
			db.execSQL(userInfoSQL);
			db.execSQL(accountSQL);
		}

		/**
		 * 如果数据表存在，删除原数据表并创建新表.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS userinfo_table");
			db.execSQL("DROP TABLE IF EXISTS account_table");
			if (newVersion > oldVersion) {
				if (oldVersion < 1) {
					// doSometing
				}
			}
			onCreate(db);
		}
	}

	/**
	 * 对数据表CustomContacts的删除操作
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case USERINFOTASK:
			count = db.delete(TABLE_USERINFO, where, whereArgs);
			break;
		case ACCOUNTTASK:
			count = db.delete(TABLE_ACCOUNT, where, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	/**
	 * 对数据表CUSTOM_CONTACTS进行插入操作
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Validate the requested uri
		switch (sUriMatcher.match(uri)) {
		case USERINFOTASK:
			return inUserInfoBox(uri, initialValues);
		case ACCOUNTTASK:
			return inAccountBox(uri, initialValues);

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	/**
	 * 对用户信息数据表进行插入操作
	 */
	private Uri inUserInfoBox(Uri uri, ContentValues initialValues) {

		if (sUriMatcher.match(uri) != USERINFOTASK) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (initialValues == null) {
			return null;
		}

		ContentValues values = new ContentValues(initialValues);

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_USERINFO, "_id", values);

		if (rowId > 0) {
			Uri contactsBoxUri = ContentUris.withAppendedId(
					UserInfoTask_Table.USERINFO_CONTENT_URI, rowId);
			getContext().getContentResolver()
					.notifyChange(contactsBoxUri, null);
			return contactsBoxUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	/**
	 * 对账号数据表进行插入操作
	 */
	private Uri inAccountBox(Uri uri, ContentValues initialValues) {

		if (sUriMatcher.match(uri) != ACCOUNTTASK) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (initialValues == null) {
			return null;
		}

		ContentValues values = new ContentValues(initialValues);

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert(TABLE_ACCOUNT, "_id", values);

		if (rowId > 0) {
			Uri contactsBoxUri = ContentUris.withAppendedId(
					AccountTask_Table.ACCOUNT_CONTENT_URI, rowId);
			getContext().getContentResolver()
					.notifyChange(contactsBoxUri, null);
			return contactsBoxUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	private DatabaseHelper mOpenHelper;
	private SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		db = mOpenHelper.getWritableDatabase();
		return false;
	}

	/**
	 * 对数据表customcontacts的查询操作
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case USERINFOTASK:
			qb.setTables(TABLE_USERINFO);
			break;
		case ACCOUNTTASK:
			qb.setTables(TABLE_ACCOUNT);
			break;

		default:
			break;
		}

		String orderBy = " _id ASC";
		switch (sUriMatcher.match(uri)) {
		case USERINFOTASK:
			orderBy = " _id ASC";
			break;
		case ACCOUNTTASK:
			orderBy = " _id ASC";
			break;

		default:
			break;
		}
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, orderBy);

		// Tell the w what uri to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	/**
	 * 对数据表的更新操作.
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		if (db == null) {
			try {
				db = mOpenHelper.getWritableDatabase();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		int count;
		switch (sUriMatcher.match(uri)) {
		case USERINFOTASK:
			count = db.update(TABLE_USERINFO, values, where, whereArgs);
			break;
		case ACCOUNTTASK:
			count = db.update(TABLE_ACCOUNT, values, where, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		// -- URI匹配器和相应的数据表相匹配.
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(CustomeTables.AUTHORITY, TABLE_USERINFO,
				USERINFOTASK);
		sUriMatcher.addURI(CustomeTables.AUTHORITY, TABLE_USERINFO + "/#",
				USERINFOTASK_ID);

		sUriMatcher.addURI(CustomeTables.AUTHORITY, TABLE_ACCOUNT, ACCOUNTTASK);
		sUriMatcher.addURI(CustomeTables.AUTHORITY, TABLE_ACCOUNT + "/#",
				ACCOUNTTASK_ID);
	}
}
