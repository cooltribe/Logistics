/**
 * 
 */
package com.seeyuan.logistics.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.R.color;
import com.seeyuan.logistics.application.ApplicationPool;
import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.customview.SingleSelectAlertDlialog;
import com.seeyuan.logistics.entity.CarLengthInfo;

/**
 * CommonUtils.java
 * 
 * @author zhazhaobao
 */
public class CommonUtils {

	public static String PATH = Environment.getExternalStorageDirectory()
			+ "/data/com.seeyuan.logistics/";

	public static CommonUtils commonUtils = null;
	/**
	 * 时间戳
	 */
	public String timestampStr = String.valueOf(System.currentTimeMillis());

	public static List<Activity> mActivityList = new ArrayList<Activity>();

	public CommonUtils() {
	}

	public static CommonUtils getInstance() {

		if (null == commonUtils) {
			commonUtils = new CommonUtils();
		}
		return commonUtils;

	}

	/**
	 * 添加activity至列表，集中管理
	 * 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		if (null != activity) {
			mActivityList.add(activity);
		}
	}

	/**
	 * 删除所以activity，
	 * 
	 * @param excludeActivity
	 *            ，可保留的activity，不保存设置为null
	 */
	public static void finishAllActivity(Activity excludeActivity) {
		if (mActivityList.size() > 0) {
			if (excludeActivity != null) {
				mActivityList.remove(excludeActivity);
			}
			Iterator<Activity> it = mActivityList.iterator();
			while (it.hasNext()) {
				Activity activity = it.next();
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}

	/**
	 * 删除所以activity
	 */
	public static void finishAllActivity() {
		if (mActivityList.size() > 0) {
			Iterator<Activity> it = mActivityList.iterator();
			while (it.hasNext()) {
				Activity activity = it.next();
				if (!activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}

	/**
	 * 获取系统服务手机号
	 * 
	 * @param context
	 * @return
	 */
	public static String getServicePhoneNum(Context context) {
		String service_phone = Settings.System.getString(
				context.getContentResolver(), "phone_service_number");
		return service_phone;
	}

	/**
	 * 跳转到系统设置界面设置手机号
	 * 
	 * @param context
	 */
	public static void jumpToSystemSetting(Context context) {
		Intent intent = new Intent("acton.settings.personal.ServicePhone");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 判断是否能够取到imsi号，判断是否有sim卡
	 * 
	 * @return
	 */
	public static boolean isHaveImsi(Context c) {

		String imsi = null;
		TelephonyManager tm = null;
		try {
			tm = (TelephonyManager) c
					.getSystemService(Context.TELEPHONY_SERVICE);

			if (tm != null) {
				imsi = tm.getSubscriberId();

				int simState = tm.getSimState();

				switch (simState) {

				case TelephonyManager.SIM_STATE_ABSENT:
					// mString = "无卡";
					// do something
					imsi = null;
					break;

				case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
					// mString = "需要NetworkPIN解锁";
					// do something

					break;

				case TelephonyManager.SIM_STATE_PIN_REQUIRED:
					// mString = "需要PIN解锁";
					// do something
					break;

				case TelephonyManager.SIM_STATE_PUK_REQUIRED:
					// mString = "需要PUN解锁";
					// do something
					break;

				case TelephonyManager.SIM_STATE_READY:
					// mString = "良好";
					// do something
					break;

				case TelephonyManager.SIM_STATE_UNKNOWN:
					// mString = "未知状态";
					// do something
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (imsi == null) {
			return false;
		}
		return true;
	}

	/**
	 * 根据资源id得到对应的String
	 * 
	 * @param context
	 * @param resid
	 *            getStringByRes(R.string.hello);
	 * @return
	 */
	public static String getStringByRes(Context context, int resis) {
		return context.getResources().getString(resis);
	}

	/**
	 * 
	 * @Title: getSign
	 * @Description: TODO 得到有时间戳的签名
	 * @param @param userid
	 * @param @param tokenStr
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getSignWithTime(String userid, String tokenStr) {
		// 时间戳
		timestampStr = String.valueOf(System.currentTimeMillis());
		// token加密
		String tokenString = getMD5Str(tokenStr, 0, 32);
		return getMD5Str(userid + tokenString + timestampStr, 0, 32);
	}

	/**
	 * 
	 * @Title: getSign
	 * @Description: TODO得到没有时间戳的签名算法一
	 * @param @param userid
	 * @param @param tokenStr
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getSignNOTime(String userid, String tokenStr) {
		// 时间戳
		// token加密
		// String tokenString = getMD5Str(tokenStr, 0, 32);
		return getMD5Str(userid + tokenStr, 0, 32);
	}

	/**
	 * 
	 * @Title: getMD5Str
	 * @Description: TODO MD5加密
	 * @param @param str
	 * @param @param offset
	 * @param @param len
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getMD5Str(String str, int offset, int len) {
		MessageDigest messageDigest = null;
		if (str == null)
			return "";

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.substring(offset, len).toString();// .toUpperCase();
	}

	/**
	 * 检测有没有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetwork(Context context) {
		ConnectivityManager netManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = netManager.getActiveNetworkInfo();
		if (info != null)
			return info.isAvailable();
		else
			return false;
	}

	/**
	 * 获取存本地文件名方法
	 * 
	 * @param appName
	 * @param tail
	 *            文件名后缀 如“.mp3”
	 * @param flag
	 *            1图片 2 语音 3崩溃 默认是0
	 * @return
	 */
	public static String getLocalFileSavePath(String appName, String tail,
			int flag) {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (!ExistSDCard()) {
			// path = Environment.getDataDirectory().getAbsolutePath() + "/";
			path = "/data/data/com.seeyuan.logistics/files/";
		} else {
			path += "/data/com/seetyuan/logistics";
		}
		if (tail.equals("jpb") || tail.endsWith("png")) {
			flag = 1;
		} else if (tail.equals("amr") || tail.endsWith("mp3")) {
			flag = 2;
		} else if (tail.equals("txt")) {
			flag = 0;
		}

		long name = System.currentTimeMillis();
		if (flag == 2) {
			path += appName + "/record";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/record-" + name + tail;
		} else if (flag == 1) {
			path += appName + "/image";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/img-" + name + tail;
		} else if (flag == 3) {
			path += appName + "/crush";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/crush-" + name + tail;
		} else {
			path += appName + "/temp";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/temp-" + name + tail;
		}
		return path;
	}

	/**
	 * 获取下载文件默认保存路径
	 * 
	 * @return
	 */
	public static String getDownloadFileSavePath() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (!ExistSDCard()) {
			path = "/data/data/com.seeyuan.logistics/files/download/";
		} else {
			path += "/data/com/seeyuan/logistics/download/";
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	public static boolean ExistSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/**
	 * 获取文件默认保存路径
	 * 
	 * @return
	 */
	public static String getFileSavePath(String folderName) {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (TextUtils.isEmpty(path)) {
			path = Environment.getDataDirectory().getAbsolutePath() + "/";
		} else {
			path += "/data/com/seeyuan/logistics/";
		}
		path += folderName + "/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	/**
	 * 卸载程序
	 * 
	 * @param name
	 */
	public static void uninstallSoftware(Context context, String packageName) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(uninstallIntent);
	}

	/**
	 * 检测app应用是否安装
	 * 
	 * @param packagename
	 * @return
	 */
	public static boolean checkAppInstalled(Context context, String packetName) {
		boolean isInstalled = false;
		if (TextUtils.isEmpty(packetName))
			return isInstalled;
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packetName, 0);

		} catch (Exception e) {
			packageInfo = null;
			// e.printStackTrace();
		}
		if (packageInfo == null) {
			isInstalled = false;
		} else {
			isInstalled = true;
		}

		return isInstalled;
	}

	public static boolean isSystemApp(Context context, String packageName) {
		String ppt_install_path = "";
		try {
			ppt_install_path = context.getPackageManager().getApplicationInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES).sourceDir;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// LogTag.e("isSystemApp", ppt_install_path);
		if (ppt_install_path.startsWith("/system/")) {
			return true;
		}

		return false;

	}

	public static void openApp(Context context, String packetName) {

		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent();
		intent = packageManager.getLaunchIntentForPackage(packetName);
		if (intent == null) {
			LogTag.e("TAG", "App not found!");
		} else {
			context.startActivity(intent);
		}

		// Intent in = new Intent();
		// // packageInfo = context.getPackageManager().getPackageInfo(
		// // appInfo.getPackageName(), 0);
		// // packageInfo.activities[0].name.toString();
		// ComponentName comp = new ComponentName(packetName,
		// "Activity的名字，要完整的");
		// in.setComponent(comp);
		// in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(in);
	}

	public static String getMKBSizeFromBt(String packageSize) {
		String mbsize = "未知大小";
		if (TextUtils.isEmpty(packageSize)) {
			return mbsize;
		}
		float size = Float.valueOf(packageSize);
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (size < 1024 * 1024) {
			f = (float) ((float) size / (float) 1024);
			mbsize = (df.format(new Float(f).doubleValue()) + "KB");
		} else {
			f = (float) ((float) size / (float) (1024 * 1024));
			mbsize = (df.format(new Float(f).doubleValue()) + "MB");
		}
		return mbsize;
	}

	public static String getMKBSizeFromKB(String packageSize) {
		String mbsize = "未知大小";
		if (TextUtils.isEmpty(packageSize)) {
			return mbsize;
		}
		float size = Float.valueOf(packageSize);
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (size < 1024) {
			f = size;
			mbsize = (df.format(new Float(f).doubleValue()) + "KB");
		} else {
			f = (float) ((float) size / (float) (1024));
			mbsize = (df.format(new Float(f).doubleValue()) + "MB");
		}
		return mbsize;
	}

	public static String getBSizeFromKB(String packageSize) {
		String bsize = "未知大小";
		if (TextUtils.isEmpty(packageSize)) {
			return bsize;
		}
		float size = Float.valueOf(packageSize);
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		f = (float) ((float) size * (float) (1024));
		bsize = (df.format(new Float(f).doubleValue()));
		return bsize;
	}

	/**
	 * 安装软件
	 * 
	 * @param name
	 */
	public static void inistallSoftware(Context context, String packageName) {
		String fileName = Environment.getExternalStorageDirectory() + "/"
				+ packageName;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(fileName)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 根据本地apk包安装应用
	 * 
	 * @param filePath
	 */
	public static void installSoftwareByAPK(Context context, String filePath) {
		Uri uri = Uri.fromFile(new File(filePath));
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		installIntent.setDataAndType(uri,
				"application/vnd.android.package-archive");
		context.startActivity(installIntent);
	}

	/**
	 * 获取文件名
	 * 
	 * @param conn
	 * @return
	 */
	public static String getFileName(String urlStr) {
		String filename = urlStr.substring(urlStr.lastIndexOf('/') + 1);

		if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称

			filename = UUID.randomUUID() + ".apk";// 默认取一个文件名
		}

		return filename;
	}

	/**
	 * sd卡是否可下载
	 * 
	 * @param packageSize
	 *            文件大小
	 * @return true:可下载 false:空间不足
	 */
	public static boolean isSDCardCanDowning(String packageSize) {
		long fileSize = 0;
		if (!TextUtils.isEmpty(packageSize)) {
			if (packageSize.endsWith("MB")) {
				packageSize = packageSize
						.substring(0, packageSize.length() - 2);
				fileSize = (long) Double.parseDouble(packageSize);
				fileSize = fileSize * 1024 * 1024;
			} else if (packageSize.endsWith("KB")) {
				packageSize = packageSize
						.substring(0, packageSize.length() - 2);
				fileSize = (long) Double.parseDouble(packageSize);
				fileSize = fileSize * 1024;
			}
		}
		// StrorageInfo stroageInfo = new StrorageInfo();
		// long avaiSDsize = stroageInfo.getAvailaleSDSize();
		// // sdCard空间不足
		// return (fileSize + 200) < avaiSDsize;
		return false;
	}

	/**
	 * 界面跳转
	 * 
	 * @param 当前context
	 * @param 目标界面
	 */
	public static <T> void jump2interface(Context context, Class<T> where) {
		Intent intent = new Intent(context, where);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 拨打电话
	 * 
	 * @param context
	 * @param phoneNum
	 *            电话号码
	 */
	public static void makeingCalls(Context context, String phoneNum) {
		if (TextUtils.isEmpty(phoneNum)) {
			return;
		}
		Intent phoneIntent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + phoneNum));
		phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(phoneIntent);
	}

	/**
	 * 获取本机手机号
	 */
	public static String getLocalPhoneNo(Context context) {
		String NativePhoneNumber = null;
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		NativePhoneNumber = telephonyManager.getLine1Number();
		return NativePhoneNumber;
	}

	/**
	 * 获取本机IMEI码
	 */
	public static String getLocalIMEINo(Context context) {
		String NativeIMEINumber = null;
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		NativeIMEINumber = telephonyManager.getDeviceId();
		return NativeIMEINumber;
	}

	/**
	 * 打开键盘
	 */
	public static void changeKeyboardState(Context context) {

		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
	}

	public static void openKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

	}

	// 关闭软键盘
	public static void closeKeyboard(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 判断密码格式是否正确
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isPasswordTypeCorrect(String password) {
		if (password.length() < 6 || password.length() > 12)
			return false;
		return true;

	}

	/**
	 * 判断密码是否相同
	 * 
	 * @param password
	 *            密码
	 * @param againPasswrod
	 *            再次确认密码
	 * @return
	 */
	public static boolean isPasswordCorrect(String password,
			String againPasswrod) {
		if (password.equalsIgnoreCase(againPasswrod))
			return true;
		return false;

	}

	/**
	 * 关闭activity
	 * 
	 * @param context
	 */
	public static void finishActivity(Activity context) {
		if (!context.isFinishing()) {
			context.finish();
		}
	}

	/**
	 * 加密字符串
	 * 
	 * @param content
	 *            内容
	 * @param no
	 *            加密后几位
	 */
	public static String encryptionString(String content, int no) {
		if (TextUtils.isEmpty(content))
			return "未知";
		if (no > content.length()) {
			return "未知";
		}
		content = content.substring(0, content.length() - no);
		String encryptionStr = "";
		for (int i = 0; i < no; i++) {
			encryptionStr = encryptionStr + "*";
		}
		content = content + encryptionStr;
		return content;
	}

	/**
	 * 检测SDCard是否
	 * 
	 * @param context
	 * @return
	 */
	public static boolean CheckExternStorage(Context context) {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @param type
	 *            输入日期格式 "yyyy-MM-dd"
	 * @return
	 */
	public static String getCurrentTime(String type) {
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 冒泡排序
	 * 
	 * @param array
	 * @return
	 */
	public static List<Integer> BubbleSort(List<Integer> list) {
		int length = list.size();
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = list.get(i);
		}
		for (int i = 0; i <= length - 1; i++) {
			for (int j = length - 1; j > i; j--) {
				if (array[j] < array[j - 1]) {
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
		list.clear();
		for (int i = 0; i < length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	public static void selectCameraPhone(final int cameraCode,
			String resultImgPath, Activity activity) {

		if (!CommonUtils.CheckExternStorage(activity)) {
			ToastUtil.show(activity,
					activity.getResources().getString(R.string.msg_no_sdcard));
		}
		takePhoto(cameraCode, resultImgPath, activity);
	}

	public static void selectSystemPhone(final int photoCode, Activity activity) {

		if (!CommonUtils.CheckExternStorage(activity)) {
			ToastUtil.show(activity,
					activity.getResources().getString(R.string.msg_no_sdcard));
			return;
		}
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		activity.startActivityForResult(intent, photoCode);
	}

	public static void takePhoto(final int cameraCode, String resultImgPath,
			Activity activity) {
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		Bundle bundle = new Bundle();

		String path = ConstantPool.DEFAULT_ICON_PATH;
		if (path != null) {
			// resultImgPath= "file://" + path + "image_diy_takephoto.jpg";
			Uri uri = Uri.parse(resultImgPath);
			bundle.putParcelable(MediaStore.EXTRA_OUTPUT, uri);
			intent.putExtras(bundle);
			try {
				activity.startActivityForResult(intent, cameraCode);
			} catch (Exception e) {
				ToastUtil.show(
						activity,
						activity.getResources().getString(
								R.string.msg_send_nophoto_prompt));
			}
		}
	}

	/**
	 * 
	 * 裁剪图片方法实现
	 * 
	 * 
	 * 
	 * @param uri
	 */
	public static void startPhotoZoom(Uri uri, int photoCode,
			String pathString, Activity activity) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		File tempFile = new File(pathString);
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			activity.startActivityForResult(intent, photoCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void cropPhoto(int photoCode, String headerImgPath,
			String resultPath, Activity activity) {
		// headerImgPath = "file://" + ConstantPool.ADVERT_ICON_PATH
		// + "image_diy_takephoto.jpg";
		if (headerImgPath == null)
			return;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.parse(headerImgPath), "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", 800);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		File tempFile = new File(resultPath);
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			activity.startActivityForResult(intent, photoCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 弹窗提示，选择车辆信息
	 */
	public static void selectCarInfo(Activity activity,
			final Handler myHandler, final int resultCode, int resourceID,
			int titleID) {

		List<CarLengthInfo> mDataList = new ArrayList<CarLengthInfo>();
		TypedArray typedArray = activity.getResources().obtainTypedArray(
				resourceID);
		typedArray.recycle();
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			CarLengthInfo indexInfo = new CarLengthInfo();
			indexInfo.setCar_Length(typedArray.getString(i));
			mDataList.add(indexInfo);
		}

		final SingleSelectAlertDlialog ad = new SingleSelectAlertDlialog(
				activity);
		ad.setTitle(activity.getResources().getString(titleID));
		ad.setListContentForCarLength(mDataList);
		ad.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				if (null == view)
					return;
				ad.dismiss();
				Message message = myHandler.obtainMessage();
				message.what = resultCode;
				message.obj = ((TextView) view
						.findViewById(R.id.item_car_length)).getText();
				myHandler.sendMessage(message);

			}
		});
	}

	/**
	 * 将bitmap转换为byte[]
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByByte(Bitmap bitmap) {
		if (null == bitmap)
			return null;
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// byte[] byteArray = baos.toByteArray();
		// return byteArray;
		return compressImage(bitmap);
	}

	/**
	 * 压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		// ByteArrayInputStream isBm = new
		// ByteArrayInputStream(baos.toByteArray());//
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// 把ByteArrayInputStream数据生成图片
		// return bitmap;
		byte[] byteArray = baos.toByteArray();
		return byteArray;
	}

	public static boolean is15IDcard(String idString) {
		Pattern p = Pattern
				.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		Matcher m = p.matcher(idString);
		return m.matches();
	}

	public static boolean is18IDcard(String idString) {
		Pattern p = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		Matcher m = p.matcher(idString);
		return m.matches();
	}

	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	public static boolean isVehiclePlate(String plate) {
		String telRegex = "/^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$/";
		if (TextUtils.isEmpty(plate))
			return false;
		else
			return plate.matches(telRegex);
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static String parserTimestamp(Timestamp timestamp) {
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			tsStr = sdf.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;

	}

	public static Timestamp parserTimestamp(String timestamp) {
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(timestamp + " " + "00:00:00");
			System.out.println(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	public static String parserLineType(String typeID) {
		if (TextUtils.isEmpty(typeID)) {
			return "未知";
		}
		if (typeID.equalsIgnoreCase("1")) {
			return "即时空车";
		} else if (typeID.equalsIgnoreCase("2")) {
			return "常跑线路";
		} else {
			return "未知";
		}

	}

	public static Date parserData(String data) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parserData(Date date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getUUID(Context context) {
		String uuID = null;
		uuID = ApplicationPool.getUUID();
		if (TextUtils.isEmpty(uuID)) {
			uuID = context.getSharedPreferences(
					ConstantPool.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE)
					.getString("uuId", "");
		}
		return uuID;
	}

	public static String getUserName(Context context) {
		String userName = null;
		userName = ApplicationPool.getUserName();
		if (TextUtils.isEmpty(userName)) {
			userName = context.getSharedPreferences(
					ConstantPool.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE)
					.getString("userName", "");
		}
		return userName;
	}

	public static String getMemberType(Context context) {
		String memberType = null;
		memberType = ApplicationPool.getMemberType();
		if (TextUtils.isEmpty(memberType)) {
			memberType = context.getSharedPreferences(
					ConstantPool.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE)
					.getString("memberType", "");
		}
		return memberType;

	}

	public static String getPassword(Context context) {
		String password = null;
		password = ApplicationPool.getPassword();
		if (TextUtils.isEmpty(password)) {
			password = context.getSharedPreferences(
					ConstantPool.LOGISTICS_PREFERENCES, Context.MODE_PRIVATE)
					.getString("password", "");
		}
		return password;

	}

	public static long getUserType(String type) {
		if (type.equalsIgnoreCase("货主")) {
			return 1;
		} else if (type.equalsIgnoreCase("个人车主")) {
			return 2;
		} else if (type.equalsIgnoreCase("物流企业")) {
			return 3;
		}
		return 0;
	}

	public static String getUserType(long type) {
		if (type == 1) {
			return "货主";
		} else if (type == 2) {
			return "个人车主";
		} else if (type == 3) {
			return "物流企业";
		}
		return "";
	}

	/**
	 * 对比时间
	 * 
	 * @param DATE1
	 *            当前时间
	 * @param DATE2
	 *            选择时间
	 * @return
	 */
	public static boolean compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return true;
			} else if (dt1.getTime() <= dt2.getTime()) {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static String getOrderType(int orderStatus) {
		String result = null;
		switch (orderStatus) {
		case 0:
			result = "初始";
			break;
		case 1:
			result = "待车主确认";
			break;
		case 2:
			result = "待货主确认";
			break;
		case 3:
			result = "订单确认";
			break;
		case 4:
			result = "已发货";
			break;
		case 5:
			result = "已到货";
			break;
		case 6:
			result = "已签收";
			break;
		case 7:
			result = "已上传回单";
			break;
		case 8:
			result = "回单确认(作废)";
			break;
		case 9:
			result = "订单完结";
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 检测app是否需要更新
	 * 
	 * @param context
	 * @param newVersionCode
	 * @param packageName
	 * @return
	 */
	public static boolean checkAppUpdate(Context context,
			String newVersionCode, String packageName) {
		// 获取已经安装的应用的versionCode
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					packageName, 0);
		} catch (Exception e) {
		}
		int currentVersion = Integer.parseInt(packageInfo.versionName.replace(
				".", ""));
		int newVersion = Integer.parseInt(newVersionCode.replace(".", ""));
		if (newVersion > currentVersion) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 根据不同状态区分颜色
	 * 
	 * @param type
	 * @return
	 */
	public static int getColor(Context context, String type) {
		if (type.equalsIgnoreCase("U")) {
			return context.getResources().getColor(color.orange);
		} else if (type.equalsIgnoreCase("Y")) {
			return context.getResources().getColor(color.green);
		} else if (type.equalsIgnoreCase("N")) {
			return context.getResources().getColor(color.red);
		} else {
			return context.getResources().getColor(color.red);
		}
	}

	public static String getTypeTitle(String type) {

		if (type.equalsIgnoreCase("U")) {
			return "未认证";
		} else if (type.equalsIgnoreCase("Y")) {
			return "认证通过";
		} else if (type.equalsIgnoreCase("N")) {
			return "认证未通过";
		} else {
			return "认证未通过";
		}

	}

	public static boolean getCheckBoxType(String type) {

		if (type.equalsIgnoreCase("Y")) {
			return true;
		} else if (type.equalsIgnoreCase("N")) {
			return false;
		} else {
			return false;
		}
	}

	public static boolean getCheckBoxTypeOpposite(String type) {

		if (type.equalsIgnoreCase("Y")) {
			return false;
		} else if (type.equalsIgnoreCase("N")) {
			return true;
		} else {
			return false;
		}
	}

	public static String getCheckBoxType(boolean type) {

		if (type) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 转换 结算单状态
	 * 
	 * @param type
	 * @return
	 */
	public static String getSettlementType(String type) {
		if (type.equalsIgnoreCase("0")) {
			return "已生成";
		} else if (type.equalsIgnoreCase("1")) {
			return "正在结算";
		} else if (type.equalsIgnoreCase("2")) {
			return "完成结算";
		} else if (type.equalsIgnoreCase("3")) {
			return "已审核";
		} else {
			return "已生成";
		}
	}

	public static String getBankID(String bankName) {
		if (bankName.equalsIgnoreCase("中国工商银行")) {
			return "10001";
		} else if (bankName.equalsIgnoreCase("中国银行")) {
			return "10002";
		} else if (bankName.equalsIgnoreCase("中国建设银行")) {
			return "10003";
		} else if (bankName.equalsIgnoreCase("支付宝")) {
			return "20001";
		} else if (bankName.equalsIgnoreCase("财付通")) {
			return "20002";
		} else {
			return "";
		}
	}

	public static String getBankName(String bankID) {
		if (TextUtils.isEmpty(bankID))
			return "";
		if (bankID.equalsIgnoreCase("10001")) {
			return "中国工商银行";
		} else if (bankID.equalsIgnoreCase("10002")) {
			return "中国银行";
		} else if (bankID.equalsIgnoreCase("10003")) {
			return "中国建设银行";
		} else if (bankID.equalsIgnoreCase("20001")) {
			return "支付宝";
		} else if (bankID.equalsIgnoreCase("20002")) {
			return "财付通";
		} else {
			return "";
		}
	}

	public static String getLineTypeID(String lineType) {
		if (lineType.equalsIgnoreCase("常跑路线")) {
			return "2";
		} else if (lineType.equalsIgnoreCase("即时空车")) {
			return "1";
		}
		return "0";
	}

	public static String getLineTypeTitle(String lineType) {
		if (lineType.equalsIgnoreCase("2")) {
			return "常跑路线";
		} else if (lineType.equalsIgnoreCase("1")) {
			return "即时空车";
		}
		return "未知";
	}
}
