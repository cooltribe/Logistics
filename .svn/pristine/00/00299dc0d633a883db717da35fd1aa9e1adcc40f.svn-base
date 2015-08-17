package com.seeyuan.logistics.util;

import android.view.Display;
import android.view.WindowManager;

public class ScreenUtil {
	private static ScreenUtil instance;

	private int screenHeight;
	private int screenWidth;

	public ScreenUtil(WindowManager windowManager) {
		instance = this;
		Display display = windowManager.getDefaultDisplay();
		this.screenHeight = display.getHeight(); // 屏幕高度
		this.screenWidth = display.getWidth(); // 屏幕宽度
	}

	public static ScreenUtil getInstance() {
		return instance;
	}

	/**
	 * 获得屏幕的高度。
	 * 
	 * @return
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * 获得屏幕的宽度。
	 * 
	 * @return
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

}
