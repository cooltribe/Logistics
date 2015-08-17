package com.seeyuan.logistics.alipay;

public class PaySetting {
	// 商户PID(合作身份者id，以2088开头的16位纯数字，这个你申请支付宝签约成功后就会看见)
	public static final String PARTNER = "2088811454929814";
	// 商户收款账号
	// 商户收款账号(这里填写收款支付宝账号，即你付款后到账的支付宝账号)
	public static final String SELLER = "searunbugbase@163.com";
	// 商户私钥，pkcs8格式
	// 商户私钥，pkcs8格式(商户私钥，自助生成，即rsa_private_key.pem中去掉首行，最后一行，空格和换行最后拼成一行的字符串，
	// rsa_private_key.pem这个文件等你申请支付宝签约成功后，按照文档说明你会生成的,如果android版本太高，这里要用PKCS8格式用户私钥，
	// 不然调用不会成功的，那个格式你到时候会生成的，表急。)
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN5NjU42gne7LGT3id/xXogwWN4DTG70XiixNOAeWoQIqu3rB9uFW747Xw3EFUT95cp37bQAoSfgoJxJZ/4IBrzx527YCsq1/3yVcFKzeI0s3smmw8LkupHnxd5CEKluP5GvZ3W/9hFT/YaDkBEqPBDAhKAKZizoYJIKqdYDxkTPAgMBAAECgYA7reIzjxqzfgJLENFo12mjcidJYuVQHDZzAi/JwxxVueX5fVFcs46PoWzBS1TScr8P/eZInqqlA/7aNjK+1fTIoKEqStr5PoNJjjC/TVKYHcFSBRtJBu5zHeCGUHkUcoJZpfOFVS5Lm2+uAzaOs27XZYg3TSDbWPRkOaKrwvStAQJBAPQ2o5b8CLayRXQh30zddfn+o3VXc36lmOJBxhaG1kR/HS/vAWBg0mTAUoTNzphduL0SDq5CI1arStFoOJWiGcECQQDpCDMOdkSb+QaRg8zMK1KPMQ2CZEbArjAayz4/oHOwcqqQGWSA0lEn0ktv+1tsC0riMOAHbQtgTHgN8PTL3mKPAkEA1S8lW1YkXSf+TUSMY9Mne9Z35qUyoyn37fsw6tVGEoFMf12KvBGJWH4zCs+GO6gE7rfmrOP7aVsacvch/i2FgQJBAKElUmlFz9wsMSafhhgKPWVX/oeU4HiN/CYLNli5lEcIhHpxlNagmg53lkMyBt6IUJhqRAHenmdRehPp9N6mQnECQE1U0UdQ1AUxg37f6Z+1HzO/nRynknXkNSkHofW3bFxgPXzj8JnFQOQWosqd7RQc/tffZY3RJRrv1fzyfGT06GY=";
	// 支付宝公钥
	// 目前没有用到
	// 支付宝公钥(支付宝（RSA）公钥,用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取；或者文档上也有。 )
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeTY1ONoJ3uyxk94nf8V6IMFjeA0xu9F4osTTgHlqECKrt6wfbhVu+O18NxBVE/eXKd+20AKEn4KCcSWf+CAa88edu2ArKtf98lXBSs3iNLN7JpsPC5LqR58XeQhCpbj+Rr2d1v/YRU/2Gg5ARKjwQwISgCmYs6GCSCqnWA8ZEzwIDAQAB";

}
