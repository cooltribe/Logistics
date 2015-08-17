package com.seeyuan.logistics.util;

import android.util.Log;

public class LogTag {
	private static boolean islog = true;

	private static String prettyArray(String[] array) {
		if (array.length == 0) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder("[");
		int len = array.length - 1;
		for (int i = 0; i < len; i++) {
			sb.append(array[i]);
			sb.append(", ");
		}
		sb.append(array[len]);
		sb.append("]");

		return sb.toString();
	}

	private static String logFormat(String format, Object... args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof String[]) {
				args[i] = prettyArray((String[]) args[i]);
			}
		}
		String s = String.format(format, args);
		s = "[" + Thread.currentThread().getId() + "] " + s;
		return s;
	}

	// public static void debug(String format, Object... args) {
	// LogTag.d(TAG, logFormat(format, args));
	// }
	//
	// public static void warn(String format, Object... args) {
	// LogTag.w(TAG, logFormat(format, args));
	// }
	//
	// public static void error(String format, Object... args) {
	// LogTag.e(TAG, logFormat(format, args));
	// }

	/**
	 * debug级别信息log
	 */
	public static void d(String tag, String log_content) {
		if (islog) {
			Log.d(tag, log_content);
		}
	}

	/**
	 * v级别信息log
	 */
	public static void v(String tag, String log_content) {
		if (islog) {
			Log.v(tag, log_content);
		}
	}

	/**
	 * 提示级别log
	 * 
	 * @param tag
	 * @param log_content
	 */
	public static void i(String tag, String log_content) {
		if (islog) {
			Log.i(tag, log_content);
		}
	}

	/**
	 * 警告级别log
	 * 
	 * @param tag
	 * @param log_content
	 */
	public static void w(String tag, String log_content) {
		if (islog) {
			Log.w(tag, log_content);
		}
	}

	/**
	 * 错误级别log
	 * 
	 * @param tag
	 * @param log_content
	 */
	public static void e(String tag, String log_content) {
		if (islog) {
			Log.e(tag, log_content);
		}
	}

	public static void f(String tag, String log_content) {
		if (islog) {
			Log.e(tag, log_content);
		}
	}
}
