package cn.my.gw.msg;

public class HeartBeatResMsg extends MsgHead {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int OPER_CMD = 0x10000002;

	public HeartBeatResMsg() {
		super(0, OPER_CMD);
	}

	public String toString() {
		return "HeartBeatResMsg: " + super.toString();
	}
}
