package com.seeyuan.logistics.application;

/**
 * 网络相关的常量 ，如服务器地址，接口action等 NetWork.java
 * 
 * @author zhazhaobao
 */
public class NetWork {
	
//	public static String SERVER_URL = "http://192.168.2.36:8877/Searun_platform/";
//	public static String RETURN_URL = "http://221.226.22.82:6666/Searun_platform/";
	public static String SERVER_URL = ApplicationPool.SERVER_URL;
	public static String RETURN_URL = ApplicationPool.RETURN_URL;

	/**
	 * 获取附近车源信息
	 */
	public static String FIND_AROUND_CAR_ACTION = SERVER_URL
			+ "ep/apps/vehicle_requestNearVehicle.action";
	public static final int FIND_AROUND_CAR_ERROR = 1001;
	public static final int FIND_AROUND_CAR_OK = 1002;

	/**
	 * 刷新获取最新的车源信息
	 */
	public static String REFRESH_AROUND_CAR_ACTION = SERVER_URL
			+ "ep/apps/vehicle_refreshNearVehicle.action";
	public static final int REFRESH_AROUND_CAR_ERROR = 1003;
	public static final int REFRESH_AROUND_CAR_OK = 1004;

	/**
	 * 上传地理位置信息
	 */
	//collectUserInfo    by zha
	public static String SUBMIT_PERSONAL_POSITION_ACTION = SERVER_URL
			+ "apps/collectUserInfo.action";
	public static final int SUBMIT_PERSONAL_POSITION_ERROR = 1005;
	public static final int SUBMIT_PERSONAL_POSITION_OK = 1006;
	/**
	 * 上传车辆地理位置信息
	 */
	//collectUserInfo    by zha
	public static String SUBMIT_CAR_POSITION_ACTION = SERVER_URL
			+ "apps/getPdaUserLocation.action";
	public static final int SUBMIT_CAR_POSITION_ERROR = 9005;
	public static final int SUBMIT_CAR_POSITION_OK = 9006;
	/**
	 * 获取附近好友
	 */
	public static String FIND_AROUND_FIRENDS_ACTION = SERVER_URL
			+ "ep/apps/public_requestNearFriend.action?";
	public static final int FIND_AROUND_FIRENDS_ERROR = 1007;
	public static final int FIND_AROUND_FIRENDS_OK = 1008;

	/**
	 * 登录
	 */
	public static String lOGIN_ACTION = SERVER_URL
			+ "apps/checkPdaUserLogin.action";
	public static final int LOGIN_ERROR = 1009;
	public static final int LOGIN_OK = 1010;

	/**
	 * 提交注册账号信息
	 */
	public static String SUBMIT_REGISTER_INFO_ACTION = SERVER_URL
			+ "apps/checkPDAUser.action";
	public static final int SUBMIT_REGISTER_INFO_ERROR = 1011;
	public static final int SUBMIT_REGISTER_INFO_OK = 1012;

	/**
	 * 提交注册账号验证码
	 */
	public static String SUBMIT_REGISTER_AUTHCODE_ACTION = SERVER_URL
			+ "apps/checkPDAVerifyCode.action";
	public static final int SUBMIT_REGISTER_AUTHCODE_ERROR = 1013;
	public static final int SUBMIT_REGISTER_AUTHCODE_OK = 1014;

	/**
	 * 提交注册账号密码
	 */
	public static String SUBMIT_REGISTER_PASSWORD_ACTION = SERVER_URL
			+ "apps/regestPDAUser.action";
	public static final int SUBMIT_REGISTER_PASSWORD_ERROR = 1015;
	public static final int SUBMIT_REGISTER_PASSWORD_OK = 1016;

	/**
	 * 找回注册账号验证码
	 */
	public static String SUBMIT_RETRIEVEE_AUTHCODE_ACTION = SERVER_URL + "";
	public static final int SUBMIT_RETRIEVEE_AUTHCODE_ERROR = 1017;
	public static final int SUBMIT_RETRIEVEE_AUTHCODE_OK = 1018;

	/**
	 * 重新设置注册账号密码
	 */
	public static String SUBMIT_RETRIEVEE_SETTING_ACTION = SERVER_URL + "";
	public static final int SUBMIT_RETRIEVEE_SETTING_ERROR = 1019;
	public static final int SUBMIT_RETRIEVEE_SETTING_OK = 1020;

	/**
	 * 收藏
	 */
	public static String COLLECT_SOURCE_ACTION = SERVER_URL + "";
	public static final int COLLECT_SOURCE_ERROR = 1021;
	public static final int COLLECT_SOURCE_OK = 1022;

