package com.seeyuan.logistics.datacenter;

/**
 * 
 * OnDataRetrieveListener.java
 * 
 * @author 査赵保
 */
public interface OnDataReceiveListener {
	/**
	 * 数据接收回调方法
	 * 
	 * @param dataHandler
	 *            数据处理器
	 * @param resultCode
	 *            接收结果
	 * @param data
	 *            接收数据
	 * @param type上传类型
	 */
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type);
}
