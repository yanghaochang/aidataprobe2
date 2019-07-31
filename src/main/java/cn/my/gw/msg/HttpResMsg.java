package cn.my.gw.msg;

import java.io.Serializable;

/**
 * 上行回复 消息体
 * 
 * @author liuchenglei
 */
public class HttpResMsg implements Serializable {
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String header;
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("HttpResReqMsg:\r\n");
		if (header != null) {
			buffer.append(header);
		}
		if (body != null) {
			buffer.append(body);
		}
		return buffer.toString();
	}
}
