package com.seeyuan.logistics.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seeyuan.logistics.R;
import com.seeyuan.logistics.datacenter.DataHandler;
import com.seeyuan.logistics.datacenter.OnDataReceiveListener;
import com.seeyuan.logistics.entity.OrderDto;

public class OrderTypeButton extends LinearLayout implements OnClickListener,
		OnDataReceiveListener {

	private Context context;

	private OrderDto orderDto;

	/**
	 * 初始状态，叫价
	 */
	private final int CALL_PRICE = 100;

	/**
	 * 待车主确认
	 */
	private final int DRIVER_CONFIRMED = 101;
	/**
	 * 待货主确认
	 */
	private final int GOODS_CONFIRMED = 102;

	/**
	 * 订单确认
	 */
	private final int ORDER_CONFIRMED = 103;

	/**
	 * 已经发货
	 */
	private final int HIPPED = 104;

	/**
	 * 已经到货
	 */
	private final int ARRIVAL = 105;

	/**
	 * 已经签收
	 */
	private final int SIGN = 106;

	/**
	 * 上传回单
	 */
	private final int UPDATE_RECEIPT = 107;

	/**
	 * 回单确认
	 */
	private final int RECEIPT_CONFIRMED = 108;

	/**
	 * 订单完结
	 */
	private final int ORDER_OVER = 109;

	/**
	 * 订单状态
	 */
	private int orderType = -1;

	private TextView appaction_main;

	public OrderTypeButton(Context context) {
		super(context);
		this.context = context;
	}

	public OrderTypeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}

	public OrderTypeButton(Context context, AttributeSet attrs,
			OrderDto orderDto) {
		super(context, attrs);
		this.orderDto = orderDto;
	}

	public void setUserInfo(OrderDto orderDto) {
		this.orderDto = orderDto;
		initButton();
		initView();
	}

	private void initView() {
		// 导入布局
		LayoutInflater.from(context).inflate(R.layout.appaction_layout, this,
				true);
		appaction_main = (TextView) findViewById(R.id.appaction_main);
		appaction_main.setText(getShowText(orderType));
		appaction_main.setOnClickListener(this);
	}

	private void initButton() {
		switch (Integer.parseInt(orderDto.getOrderStatus())) {
		case 0:
			orderType = CALL_PRICE;
			break;
		case 1:
			orderType = DRIVER_CONFIRMED;
			break;
		case 2:
			orderType = GOODS_CONFIRMED;
			break;
		case 3:
			orderType = ORDER_CONFIRMED;
			break;
		case 4:
			orderType = HIPPED;
			break;
		case 5:
			orderType = ARRIVAL;
			break;
		case 6:
			orderType = SIGN;
			break;
		case 7:
			orderType = UPDATE_RECEIPT;
			break;
		case 8:
			orderType = RECEIPT_CONFIRMED;
			break;
		case 9:
			orderType = ORDER_OVER;
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		doOrderClick(orderType);
	}

	private void doOrderClick(int orderType) {
		switch (orderType) {
		case CALL_PRICE:
			break;
		case DRIVER_CONFIRMED:
			break;
		case GOODS_CONFIRMED:
			break;
		case ORDER_CONFIRMED:
			break;
		case HIPPED:
			break;
		case ARRIVAL:
			break;
		case SIGN:
			break;
		case UPDATE_RECEIPT:
			break;
		case RECEIPT_CONFIRMED:
			break;
		case ORDER_OVER:
			break;
		default:
			break;
		}
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {

	}

	private String getShowText(int orderType) {
		String result = "";
		switch (orderType) {
		case CALL_PRICE:
			result = "报价";
			break;
		case DRIVER_CONFIRMED:
			result = "重新报价";
			break;
		case GOODS_CONFIRMED:
			result = "重新报价";
			break;
		case ORDER_CONFIRMED:
			result = "订单确认";
			break;
		case HIPPED:
			result = "已经发货";
			break;
		case ARRIVAL:
			result = "已经到达";
			break;
		case SIGN:
			result = "已经签收";
			break;
		case UPDATE_RECEIPT:
			result = "上传回单";
			break;
		case RECEIPT_CONFIRMED:
			result = "回单确认";
			break;
		case ORDER_OVER:
			result = "结束";
			break;
		default:
			break;
		}
		return result;
	}
}
