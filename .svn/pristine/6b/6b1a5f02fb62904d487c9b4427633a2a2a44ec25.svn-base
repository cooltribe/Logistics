package com.seeyuan.logistics.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.seeyuan.logistics.R;

public class ProgressBarButton extends ImageView {

	private Button progress_button;
	private ImageView progress_bar;
	private String content;

	public ProgressBarButton(Context context) {
		super(context);
	}

	public ProgressBarButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public ProgressBarButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private void initView(Context context, AttributeSet attrs) {
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.activity_progressbar_button, null);
		progress_button = (Button) contentView
				.findViewById(R.id.progress_button);
		progress_bar = (ImageView) contentView.findViewById(R.id.progress_bar);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.ProgressBarButton);
		int N = typedArray.getIndexCount();
		for (int i = 0; i < N; i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.ProgressBarButton_progressbar_background:
				progress_button
						.setBackgroundResource(typedArray
								.getResourceId(
										R.styleable.ProgressBarButton_progressbar_background,
										0));
				break;
			case R.styleable.ProgressBarButton_progressbar_text:
				progress_button.setText(typedArray.getResourceId(
						R.styleable.ProgressBarButton_progressbar_text, 0));
				break;
			case R.styleable.ProgressBarButton_progressbar_textColor:
				progress_button
						.setTextColor(typedArray
								.getResourceId(
										R.styleable.ProgressBarButton_progressbar_textColor,
										0));
				break;
			case R.styleable.ProgressBarButton_progressbar_textSize:
				progress_button.setTextSize(typedArray.getResourceId(
						R.styleable.ProgressBarButton_progressbar_textSize, 0));
				break;
			}
		}

	}

	public void setProgressButtonBackground(int resid) {
		progress_button.setBackgroundResource(resid);
	}

	public void setProgressButtonContent(String content) {
		this.content = content;
		progress_button.setText(this.content);
	}

	public void setProgressBarVisible(boolean visibility) {
		if (visibility) {
			progress_button.setText("");
			progress_bar.setVisibility(View.VISIBLE);
		} else {
			progress_button.setText(content);
			progress_bar.setVisibility(View.GONE);
		}
	}
}
