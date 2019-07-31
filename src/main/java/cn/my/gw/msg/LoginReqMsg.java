package cn.my.gw.msg;

public class LoginReqMsg extends MsgHead {
	/**
	 * 客户端登录发送的消息
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public final static int OPER_CMD = 0x10000003;
	public final static int LEN_USERNAME = 10;
	public final static int LEN_PASSWORD = 16;
	public final static int LEN_BODY = LEN_USERNAME + LEN_PASSWORD;

	public LoginReqMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("LoginReqMsg:");
		buffer.append(super.toString());
		buffer.append(" usesname:" + username);
		buffer.append(" password:" + password);

		return buffer.toString();
	}

}
