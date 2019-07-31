package cn.my.server.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface SdkConfService {
	
	public  HashMap<String,Object>  sdkConfData(String event_code);
	
	public ArrayList<HashMap<String, Object>> sdkAllConfData() ;

}
