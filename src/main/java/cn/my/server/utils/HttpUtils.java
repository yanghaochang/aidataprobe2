package cn.my.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtils {
	public static StringBuilder send(String key,double lng,double lat) throws IOException{
		StringBuilder resultData = new StringBuilder();
		// 秘钥换成你申请的秘钥，到百度API申请ak值（即秘钥）
		//String url = "http://api.map.baidu.com/geocoder/v2/?ak=6XXr95iExPP41l0Khl4i9Mcqh5gcsKTA=" + lat + ","+ lng + "&output=json&pois=1";
		
		String url = "https://restapi.amap.com/v3/geocode/regeo?key="+key+"&location=" + lng + ","+ lat + "&output=JSON&radius=1000&extensions=all";
		URL myURL = null;
		URLConnection httpsConn = null;
		
		try {
		myURL = new URL(url);
		} catch (MalformedURLException e) {
		e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
		httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
		if (httpsConn != null) {
		insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
		br = new BufferedReader(insr);
		String data = null;
		while ((data = br.readLine()) != null) {
		resultData.append(data);
		}
		}
		} catch (IOException e) {
		e.printStackTrace();
		} finally {
		if (insr != null) {
		insr.close();
		}
		if (br != null) {
		br.close();
		}
		}
		return resultData;
	}
}