	/**
	 * 提交评价
	 */
	public static String SUBMIT_EVALUATE_ACTION = SERVER_URL + "";
	public static final int SUBMIT_EVALUATE_ERROR = 1023;
	public static final int SUBMIT_EVALUATE_OK = 1024;

	/**
	 * 发布车源
	 * 添加线路 by wang
	 */
	public static String PUBLISH_CAR_SOURCE_ACTION = SERVER_URL
			+ "apps/releaseRouteByPda.action";
	public static final int PUBLISH_CAR_SOURCE_ERROR = 1025;
	public static final int PUBLISH_CAR_SOURCE_OK = 1026;

	/**
	 * 发布货源
	 */
	public static String PUBLISH_GOODS_SOURCE_ACTION = SERVER_URL
			+ "apps/releaseGoodsByPda.action";
	public static final int PUBLISH_GOODS_SOURCE_ERROR = 1027;
	public static final int PUBLISH_GOODS_SOURCE_OK = 1028;

	/**
	 * 发布专线
	 */
	public static String PUBLISH_LINE_SOURCE_ACTION = SERVER_URL + "";
	public static final int PUBLISH_LINE_SOURCE_ERROR = 1029;
	public static final int PUBLISH_LINE_SOURCE_OK = 1030;

	/**
	 * 天气
	 */
	public static String WEATHER_SOURCE_ACTION = "http://v.juhe.cn/weather/index?";
	public static final int WEATHER_SOURCE_ERROR = 1031;
	public static final int WEATHER_SOURCE_OK = 1032;

	/**
	 * 附近加油站
	 */
	public static String GAS_STATION_SOURCE_ACTION = "http://apis.juhe.cn/oil/local?";
	public static final int GAS_STATION_SOURCE_ERROR = 1033;
	public static final int GAS_STATION_SOURCE_OK = 1034;

	/**
	 * 搜索货源
	 */
	public static String SEARCH_GOODS_ACTION = SERVER_URL
			+ "apps/searchGoodsByPda.action";
	public static final int SEACH_GOODS_ERROR = 1035;
	public static final int SEACH_GOODS_OK = 1036;

	/**
	 * 搜索车源
	 */
	public static String SEARCH_CAR_SOURCE_ACTION = SERVER_URL
			+ "apps/searchRouteByPda.action";
	public static final int SEACH_CAR_SOURCE_ERROR = 1037;
	public static final int SEACH_CAR_SOURCE_OK = 1038;

	/**
	 * 添加新司机
	 */
	public static String ADD_DRIVER_ACTION = SERVER_URL
			+ "apps/registDriverByPda.action";
	public static final int ADD_DRIVER_ERROR = 1039;
	public static final int ADD_DRIVER_OK = 1040;

	/**
	 * 获取司机信息
	 */
	public static String GET_DRIVER_INFO_ACTION = SERVER_URL
			+ "apps/searchDriverByPda.action";
	public static final int GET_DRIVER_INFO_ERROR = 1041;
	public static final int GET_DRIVER_INFO_OK = 1042;

	/**
	 * 获取司机信息
	 */
	public static String DELETE_DRIVER_INFO_ACTION = SERVER_URL
			+ "apps/deleteDriver.action";
	public static final int DELETE_DRIVER_INFO_ERROR = 1043;
	public static final int DELETE_DRIVER_INFO_OK = 1044;

	/**
	 * 获取司机信息
	 */
	public static String EDIT_DRIVER_INFO_ACTION = SERVER_URL
			+ "apps/updateDriverByPda.action";
	public static final int EDIT_DRIVER_INFO_ERROR = 1045;
	public static final int EDIT_DRIVER_INFO_OK = 1046;

	/**
	 * 添加新车辆
	 */
	public static String ADD_CAR_ACTION = SERVER_URL + "apps/addPdaCars.action";
	public static final int ADD_CAR_ERROR = 1047;
	public static final int ADD_CAR_OK = 1048;

	/**
	 * 搜索车辆
	 */
	public static String SEARCH_CAR_ACTION = SERVER_URL
			+ "apps/searchCarByPda.action";
	public static final int SEARCH_CAR_ERROR = 1049;
	public static final int SEARCH_CAR_OK = 1050;

	/**
	 * 修改车辆信息
	 */
	public static String UPDATE_CAR_ACTION = SERVER_URL
			+ "apps/updateCarsByPda.action";
	public static final int UPDATE_CAR_ERROR = 1051;
	public static final int UPDATE_CAR_OK = 1052;

