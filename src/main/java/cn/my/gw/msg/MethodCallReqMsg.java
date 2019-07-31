package cn.my.gw.msg;

/**
 * 方法调用请求消息
 */
public class MethodCallReqMsg extends MsgHead {
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 命令字
	 */
	public static final int OPER_CMD = 0x10000008;
	/*
	 * 消息体总长度 4+40+500+100+8+30=682
	 */
	public static final int LEN_BODY = 8 + 40 + 1024 * 1024 + 100 + 8 + 30;

	// 前端保持的连接ID
	private long sessionID;

	// 调用的方法名称
	private String methodName;

	// 调用的应用级参数列表
	private String appParams;

	// 调用的系统级参数列表
	private String sysParams;

	// 业务类型
	private long busiType;

	// 接收时间
	private String time;

	public String getAppParams() {
		return appParams;
	}

	public void setAppParams(String appParams) {
		this.appParams = appParams;
	}

	public long getBusiType() {
		return busiType;
	}

	public void setBusiType(long busiType) {
		this.busiType = busiType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public String getSysParams() {
		return sysParams;
	}

	public void setSysParams(String sysParams) {
		this.sysParams = sysParams;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public MethodCallReqMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("MethodCallReqMsg:");
		buffer.append(super.toString());
		buffer.append(" sessionID:" + sessionID);
		buffer.append(" methodName:" + methodName);
		buffer.append(" appParams:" + appParams);
		buffer.append(" sysParams:" + sysParams);
		buffer.append(" busiType:" + busiType);
		buffer.append(" time:" + time);

		return buffer.toString();
	}
}
