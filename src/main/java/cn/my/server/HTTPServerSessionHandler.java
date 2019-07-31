package cn.my.server;

import cn.my.gw.msg.HttpReqMsg;
import cn.my.gw.msg.HttpResMsg;
import cn.my.gw.msg.NotMatchMsg;
import cn.my.server.ServicePackaging.AdapterPackaging;
import cn.my.server.common.EventType;
import cn.my.server.common.ResultUtils;
import cn.my.server.dao.SendKafkaUtils;
import cn.my.util.DateFormat;
import cn.my.util.DingdingUtil;
import cn.my.util.PropertiesUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuchenglei
 */
public class HTTPServerSessionHandler extends IoHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(HTTPServerSessionHandler.class);
//	private static final Log INTP = LogFactory.getLog("intp");
//	private static final Log SYS = LogFactory.getLog("sys");
//	private static final Log BODYMESSAGE = LogFactory.getLog("bodyMessage");
//	private static final Log ERRORMESSAGE = LogFactory.getLog("errorMessage");

	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	public void sessionOpened(IoSession session) {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
	}

	public void messageSent(IoSession session, Object msg) throws Exception {
		super.messageSent(session, msg);
	}

	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	public void messageReceived(IoSession session, Object msg) throws Exception {
		long startTime = System.currentTimeMillis();
		String json = null;
		try {
			if (msg instanceof NotMatchMsg) {
//				NotMatchMsg totMatchMsg = (NotMatchMsg) msg;
				logger.info(msg.toString());
			} else if (msg instanceof HttpReqMsg) {
				try {
					HttpReqMsg reqMsg = (HttpReqMsg) msg;
					json = reqMsg.getBody();
					String now = DateFormat.getDateTimeFormat(DateFormat.DATE_MATCHING);
					String messages = now + "\007" + json;
//					logger.info(messages);
					JSONObject requestObj = null;
					try{
						logger.info(json);
						requestObj = JSONObject.parseObject(json);
					}catch(Exception e){
						throw new IllegalStateException("messageReceived Exception:json应为JSON格式");
					}
					if (requestObj==null||requestObj.isEmpty()) {
						throw new IllegalStateException("messageReceived Exception:json is blank!");
					}
					
					HttpResMsg resMsg = new HttpResMsg();
					resMsg.setHeader(ResultUtils.HTTP);
					String responseStr = ResultUtils.ERRORS;
					if (StringUtils.isNotBlank(json)) {
						JSONObject common = requestObj.getJSONObject("common");
						try {
							if (common != null) {
								if ("-999999".equals(requestObj.getString("message_id"))) {
									PropertiesUtil.reviseProperties();
									responseStr = ResultUtils.SUCCSESS;
								} else {
									responseStr = SendKafkaUtils.toKafka(EventType.topic + common.getString("product_name"), requestObj.toString());
								}
							} else {
								logger.error("requestObj.getJSONObject(common) is null:" + json);
							}
						}catch (Exception ee){
							logger.error(ee.getMessage()+json);
						}
					}
					resMsg.setBody(responseStr);
					session.write(resMsg);
					session.closeOnFlush();
				} catch (Exception e) {
					logger.error("messageReceived error;"+json, e);
				}finally{
					session.closeOnFlush();
				}
			}
			} catch (Exception e) {
			logger.error("messageReceived error;json=" + json, e);
				DingdingUtil.sendErrorMessage(json, e.toString(), MyConfig.SYS_ERROR_MONITOR,
						e.getStackTrace()[0].getLineNumber());
			}
			this.checkServiceTime(startTime);
	}

	 private void checkServiceTime(long startTime) throws Exception {
		  long time = System.currentTimeMillis() - startTime;
		  if (time > MyConfig.SERVICE_TIME_THRESHOLD) {
			   String content = "数据埋点总耗时毫秒(单位ms)：" + MyConfig.SERVICE_TIME_THRESHOLD + ",使用时间(单位ms)：" + time;
			  logger.info(content);
		  }
		 logger.info("数据埋点总耗时毫秒(单位ms)：" + time);
		 }

	/**
	 * 
	 * @param json
	 * @return
	 * @Description: 埋点数据入库，获得返回的字符串
	 * @author huaxuefeng
	 * 
	 */
	private String getResponseStr(String json, IoSession session) throws Exception {
		long startTime = System.currentTimeMillis();
		String MESSAGE_ID = "";
		boolean flag = false;
		try {
			JSONObject requestObj = null;
			try{
				requestObj = JSONObject.parseObject(json);
			}catch(Exception e){
				throw new IllegalStateException("getResponseStr Exception:json应为JSON格式");
			}
			
			MESSAGE_ID = requestObj.getString("message_id");

//			Asserts.notBlank(MESSAGE_ID, "getResponseStr Exception:message_id param");
			if ("-999999".equals(MESSAGE_ID)) {
					PropertiesUtil.reviseProperties();
					return ResultUtils.SUCCSESS;
			}
			
			String now = DateFormat.getDateTimeFormat(DateFormat.DATE_MATCHING);
			
			JSONObject a = new JSONObject();
			
			String event_code = requestObj.getString("event_code");
			String curr_page = requestObj.getString("curr_page");
			if("F0000".equals(curr_page)&&StringUtils.isBlank(event_code)){
				requestObj.put("event_code", "login00");
			}
			
			String messages = now + "\007" + requestObj;
			return AdapterPackaging.adapter(messages, requestObj,now);
		} catch (Exception e) {
			logger.error(
					"getResponseStr error，耗时(单位ms)：" + (System.currentTimeMillis() - startTime) + ";json=" + json, e);
			DingdingUtil.sendErrorMessage("getResponseStr error", e.toString(), MyConfig.SYS_ERROR_MONITOR,
					e.getStackTrace()[0].getLineNumber());
			return  ResultUtils.ERRORS;
		}
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

		if (session != null) {
			session.closeOnFlush();
		}
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (session != null) {
			session.closeOnFlush();
		}
		logger.error("exception", cause);
	}

}
