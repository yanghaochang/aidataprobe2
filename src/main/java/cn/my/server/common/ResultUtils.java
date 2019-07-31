package cn.my.server.common;

import java.util.HashMap;

public class ResultUtils {
	public static final String SUCCSESS = "{\"code\": \"200\",\"message\": \"成功\"}";
	public static final String ERRORS = "{\"code\": \"500\",\"message\": \"失败\"}";
	public static final String HTTP = "HTTP/1.1 200 ok\r\nAccess-Control-Allow-Origin: *\r\n";
	
	public static void main(String[] args) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("event_code", "login_01");
		map.put("event_name", "首页红包显示");
		System.out.println(12);

	}
	

}
