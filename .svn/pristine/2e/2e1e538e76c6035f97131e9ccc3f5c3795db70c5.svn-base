package com.seeyuan.logistics.datacenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.seeyuan.logistics.net.http.HttpAction;
import com.seeyuan.logistics.net.http.HttpManager;
import com.seeyuan.logistics.net.http.HttpObserver;

/**
 * 
 * DataHandler.java
 * 
 * @author 査赵保
 */
public abstract class DataHandler implements Runnable {
	private static final String TAG = "DataHandler";

	public static final int OK = 0;
	public static final int ERROR = 1;

	private OnDataReceiveListener onDataReceiveListener;
	private DataUIHandler dataUIHandler;
	private DataHttpObserver dataHttpObserver;

	public DataHandler() {
		dataUIHandler = new DataUIHandler();
		dataHttpObserver = new DataHttpObserver();
	}

	/**
	 * 开始数据处理
	 */
	public final void start() {
		Processor.getInstance().put(this);
	}

	/**
	 * 开始联网
	 * 
	 * @param httpAction
	 *            Http事件
	 */
	protected final void startNetwork(HttpAction httpAction) {
		httpAction.setHttpObserver(dataHttpObserver);
		Log.i("httpAction.getReceiveBody().toString()", httpAction.toString());
		HttpManager.getInstance().add(httpAction);
	}

	/**
	 * 联网成功回调
	 * 
	 * @param receiveBody
	 *            联网获取的数据
	 */
	protected void onNetReceiveOk(byte[] receiveBody) {

	}

	/**
	 * 联网失败回调
	 */
	protected void onNetReceiveError(int errorCode) {

	}

	/**
	 * 联网超时回调
	 */
	protected void onNetReceiveTimeout(int errorCode) {

	}

	private class DataHttpObserver implements HttpObserver {
		@Override
		public void httpResultOK(HttpAction httpAction) {
			byte[] data = (byte[]) httpAction.getReceiveBody();
			onNetReceiveOk(data);
		}

		@Override
		public void httpResultError(HttpAction httpAction) {
			int errorcode = httpAction.getErrorCode();
			onNetReceiveError(errorcode);
		}

		@Override
		public void httpResultTimeOut(HttpAction httpAction) {
			int errorcode = httpAction.getErrorCode();
			onNetReceiveTimeout(errorcode);

		}
	}

	/**
	 * 发送数据处理结果
	 * 
	 * @param resultCode
	 *            处理结果
	 * @param object
	 *            结果数据
	 * 
	 */
	protected void sendResult(int resultCode, Object object) {
		Message msg = new Message();
		msg.what = resultCode;
		msg.obj = object;
		dataUIHandler.sendMessage(msg);
	}

	/**
	 * 上传文件数据处理结果
	 * 
	 * @param resultCode
	 *            处理结果
	 * @param object
	 *            结果数据
	 * @param type
	 *            文件类型 图片 语音
	 */
	protected void sendResult(int resultCode, Object object, int type) {
		Message msg = new Message();
		msg.what = resultCode;
		msg.obj = object;
		msg.arg1 = type;
		dataUIHandler.sendMessage(msg);
	}

	private class DataUIHandler extends Handler {
		private DataUIHandler() {
			super(Looper.getMainLooper());
		}

		@Override
		public void handleMessage(Message msg) {
			if (onDataReceiveListener != null) {
				onDataReceiveListener.onDataReceive(DataHandler.this, msg.what,
						msg.obj, msg.arg1);
			}
		}
	}

	/**
	 * 获取数据接收监听器
	 * 
	 * @return
	 */
	public OnDataReceiveListener getOnDataReceiveListener() {
		return onDataReceiveListener;
	}

	/**
	 * 设置数据接收监听器
	 * 
	 * @param onDataRetrieveListener
	 *            数据接收监听器
	 */
	public void setOnDataReceiveListener(
			OnDataReceiveListener onDataReceiveListener) {
		if (onDataReceiveListener != null) {
			this.onDataReceiveListener = onDataReceiveListener;
		}
	}

	/**
	 * 该方法用于被子类重写，以达到实现处理数据过程的目的
	 */
	@Override
	public void run() {

	}
}