	/**
	 * 删除车辆信息
	 */
	public static String DELETE_CAR_ACTION = SERVER_URL
			+ "apps/deleteCarByPda.action";
	public static final int DELETE_CAR_ERROR = 1053;
	public static final int DELETE_CAR_OK = 1054;

	/**
	 * 获取用户信息
	 */
	public static String GET_USERINFO_ACTION = SERVER_URL
			+ "apps/getmemberInfoByPda.action";
	public static final int GET_USERINFO_ERROR = 1054;
	public static final int GET_USERINFO_OK = 1055;

	/**
	 * 修改用户信息
	 */
	public static String UPDATE_USERINFO_ACTION = SERVER_URL
			+ "apps/updateMemberInfoByPda.action";
	public static final int UPDATE_USERINFO_ERROR = 1056;
	public static final int UPDATE_USERINFO_OK = 1057;

	/**
	 * 获取用户订单信息
	 */
	public static String GET_ORDER_INFO_ACTION = SERVER_URL
			+ "apps/getOrdersByPda.action";
	public static final int GET_ORDER_INFO_ERROR = 1058;
	public static final int GET_ORDER_INFO_OK = 1059;

	/**
	 * 生成订单
	 */
	public static String CREATE_ORDER_ACTION = SERVER_URL
			+ "apps/createOderByPda.action";
	public static final int CREATE_ORDER_ERROR = 1060;
	public static final int CREATE_ORDER_OK = 1061;

	/**
	 * 检测更新
	 */
	public static String CHECK_UPDATE_ACTION = SERVER_URL
			+ "apps/checkPdaVersionInfo.action";
	public static final int CHECK_UPDATE_ERROR = 1062;
	public static final int CHECK_UPDATE_OK = 1063;

	/**
	 * 获取货源信息
	 */
	public static String GET_GOODS_INFO_ACTION = SERVER_URL
			+ "apps/getUserGoodsByPda.action";
	public static final int GET_GOODS_INFO_ERROR = 1064;
	public static final int GET_GOODS_INFO_OK = 1065;

	/**
	 * 修改货源信息
	 */
	public static String EDIT_GOODS_INFO_ACTION = SERVER_URL
			+ "apps/editGoodsByPda.action";
	public static final int EDIT_GOODS_INFO_ERROR = 1066;
	public static final int EDIT_GOODS_INFO_OK = 1067;

	/**
	 * 下单
	 */
	public static String PLACE_AN_ORDER_ACTION = SERVER_URL
			+ "apps/createOderByPda.action";
	public static final int PLACE_AN_ORDER_ERROR = 1068;
	public static final int PLACE_AN_ORDER_OK = 1069;

	/**
	 * 下单
	 */
	public static String ORDER_COMPLETE_ACTION = SERVER_URL
			+ "apps/createOderByPda.action";
	public static final int ORDER_COMPLETE_ERROR = 1070;
	public static final int ORDER_COMPLETE_OK = 1071;

	/**
	 * 修改订单价格
	 */
	public static String EDIT_ORDER_PRICE_ACTION = SERVER_URL
			+ "apps/changeOrderPriceByPda.action";
	public static final int EDIT_ORDER_PRICE_ERROR = 1072;
	public static final int EDIT_ORDER_PRICE_OK = 1073;

	/**
	 * 获取订单详细详细
	 */
	public static String GET_ORDER_DETAIL_ACTION = SERVER_URL
			+ "apps/findOrderInfoByPda.action";
	public static final int GET_ORDER_DETAIL_ERROR = 1074;
	public static final int GET_ORDER_DETAIL_OK = 1075;

	/**
	 * 修改订单状态
	 */
	public static String CHANGE_ORDER_STATUS_ACTION = SERVER_URL
			+ "apps/changeOrderStatusByPda.action";
	public static final int CHANGE_ORDER_STATUS_ERROR = 1076;
	public static final int CHANGE_ORDER_STATUS_OK = 1077;

	/**
	 * 获取当前订单提示信息
	 */
	public static String GET_CURRENT_ORDER_MESSAGE_ACTION = SERVER_URL
			+ "apps/getStatisticalInfo.action";
	public static final int GET_CURRENT_ORDER_MESSAGE_ERROR = 1078;
	public static final int GET_CURRENT_ORDER_MESSAGE_OK = 1079;

