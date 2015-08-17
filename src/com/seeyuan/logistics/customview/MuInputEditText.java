package com.seeyuan.logistics.customview;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seeyuan.logistics.R;

@SuppressLint("NewApi")
public class MuInputEditText extends EditText {
	private final static String TAG = "MuInputEditText";
	// private Drawable imgInable;
	private Drawable imgAble;
	private Drawable warning;
	private Context mContext;
	private Drawable background;
	private int normal_bg;
	private int max_length;
	private CustomPopupWindow popupWindow;
	private boolean isShowPopUpWindow = false;
	private View conentPopView;
	private int screenWidth;
	private int screenHeight;
	private boolean isCanTouch = true;

	public MuInputEditText(Context context) {
		super(context);
		mContext = context;
	}

	public MuInputEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public MuInputEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.MuInputEditText);
		int N = typedArray.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = typedArray.getIndex(i);

			switch (attr) {
			case R.styleable.MuInputEditText_normal_bg:
				normal_bg = typedArray.getResourceId(
						R.styleable.MuInputEditText_normal_bg, 0);
				break;
			case R.styleable.MuInputEditText_max_length:
				max_length = typedArray.getResourceId(
						R.styleable.MuInputEditText_max_length, 0);
				break;
			}
		}
		imgAble = mContext.getResources().getDrawable(R.drawable.delete);
		warning = mContext.getResources().getDrawable(
				R.drawable.muinput_warning_bg);
		background = getBackground();
		// viewWidth = getMeasuredWidth();
		// Log.d("TAG", "viewWidth = " + viewWidth);
		setBackgroundResource(normal_bg);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setOnFocusChangeListener(new OnFocusChangeListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				if (hasFocus() == false) {
					setBackgroundResource(normal_bg);
				} else {
//					setBackground(background);
					setBackgroundDrawable(background);
				}
			}
		});
		addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				isShowPopUpWindow = false;
				setDrawable();
			}
		});
		typedArray.recycle();
		setDrawable();
	}

	// 设置删除图片
	private void setDrawable() {
		if (length() < 1)
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
	}

	// 处理删除事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isCanTouch)
			return false;
		if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 80;
			if (rect.contains(eventX, eventY))
				setText("");
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 显示PopUpWindow
	 * 
	 * @param context
	 * @param content
	 *            显示内容
	 */
	public void showPopWindow(Activity activity, String content) {

		if (popupWindow == null)
			popupWindow = new CustomPopupWindow(activity, content);
		isShowPopUpWindow = true;
		setCompoundDrawablesWithIntrinsicBounds(null, null, warning, null);

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
		screenWidth = dm.widthPixels;// 宽度
		screenHeight = dm.heightPixels;// 高度
		int[] location = new int[2];
		getLocationOnScreen(location);
		popX = screenWidth - (location[0] + getMeasuredWidth())
				- warning.getIntrinsicWidth() / 4;
		// popY = location[1] - screenHeight / 2 + getMeasuredHeight()
		// + warning.getIntrinsicHeight() / 3 * 2;
		// popX = warning.getIntrinsicWidth() / 4;
		popY = location[1] + warning.getIntrinsicHeight()
				+ warning.getIntrinsicWidth() / 2;
	}

	private int popX;
	private int popY;

	@Override
	public boolean isFocused() {
		boolean isFocused = super.isFocused();
		if (popupWindow != null) {
			if (isFocused && isShowPopUpWindow) {
				popupWindow.showAtLocation(this, Gravity.RIGHT | Gravity.TOP,
						popX, popY);
			} else {
				popupWindow.dismiss();
			}
		}
		return isFocused;
	}

	/**
	 * 隐藏PopUpWindow
	 */
	public void dismissPopWindow() {
		if (popupWindow != null && popupWindow.isShowing())
			popupWindow.dismiss();
	}

	// 创建一个包含自定义view的PopupWindow
	@SuppressWarnings("deprecation")
	private PopupWindow makePopupWindow(Context context, String content) {
		PopupWindow window;
		window = new PopupWindow(context);

		View contentView = LayoutInflater.from(context).inflate(
				R.layout.activity_muinputedit_popwindow, null);
		TextView textView = (TextView) contentView
				.findViewById(R.id.muinput_content);
		textView.setText(content);
		window.setContentView(contentView);

		// 设置PopupWindow外部区域是否可触摸
		window.setFocusable(true); // 设置PopupWindow可获得焦点
		window.setTouchable(true); // 设置PopupWindow可触摸
		window.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		window.update();
		return window;
	}

	public class CustomPopupWindow extends PopupWindow {

		public CustomPopupWindow(final Activity context, String content) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			conentPopView = inflater.inflate(
					R.layout.activity_muinputedit_popwindow, null);
			RelativeLayout layout = (RelativeLayout) conentPopView
					.findViewById(R.id.muinput_background);
			TextView textView = (TextView) conentPopView
					.findViewById(R.id.muinput_content);
			textView.setText(content);
			// 设置SelectPicPopupWindow的View
			// popWidth = conentPopView.getMeasuredWidth();
			// Log.d("TAG", "popWidth = " + popWidth);
			setContentView(conentPopView);
			// 设置SelectPicPopupWindow弹出窗体的宽
			setWidth(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			setHeight(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体可点击
			setFocusable(false);
			setTouchable(false);
			setOutsideTouchable(true);
			// 刷新状态
			update();
		}
	}

	public void setCanTouch(boolean isTouch) {
		isCanTouch = isTouch;
	}

}
