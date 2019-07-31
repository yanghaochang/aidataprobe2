package cn.my.gw.msg;

public class LoginResMsg extends MsgHead {

	/*
	 * 登录响应消息0为成功 非0为失败
	 */
	private static final long serialVersionUID = 1L;

	public static final int OPER_CMD = 0x10000004; // 操作命令字
	public static final int LEN_BODY = 4; // 消息体长度

	private int status;

	public LoginResMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("LoginResMsg:");
		buffer.append(super.toString());
		buffer.append(" status:" + status);
		return buffer.toString();
	}
}