	/**
	 * 删除货源
	 */
	public static String DELETE_GOODS_ACTION = SERVER_URL
			+ "apps/delGoodsByUser.action";
	public static final int DELETE_GOODS_ERROR = 1080;
	public static final int DELETE_GOODS_OK = 1081;

	/**
	 * 删除线路
	 */
	public static String DELETE_LINE_ACTION = SERVER_URL
			+ "apps/deleteRouteByPda.action";
	public static final int DELETE_LINE_ERROR = 1082;
	public static final int DELETE_LINE_OK = 1083;

	/**
	 * 获取订单操作日志
	 */
	public static String GET_ORDER_OPERATION_DATA_ACTION = SERVER_URL
			+ "apps/getOrderTracesByPda.action";
	public static final int GET_ORDER_OPERATION_DATA_ERROR = 1084;
	public static final int GET_ORDER_OPERATION_DATA_OK = 1085;

	/**
	 * 修改线路
	 */
	public static String UPDATA_LINE_ACTION = SERVER_URL
			+ "apps/updateRouteByPda.action";
	public static final int UPDATA_LINE_ERROR = 1086;
	public static final int UPDATA_LINE_OK = 1087;

	/**
	 * 短信接口
	 */
	public static String SEND_SMS_ACTION = SERVER_URL
			+ "apps/sendSmsByPda.action";
	public static final int SEND_SMS_ERROR = 1086;
	public static final int SEND_SMS_OK = 1087;

	/**
	 * 实名认证
	 */
	public static String CERTIFICATION_ACTION = SERVER_URL
			+ "apps/memberAuthByPda.action";
	public static final int CERTIFICATION_ERROR = 1088;
	public static final int CERTIFICATION_OK = 1089;

	/**
	 * 获取实名认证信息
	 */
	public static String GET_CERTIFICATION_INFO_ACTION = SERVER_URL
			+ "apps/getMemeberAuthInfo.action";
	public static final int GET_CERTIFICATION_INFO_ERROR = 1090;
	public static final int GET_CERTIFICATION_INFO_OK = 1091;

	/**
	 * 获取企业认证信息
	 */
	public static String GET_COMPANY_AUTHENTICATION_INFO_ACTION = SERVER_URL
			+ "apps/getCompanyAuthInfo.action";
	public static final int GET_COMPANY_AUTHENTICATION_INFO_ERROR = 1092;
	public static final int GET_COMPANY_AUTHENTICATION_INFO_OK = 1093;

	/**
	 * 企业认证
	 */
	public static String COMPANY_AUTHENTICATION_ACTION = SERVER_URL
			+ "apps/companyAuthByPda.action";
	public static final int COMPANY_AUTHENTICATION_ERROR = 1094;
	public static final int COMPANY_AUTHENTICATION_OK = 1095;

	public static String ORDER_TRACKING_ACTION = SERVER_URL
			+ "baidumap/baidumap_toMap.action";

	/**
	 * 会员充值
	 */
	public static String RECHARGE_ACTION = SERVER_URL + "apps/doCharge.action";
	public static final int RECHARGE_ERROR = 1096;
	public static final int RECHARGE_OK = 1097;

	/**
	 * 获取充值账号信息
	 */
	public static String GET_RECHARGE_ACCOUNT_ACTION = SERVER_URL
			+ "apps/getMemberAccount.action";
	public static final int GET_RECHARGE_ACCOUNT_ERROR = 1098;
	public static final int GET_RECHARGE_ACCOUNT_OK = 1099;

	/**
	 * 获取充值账号信息
	 */
	public static String GET_ACCOUNT_INFO_ACTION = SERVER_URL
			+ "apps/getAccounts.action";
	public static final int GET_ACCOUNT_INFO_ERROR = 1100;
	public static final int GET_ACCOUNT_INFO_OK = 1101;

	/**
	 * 添加充值账号
	 */
	public static String ADD_ACCOUNT_ACTION = SERVER_URL
			+ "apps/addAccountByPda.action";
	public static final int ADD_ACCOUNT_ERROR = 1102;
	public static final int ADD_ACCOUNT_OK = 1103;

	/**
	 * 删除充值账号
	 */
	public static String DELETE_ACCOUNT_ACTION = SERVER_URL
			+ "apps/delAccountByPda.action";
	public static final int DELETE_ACCOUNT_ERROR = 1104;
	public static final int DELETE_ACCOUNT_OK = 1105;

	/**
	 * 修改充值账号
	 */
	public static String UPDATE_ACCOUNT_ACTION = SERVER_URL
			+ "apps/updateAccountByPda.action";
	public static final int UPDATE_ACCOUNT_ERROR = 1106;
	public static final int UPDATE_ACCOUNT_OK = 1107;

