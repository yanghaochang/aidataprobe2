package cn.my.gw.msg;

import java.io.Serializable;

/**
 * @author liuchenglei
 */
public class HttpReqMsg implements Serializable {
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
		buffer.append("HttpReqMsg:\r\n");
		buffer.append(header);
		buffer.append("\r\n\r\n" + body);
		return buffer.toString();
	}
}
