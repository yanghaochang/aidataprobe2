package cn.my.server.ServicePackaging;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.my.server.common.ResultUtils;
import cn.my.server.common.ServerType;
import cn.my.server.service.SendData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdapterPackaging {
	private static Logger logger = LoggerFactory.getLogger(AdapterPackaging.class);
	private static String  sdk_switch;
	private static String message_id;

	public static String adapter(String messages,JSONObject requestObj,String now)throws Exception{
		try{
//				logger.info(requestObj.toJSONString());
				JSONObject common = requestObj.getJSONObject("common");
				if (common == null || common.isEmpty()) {
					String str = "Exception:common is blank!requestObj:"+requestObj;
					logger.error(str);
				}
				JSONArray events = requestObj.getJSONArray("events");
				if (events == null || events.isEmpty()) {
					String str = "Exception:events is blank!requestObj:"+requestObj;
					logger.error(str);
				}
				message_id = common.getString("message_id");
				if(ServerType.message_id_report.equals(message_id)){
					SendData.hdfsData(requestObj,now);//发送topic，并写相应时间的日志开发
					return ResultUtils.SUCCSESS;
				}else{
					String str = "Exception:message_id is error!requestObj:"+requestObj;
					logger.error(str);
				}

			return ResultUtils.ERRORS;
		}catch(Exception e){
			String str = "Exception:adapter is error!requestObj:"+requestObj;
			logger.error(str,e);
			return null;
		}

	}


}
