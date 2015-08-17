package com.seeyuan.logistics.util;

import com.seeyuan.logistics.application.ConstantPool;

import android.app.NotificationManager;
import android.content.Context;

public class NotificaionUtil {

	public static void clearNotification(Context context) {
		NotificationManager mgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
//		mgr.cancelAll();
		mgr.cancel(ConstantPool.DEFAULT_NOTIFICATION_ID);
	}

}
