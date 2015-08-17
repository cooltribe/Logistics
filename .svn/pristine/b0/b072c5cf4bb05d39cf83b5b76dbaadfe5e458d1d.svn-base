package com.seeyuan.logistics.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.seeyuan.logistics.R;
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
import com.seeyuan.logistics.util.ToastUtil;

/**
 * 关于
 * 
 * @author zhazhaobao
 * 
 */
public class AboutActivity extends BaseActivity implements OnClickListener ,OnDataReceiveListener{

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	/**
	 * 版本
	 */
	private TextView version;

	/**
	 * 发布时间
	 */
	private TextView tv_publishTime;
	
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_about_us);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.About_Title);

		version = (TextView) findViewById(R.id.version);
		version.setText(CommonUtils.getVersion(context));

		tv_publishTime = (TextView) findViewById(R.id.tv_publishTime);
		tv_publishTime.setText("");// 发布时间待和服务器交互，获取app发布信息，保存
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.Access_But:
			doEvalute();
			break;
		case R.id.bt_product_help:
			doHelp();
			break;
		case R.id.function_update:
			doFunction();
			break;
		case R.id.comment_feedback:
			doFeedback();
			break;
		case R.id.system_notif:
			doAgreement();
			break;
		case R.id.test_update:
			doCheckUpdate();
			break;

		default:
			break;
		}
	}

	/**
	 * 检测更新
	 */
	private void doCheckUpdate() {
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		CheckUpdateHandler dataHandler = new CheckUpdateHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 协议和声明
	 */
	private void doAgreement() {
		Intent intent = new Intent(AboutActivity.this,
				AgreementAndStatementActivity.class);
		startActivity(intent);
	}

	/**
	 * 意见反馈
	 */
	private void doFeedback() {
		Intent intent = new Intent(AboutActivity.this,
				CommentsAndFeedbackActivity.class);
		startActivity(intent);
	}

	/**
	 * 功能介绍
	 */
	private void doFunction() {
		Intent intent = new Intent(AboutActivity.this,
				FunctionIntroductionActivity.class);
		startActivity(intent);
	}

	/**
	 * 产品帮助
	 */
	private void doHelp() {
		Intent intent = new Intent(AboutActivity.this,
				ProductHelpActivity.class);
		startActivity(intent);
	}

	/**
	 * 去评价
	 */
	private void doEvalute() {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(AboutActivity.this);
			break;

		default:
			break;
		}
	}

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
				}
				return false;
			}
		});
	};
	/**
	 * 检测更新成功
	 * 
	 * @param data
	 */
	private void doCheckUpdateSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
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
				ToastUtil.show(context, "当前为最新版本,无需更新");
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
