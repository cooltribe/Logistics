package com.seeyuan.logistics.net.http;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.os.Process;

/**
 * 
 * HttpManager.java
 * 
 * @author 査赵保
 */
public class HttpManager implements Runnable {
	private static final Integer SOCKET_TIMEOUT = 60000;
	private static final Integer CONNECTION_TIMEOUT = 60000;

	private static HttpManager inst;

	private DefaultHttpClient mClient;
	private BlockingQueue<HttpAction> mHttpActions;
	
	/**
	 * 获取Http管理器单例 
	 * 
	 * @return Http管理器单例
	 */
	public synchronized static HttpManager getInstance() {
		if (inst == null) {
			inst = new HttpManager();
		}
		return inst;
	}

	private HttpManager() {
		mHttpActions = new LinkedBlockingQueue<HttpAction>();
		mClient = generateDefaultHttpClient();
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * 添加Http事件
	 * 
	 * @param httpAction
	 *            Http事件
	 */
	public void add(HttpAction httpAction) {
		mHttpActions.add(httpAction);
	}

	/**
	 * 设置认证信息
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public void setCredentials(String username, String password) {
		Credentials defaultcreds = new UsernamePasswordCredentials(username,
				password);
		String host = AuthScope.ANY_HOST;
		BasicCredentialsProvider cP = new BasicCredentialsProvider();
		cP.setCredentials(new AuthScope(host, AuthScope.ANY_PORT,
				AuthScope.ANY_REALM), defaultcreds);
		mClient.setCredentialsProvider(cP);
	}

	/**
	 * 获取认证管理
	 * 
	 * @return 认证管理器
	 */
	public CredentialsProvider getCredentials() {
		return mClient.getCredentialsProvider();
	}

	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		try {
			while (true) {
				HttpAction currentNetAct = (HttpAction) mHttpActions.take();
				currentNetAct.execute(mClient);
			}
		} catch (InterruptedException e) {

		}
	}

	/**
	 * 生成请求客户端实例
	 * 
	 * @return 请求客户端
	 */
	public static DefaultHttpClient generateDefaultHttpClient() {
		HttpParams params = getHttpParams();
		SchemeRegistry schemeRegistry = getSchemeRegistry();
		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				params, schemeRegistry);
		// 添加多线程安全管理
		// return new DefaultHttpClient(params);
		return new DefaultHttpClient(manager, params);
	}

	private static SchemeRegistry getSchemeRegistry() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		return schemeRegistry;
	}

	private static HttpParams getHttpParams() {
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		//添加持续握手功能
		HttpProtocolParams.setUseExpectContinue(params, true);
		// HttpConnectionParams.setStaleCheckingEnabled(params, true);
		HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		return params;
	}

	public void printCurrentUserName(String TAG) {
		String userName = HttpManager
				.getInstance()
				.getCredentials()
				.getCredentials(
						new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,
								AuthScope.ANY_REALM)).getUserPrincipal()
				.getName();
		// LogUtil.print(TAG, "现在httpManager的用户名:" + userName);
	}
}