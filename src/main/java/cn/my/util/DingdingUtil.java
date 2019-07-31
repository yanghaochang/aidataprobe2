package cn.my.util;

import cn.my.server.MyConfig;
import cn.my.server.SysSwitchConfig;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送钉钉报警工具类
 * 
 * @author huaxuefeng
 *
 */
public class DingdingUtil {
	private static Logger logger = LoggerFactory.getLogger(DingdingUtil.class);
//	private static final Log ERRORMESSAGE = LogFactory.getLog("errorMessage");
//	private static final Log INTP = LogFactory.getLog("intp");
	private final static int CONNECT_TIME_OUT = 350;
	private final static int CONNECT_REQUEST_TIME_OUT = 350;
	private final static int SOCKET_TIME_OUT = 350;

	public static void sendErrorMessage(String json, String message, String url, int lineNum) {
		try {
			SysSwitchConfig switchConfig = new SysSwitchConfig();
			JSONObject switchs = switchConfig.getSwitch();
			if ("1".equals(switchs.getString("dingding_switch"))) {
				Map<String, Object> map = DingdingUtil.getMessage(json);
				map.put("lineNum", lineNum);
				map.put("errorMessage", message);
				// 获取钉钉开关
				DingdingUtil.setMessageToDingDing(JSONObject.toJSONString(map), url);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public static void sendMessage(String json, String url) throws Exception {
		// 获取钉钉开关
		SysSwitchConfig switchConfig = new SysSwitchConfig();
		JSONObject switchs = switchConfig.getSwitch();
		if ("1".equals(switchs.getString("dingding_switch"))) {
			DingdingUtil.setMessageToDingDing(JSONObject.toJSONString(DingdingUtil.getMessage(json)), url);
		}
	}

	private static Map<String, Object> getMessage(String json) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			InetAddress address = InetAddress.getLocalHost();
			map.put("ip", address.getHostAddress());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("time", DateFormat.getDateTimeFormat(DateFormat.DATE_MATCHING));
		map.put("json", json);
		return map;
	}

	public static final String setMessageToDingDing(String content, String ddUrl) throws Exception {
		long startTime = System.currentTimeMillis();
		String result = "";

		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = null;
		try {
			sslcontext = createIgnoreVerifySSL();
		} catch (KeyManagementException | NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage(), e1);
		}

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);
		// 创建自定义的httpclient对象
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager).build();
		// CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		CloseableHttpResponse response = null;
		try {
			Asserts.notBlank(ddUrl, "请求钉钉ddUrl ： " + ddUrl);
			logger.info(ddUrl);
			HttpPost httppost = new HttpPost(ddUrl);
			httppost.addHeader("Content-Type", "application/json; charset=utf-8");
			String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": " + content + "}}";
			StringEntity se = new StringEntity(textMsg, "utf-8");
			httppost.setEntity(se);
			// 设置超时时间
			RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT)
					.setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT).setSocketTimeout(SOCKET_TIME_OUT).build();
			httppost.setConfig(config);
			response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				if (result.indexOf("40035") > 0) {
					DingdingUtil.sendMessage("钉钉发送失败", MyConfig.SYS_SERVICE_MONITOR);
					logger.error("钉钉发送失败,content=" + content);
				}
				return result;
			} else {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				logger.error(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			long time = System.currentTimeMillis() - startTime;
			logger.info("发送钉钉消息，耗时(ms):" + time);
		}
		return result;
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws Exception {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	// public static String setMessageToDingDing(String content, String url) {
	// // 创建Httpclient对象
	// CloseableHttpClient httpClient = createSSLInsecureClient();
	// CloseableHttpResponse response = null;
	// String resultString = "";
	// try {
	// INTP.info("HttpClientUtil>doPostJson[url=" + url + ",json=" + content +
	// "]");
	// // 创建Http Post请求
	// HttpPost httpPost = new HttpPost(url);
	// httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
	// // 创建请求内容
	// String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": " +
	// content + "}}";
	// StringEntity se = new StringEntity(textMsg, "utf-8");
	//
	// httpPost.setEntity(se);
	// //设置超时时间，单位ms
	// RequestConfig requestConfig = RequestConfig.custom()
	// .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT)
	// .setSocketTimeout(SOCKET_TIME_OUT).build();
	// httpPost.setConfig(requestConfig);
	// // 执行http请求
	// response = httpClient.execute(httpPost);
	//
	// //调用接口状态不为200的就走钉钉报警
	// if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	// INTP.error(String.format("POST接口异常，请求返回状态为：【%s】",
	// response.getStatusLine().getStatusCode()));
	// } else {
	// resultString = EntityUtils.toString(response.getEntity(), "utf-8");
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// INTP.error(e.getMessage());
	// throw new RuntimeException(e.getMessage());
	// } finally {
	// try {
	// if (response != null) {
	// response.close();
	// }
	// if (httpClient != null) {
	// httpClient.close();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// INTP.error(e.getMessage());
	// throw new RuntimeException(e.getMessage());
	// }
	// }
	//
	// return resultString;
	// }
	//
	// /**
	// * 创建一个SSL信任所有证书的httpClient对象
	// *
	// * @return
	// */
	// private static CloseableHttpClient createSSLInsecureClient() {
	// try {
	// SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
	// new TrustStrategy() {
	// // 默认信任所有证书
	// @Override
	// public boolean isTrusted(X509Certificate[] arg0, String arg1) throws
	// CertificateException {
	// return true;
	// }
	// }).build();
	// // AllowAllHostnameVerifier: 这种方式不对主机名进行验证，验证功能被关闭，是个空操作(域名验证)
	// SSLConnectionSocketFactory sslcsf = new
	// SSLConnectionSocketFactory(sslContext,
	// SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	// return HttpClients.custom().setSSLSocketFactory(sslcsf).build();
	// } catch (Exception e) {
	// INTP.error(e.getMessage(), e);
	// }
	// return HttpClients.createDefault();
	// }
}