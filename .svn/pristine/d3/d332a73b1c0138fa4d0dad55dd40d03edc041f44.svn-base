package com.seeyuan.logistics.receiver;

import io.yunba.android.manager.YunBaManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.seeyuan.logistics.util.YunbaUtil;

public class YunbaReceiver extends BroadcastReceiver {

	private final static String REPORT_MSG_SHOW_NOTIFICARION = "1000";
	private final static String REPORT_MSG_SHOW_NOTIFICARION_FAILED = "1001";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

			String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
			String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

			StringBuilder showMsg = new StringBuilder();
			showMsg.append("Received message from server: ")
					.append(YunBaManager.MQTT_TOPIC).append(" = ")
					.append(topic).append(" ").append(YunBaManager.MQTT_MSG)
					.append(" = ").append(msg);
			boolean flag = YunbaUtil.showNotifation(context, topic, msg);
			// 上报显示通知栏状态， 以方便后台统计
			if (flag)
				YunBaManager.report(context, REPORT_MSG_SHOW_NOTIFICARION,
						topic);
			else
				YunBaManager.report(context,
						REPORT_MSG_SHOW_NOTIFICARION_FAILED, topic);

			// send msg to app

		} else if (YunBaManager.PRESENCE_RECEIVED_ACTION.equals(intent
				.getAction())) {
			// msg from presence.
			String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
			String payload = intent.getStringExtra(YunBaManager.MQTT_MSG);
			StringBuilder showMsg = new StringBuilder();
			showMsg.append("Received message presence: ")
					.append(YunBaManager.MQTT_TOPIC).append(" = ")
					.append(topic).append(" ").append(YunBaManager.MQTT_MSG)
					.append(" = ").append(payload);
			Log.d("DemoReceiver", showMsg.toString());

		}

	}

	// send msg to MainActivity
	private void processCustomMessage(Context context, Intent intent) {


	}
}
