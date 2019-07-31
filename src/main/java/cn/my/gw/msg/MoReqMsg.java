package cn.my.gw.msg;

/**
 * @author liuchenglei
 */
public class MoReqMsg extends MsgHead {
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 命令字
	 */
	public static final int OPER_CMD = 0x10000005;
	/*
	 * 消息体总长度
	 */
	public static final int LEN_BODY = 276;

	/*
	 * 消息ID 4byte
	 */
	private int msgID;

	/*
	 * 源地址 21byte 字符串
	 */
	private String srcTerminalID;

	/*
	 * 目的地址 21byte 字符串
	 */
	private String destTerminalID;

	/*
	 * 消息内容 127byte 字符串
	 */
	private String msgContent;

	/*
	 * 上行内容长度 1byte整形数字
	 */
	private byte contentLength;

	/*
	 * 消息内容 20byte 字符串
	 */
	private String keyword;

	/*
	 * 用户上行时间 30byte 字符串
	 */
	private String sendTime;

	/*
	 * sp id 4byte 字符串
	 */
	private int spID;

	/*
	 * 网关id 4byte 字符串
	 */
	private int gateWayID;

	/*
	 * mo处理类型 4byte 字符串
	 */
	private int moProcessType;

	/*
	 * 20byte定长字符串
	 */
	private String linkID;

	/*
	 * 20byte定长字符串
	 */
	private String businessType;

	public MoReqMsg() {
		super(LEN_BODY, OPER_CMD);
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public byte getContentLength() {
		return contentLength;
	}

	public void setContentLength(byte contentLength) {
		this.contentLength = contentLength;
	}

	public String getDestTerminalID() {
		return destTerminalID;
	}

	public void setDestTerminalID(String destTerminalID) {
		this.destTerminalID = destTerminalID;
	}

	public int getGateWayID() {
		return gateWayID;
	}

	public void setGateWayID(int gateWayID) {
		this.gateWayID = gateWayID;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLinkID() {
		return linkID;
	}

	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}

	public int getMoProcessType() {
		return moProcessType;
	}

	public void setMoProcessType(int moProcessType) {
		this.moProcessType = moProcessType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public int getMsgID() {
		return msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public int getSpID() {
		return spID;
	}

	public void setSpID(int spID) {
		this.spID = spID;
	}

	public String getSrcTerminalID() {
		return srcTerminalID;
	}

	public void setSrcTerminalID(String srcTerminalID) {
		this.srcTerminalID = srcTerminalID;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("MoReqMsg:");
		buffer.append(super.toString());
		buffer.append(" msgID:" + msgID);
		buffer.append(" srcTerminalID:" + srcTerminalID);
		buffer.append(" destTerminalID:" + destTerminalID);
		buffer.append(" keyword:" + keyword);
		buffer.append(" moProcessType:" + moProcessType);
		buffer.append(" businessType:" + businessType);
		buffer.append(" gateWayID:" + gateWayID);
		buffer.append(" spID:" + spID);
		buffer.append(" linkID:" + linkID);
		buffer.append(" sendTime:" + sendTime);
		buffer.append(" contentLength:" + contentLength);
		buffer.append(" msgContent:" + msgContent);

		return buffer.toString();
	}
}
