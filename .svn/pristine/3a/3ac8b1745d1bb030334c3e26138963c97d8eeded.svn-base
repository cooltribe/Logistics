package com.seeyuan.logistics.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.seeyuan.logistics.application.ConstantPool;
import com.seeyuan.logistics.application.NetWork;
import com.seeyuan.logistics.customview.UpdateAppAlertDlialog;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.datahandler.CheckUpdateHandler;
import com.seeyuan.logistics.entity.PdaRequest;
import com.seeyuan.logistics.entity.PdaResponse;
import com.seeyuan.logistics.entity.PdaVersionInfoDto;
import com.seeyuan.logistics.jsonparser.CheckUpdateJsonParser;
import com.seeyuan.logistics.util.CommonUtils;

public class CheckUpdateService extends Service implements
		OnDataReceiveListener {

	private Context context;

	/**
	 * 强制更新
	 */
	private final int FORCED_UPDATING = 100;
	/**
	 * 选择更新
	 */
	private final int SELECTIVE_UPDATING = 101;

	private final int DOWNLOAD = 102;

	private final int DOWNLOAD_FINISH = 103;

	private String apkPath;

	private PdaVersionInfoDto versionData;

	private int progress;

	private boolean cancelUpdate = false;

	private UpdateAppAlertDlialog dialog;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		apkPath = ConstantPool.DEFAULT_DOWNLOAD_PATH + "Logistics.apk";
		CheckUpdate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 检测更新
	 */
	private void CheckUpdate() {
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		CheckUpdateHandler dataHandler = new CheckUpdateHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case FORCED_UPDATING:
				doForcedUpdating();
				break;
			case SELECTIVE_UPDATING:
				doSelectiveUpdating();
				break;
			case DOWNLOAD:
				dialog.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				dialog.dismiss();
				CommonUtils.installSoftwareByAPK(context, apkPath);
				CommonUtils.finishAllActivity();
				stopSelf();
				break;

			default:
				break;
			}
		}

	};

	/**
	 * 强制更新
	 */
	private void doForcedUpdating() {
		dialog = new UpdateAppAlertDlialog(context);
		dialog.setTitleContent("检测到更新");
		dialog.setUpdateContent("有新版本，请更新");
		dialog.setCancelButtonVisible(false);
		dialog.setEnsureButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 下载文件
				downloadApk();
				dialog.setButtonEnable(false);
			}
		});
		// 强制更新没有返回键
		dialog.setCancelButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelUpdate = true;
				dialog.dismiss();

			}
		});
		dialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getRepeatCount() == 0) {
					cancelUpdate = true;
					dialog.dismiss();
					CommonUtils.finishAllActivity();
					stopSelf();
				}
				return false;
			}
		});
	};

	/**
	 * 选择更新
	 */
	private void doSelectiveUpdating() {
		dialog = new UpdateAppAlertDlialog(context);
		dialog.setTitleContent("检测到更新");
		dialog.setUpdateContent("有新版本，是否更新");
		dialog.setEnsureButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 下载文件
				downloadApk();
				dialog.setButtonEnable(false);
			}
		});
		dialog.setCancelButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelUpdate = true;
				dialog.dismiss();
				stopSelf();
			}
		});
		dialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getRepeatCount() == 0) {
					cancelUpdate = true;
					dialog.dismiss();
					CommonUtils.finishAllActivity();
					stopSelf();
				}
				return false;
			}
		});
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.CHECK_UPDATE_OK:
			doCheckUpdateSuccess(data);
			break;
		case NetWork.CHECK_UPDATE_ERROR:

			break;

		default:
			break;
		}
	}

	/**
	 * 检测更新成功
	 * 
	 * @param data
	 */
	private void doCheckUpdateSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
			Log.i("gengxin", dataString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<PdaVersionInfoDto> response = CheckUpdateJsonParser
					.parserCheckUpdateJson(dataString);
			if (response.isSuccess()) {
				versionData = response.getData();
				if (TextUtils.isEmpty(versionData.getVersion()))
					return;
				if (CommonUtils.checkAppUpdate(context,
						versionData.getVersion(), "com.seeyuan.logistics")) {
					if (versionData.getIsUpgrade().equalsIgnoreCase("1")) {//
						// 强制更新
						Message msg = myHandler.obtainMessage();
						msg.what = FORCED_UPDATING;
						msg.obj = versionData;
						myHandler.sendMessage(msg);
					} else {// 选择更新
						Message msg = myHandler.obtainMessage();
						msg.what = SELECTIVE_UPDATING;
						msg.obj = versionData;
						myHandler.sendMessage(msg);
					}
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 下载APK文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new DownloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * 
	 * @author Administrator
	 * 
	 */
	private class DownloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					URL url = new URL(versionData.getUrl());
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(ConstantPool.DEFAULT_DOWNLOAD_PATH);
					// 如果文件不存在，新建目录
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(ConstantPool.DEFAULT_DOWNLOAD_PATH,
							"Logistics.apk");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条的位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						myHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							myHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			// mDownloadDialog.dismiss();
		}
	}
}