	/**
	 * 获取结算单
	 */
	public static String GET_ACCOUNT_SETTLEMENT_ACTION = SERVER_URL
			+ "apps/getAccountSettles.action";
	public static final int GET_ACCOUNT_SETTLEMENT_ERROR = 1108;
	public static final int GET_ACCOUNT_SETTLEMENT_OK = 1109;

	/**
	 * 获取结算单明细
	 */
	public static String GET_ACCOUNT_SETTLEMENT_DETAIL_ACTION = SERVER_URL
			+ "apps/getAccountSettleDettles.action";
	public static final int GET_ACCOUNT_SETTLEMENT_DETAIL_ERROR = 1108;
	public static final int GET_ACCOUNT_SETTLEMENT_DETAIL_OK = 1109;

	/**
	 * 获取结算单明细
	 */
	public static String GET_DEAL_MANAGER_ACTION = SERVER_URL
			+ "apps/getAccountLogs.action";
	public static final int GET_DEAL_MANAGER_ERROR = 1110;
	public static final int GET_DEAL_MANAGER_OK = 1111;

	/**
	 * 获取订单结算 计算规则
	 */
	public static String GET_CALCULATE_INFO_ACTION = SERVER_URL
			+ "apps/toPayByPda.action";
	public static final int GET_CALCULATE_INFO_ERROR = 1112;
	public static final int GET_CALCULATE_INFO_OK = 1113;

	/**
	 * 付款。
	 */
	public static String SUBMIT_PAYMENT_ACTION = SERVER_URL
			+ "apps/payByPda.action";
	public static final int SUBMIT_PAYMENT_ERROR = 1114;
	public static final int SUBMIT_PAYMENT_OK = 1115;

	/**
	 * 找回密码。提交密码
	 */
	public static String SUBMIT_RETRIVEV_PASSWORD_ACTION = SERVER_URL
			+ "apps/setPassword.action";
	public static final int SUBMIT_RETRIVEV_PASSWORD_ERROR = 1124;
	public static final int SUBMIT_RETRIVEV_PASSWORD_OK = 1125;

