package cn.my.gw.msg;

public class HeartBeatReqMsg extends MsgHead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int OPER_CMD = 0x10000001;

	public HeartBeatReqMsg() {
		super(0, OPER_CMD);
	}

	public String toString() {
		return "HeartBeatReqMsg: " + super.toString();
	}
}
