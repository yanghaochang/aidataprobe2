package cn.my.gw.msg;

import java.io.Serializable;

/**
 * 定义消息头
 * 
 * @author liuchenglei
 * 
 */
public abstract class MsgHead implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7081045222401336572L;

	public final static int LEN_HEAD = 24; // 消息头长度

	private int msgLength; // 消息长度 4 byte
	public int version = 0x30322E30; // 版本号 4 byte
	private int sequence; // 序列号 4 byte
	private int requestModule; // 请求模块 4 byte
	private int serviceModule; // 服务模块 4 byte
	private int operCmd; // 操作命令字 4 byte

	public MsgHead() {

	}

	public MsgHead(int msgLenth, int operCmd) {
		this.msgLength = LEN_HEAD + msgLenth;
		this.operCmd = operCmd;
	}

	public int getMsgLength() {
		return msgLength;
	}

	public int getOperCmd() {
		return operCmd;
	}

	public int getRequestModule() {
		return requestModule;
	}

	public void setRequestModule(int requestModule) {
		this.requestModule = requestModule;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getServiceModule() {
		return serviceModule;
	}

	public void setServiceModule(int serviceModule) {
		this.serviceModule = serviceModule;
	}

	public void setMsgLength(int msgLenth) {
		this.msgLength = msgLenth;
	}

	public void setOperCmd(int operCmd) {
		this.operCmd = operCmd;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(" msgLength:" + msgLength);
		buf.append(" version:0x" + Integer.toHexString(0x30322E30));
		buf.append(" sequence:" + sequence);
		buf.append(" requestModule:0x" + Integer.toHexString(requestModule));
		buf.append(" serviceModule:0x" + Integer.toHexString(serviceModule));
		buf.append(" operCmd:0x" + Integer.toHexString(operCmd));
		return buf.toString();
	}
}