	public static void setServerIp(String url) {
		SERVER_URL = url;
		FIND_AROUND_CAR_ACTION = SERVER_URL
				+ "ep/apps/vehicle_requestNearVehicle.action";
		REFRESH_AROUND_CAR_ACTION = SERVER_URL
				+ "ep/apps/vehicle_refreshNearVehicle.action";
		SUBMIT_PERSONAL_POSITION_ACTION = SERVER_URL
				+ "apps/collectUserInfo.action";
		FIND_AROUND_FIRENDS_ACTION = SERVER_URL
				+ "ep/apps/public_requestNearFriend.action?";
		lOGIN_ACTION = SERVER_URL + "apps/checkPdaUserLogin.action";
		SUBMIT_REGISTER_INFO_ACTION = SERVER_URL + "apps/checkPDAUser.action";
		SUBMIT_REGISTER_AUTHCODE_ACTION = SERVER_URL
				+ "apps/checkPDAVerifyCode.action";
		SUBMIT_REGISTER_PASSWORD_ACTION = SERVER_URL
				+ "apps/regestPDAUser.action";
		SUBMIT_RETRIEVEE_AUTHCODE_ACTION = SERVER_URL + "";
		COLLECT_SOURCE_ACTION = SERVER_URL + "";
		SUBMIT_EVALUATE_ACTION = SERVER_URL + "";
		PUBLISH_CAR_SOURCE_ACTION = SERVER_URL
				+ "apps/releaseRouteByPda.action";
		PUBLISH_GOODS_SOURCE_ACTION = SERVER_URL
				+ "apps/releaseGoodsByPda.action";
		PUBLISH_LINE_SOURCE_ACTION = SERVER_URL + "";
		WEATHER_SOURCE_ACTION = "http://v.juhe.cn/weather/index?";
		GAS_STATION_SOURCE_ACTION = "http://apis.juhe.cn/oil/local?";
		SEARCH_GOODS_ACTION = SERVER_URL + "apps/searchGoodsByPda.action";
		SEARCH_CAR_SOURCE_ACTION = SERVER_URL + "apps/searchRouteByPda.action";
		ADD_DRIVER_ACTION = SERVER_URL + "apps/registDriverByPda.action";
		GET_DRIVER_INFO_ACTION = SERVER_URL + "apps/searchDriverByPda.action";
		DELETE_DRIVER_INFO_ACTION = SERVER_URL + "apps/deleteDriver.action";
		EDIT_DRIVER_INFO_ACTION = SERVER_URL + "apps/updateDriverByPda.action";
		ADD_CAR_ACTION = SERVER_URL + "apps/addPdaCars.action";
		SEARCH_CAR_ACTION = SERVER_URL + "apps/searchCarByPda.action";
		UPDATE_CAR_ACTION = SERVER_URL + "apps/updateCarsByPda.action";
		DELETE_CAR_ACTION = SERVER_URL + "apps/deleteCarByPda.action";
		GET_USERINFO_ACTION = SERVER_URL + "apps/getmemberInfoByPda.action";
		UPDATE_USERINFO_ACTION = SERVER_URL
				+ "apps/updateMemberInfoByPda.action";
		GET_ORDER_INFO_ACTION = SERVER_URL + "apps/getOrdersByPda.action";
		CREATE_ORDER_ACTION = SERVER_URL + "apps/createOderByPda.action";
		CHECK_UPDATE_ACTION = SERVER_URL + "apps/checkPdaVersionInfo.action";
		GET_GOODS_INFO_ACTION = SERVER_URL + "apps/getUserGoodsByPda.action";
		PLACE_AN_ORDER_ACTION = SERVER_URL + "apps/createOderByPda.action";
		ORDER_COMPLETE_ACTION = SERVER_URL + "apps/createOderByPda.action";
		EDIT_ORDER_PRICE_ACTION = SERVER_URL
				+ "apps/changeOrderPriceByPda.action";
		GET_ORDER_DETAIL_ACTION = SERVER_URL + "apps/findOrderInfoByPda.action";
		CHANGE_ORDER_STATUS_ACTION = SERVER_URL
				+ "apps/changeOrderStatusByPda.action";
		GET_CURRENT_ORDER_MESSAGE_ACTION = SERVER_URL
				+ "apps/getStatisticalInfo.action";
		DELETE_GOODS_ACTION = SERVER_URL + "apps/delGoodsByUser.action";
		DELETE_LINE_ACTION = SERVER_URL + "apps/deleteRouteByPda.action";
		GET_ORDER_OPERATION_DATA_ACTION = SERVER_URL
				+ "apps/getOrderTracesByPda.action";
		UPDATA_LINE_ACTION = SERVER_URL + "apps/updateRouteByPda.action";
		SEND_SMS_ACTION = SERVER_URL + "apps/sendSmsByPda.action";
		CERTIFICATION_ACTION = SERVER_URL + "apps/memberAuthByPda.action";
		GET_CERTIFICATION_INFO_ACTION = SERVER_URL
				+ "apps/getMemeberAuthInfo.action";
		GET_COMPANY_AUTHENTICATION_INFO_ACTION = SERVER_URL
				+ "apps/getCompanyAuthInfo.action";
		COMPANY_AUTHENTICATION_ACTION = SERVER_URL
				+ "apps/companyAuthByPda.action";
		ORDER_TRACKING_ACTION = SERVER_URL + "baidumap/baidumap_toMap.action";
		RECHARGE_ACTION = SERVER_URL + "apps/doCharge.action";
		GET_RECHARGE_ACCOUNT_ACTION = SERVER_URL
				+ "apps/getMemberAccount.action";
		GET_ACCOUNT_INFO_ACTION = SERVER_URL + "apps/getAccounts.action";
		ADD_ACCOUNT_ACTION = SERVER_URL + "apps/addAccountByPda.action";
		DELETE_ACCOUNT_ACTION = SERVER_URL + "apps/delAccountByPda.action";
		UPDATE_ACCOUNT_ACTION = SERVER_URL + "apps/updateAccountByPda.action";
		GET_ACCOUNT_SETTLEMENT_ACTION = SERVER_URL
				+ "apps/getAccountSettles.action";
		GET_ACCOUNT_SETTLEMENT_DETAIL_ACTION = SERVER_URL
				+ "apps/getAccountSettleDettles.action";
		GET_DEAL_MANAGER_ACTION = SERVER_URL + "apps/getAccountLogs.action";
		GET_CALCULATE_INFO_ACTION = SERVER_URL + "apps/toPayByPda.action";
		SUBMIT_PAYMENT_ACTION = SERVER_URL + "apps/payByPda.action";
		SUBMIT_RETRIVEV_PASSWORD_ACTION = SERVER_URL
				+ "apps/setPassword.action";
	}
}
