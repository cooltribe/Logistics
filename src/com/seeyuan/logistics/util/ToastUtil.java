/**
 * 
 */
package com.seeyuan.logistics.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

	public static void show(Context context, String info) {
		if (TextUtils.isEmpty(info))
			return;
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	public static void show(Context context, String info, boolean longtoast) {
		if (TextUtils.isEmpty(info))
			return;
		if (longtoast) {
			Toast.makeText(context, info, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
		}
	}

	public static void show(Context context, int info, boolean longtoast) {
		if (longtoast) {
			Toast.makeText(context, info, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
		}
	}
}
