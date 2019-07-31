package cn.my.gw.msg;

/**
 * 方法调用的异步响应消息
 */
public class MethodCallResMsg extends MsgHead {
	/*
	 * 
	 */
	private static final long serialVersionUID = -7183020277030110421L;

	/*
	 * 命令字
	 */
	public static final int OPER_CMD = 0x10000009;

	/*
	 * 消息体长度
	 */
	public static final int LEN_BODY = 4;

	/*
	 * 消息返回码 0成功 非0失败 4byte
	 */
	private int status;

	public MethodCallResMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		return "MethodCallResMsg:" + super.toString() + " status:" + status;
	}
}
