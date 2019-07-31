package cn.my.gw.msg;

/**
 * 上行回复 消息体
 * 
 * @author liuchenglei
 */
public class MoResMsg extends MsgHead {
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 命令字
	 */
	public static final int OPER_CMD = 0x10000006;

	/*
	 * 消息体长度
	 */
	public static final int LEN_BODY = 4;

	/*
	 * 消息返回码 0成功 非0失败 4byte
	 */
	private int status;

	public MoResMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		return "MoResMsg:" + super.toString() + " status:" + status;
	}
}
