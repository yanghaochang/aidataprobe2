package cn.my.gw.msg;

/**
 * 方法调用的结果响应消息
 */
public class MethodCallRetMsg extends MsgHead {
	/*
	 * 
	 */
	private static final long serialVersionUID = -8755880012908146097L;

	/*
	 * 命令字
	 */
	public static final int OPER_CMD = 0x1000000A;

	/*
	 * 消息体长度
	 */
	public static final int LEN_BODY = 8 + 8164 * 5;

	/*
	 * 前端session标识
	 */
	private long sessionID;
	/*
	 * 调用第三方结果的响应数据
	 */
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public MethodCallRetMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public String toString() {
		return "MethodCallRetMsg:" + super.toString() + " sessionID:" + sessionID + " result:" + result;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long session) {
		this.sessionID = session;
	}
}
