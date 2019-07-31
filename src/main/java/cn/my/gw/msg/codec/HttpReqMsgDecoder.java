package cn.my.gw.msg.codec;

import cn.my.gw.msg.HttpReqMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpReqMsgDecoder implements MessageDecoder {
	private byte[] headerBytes;
	private byte[] bodyBytes;
	private int contentLength;

	public MessageDecoderResult decodable(IoSession session, IoBuffer buf) {
		if (buf.remaining() < 20) {
			return MessageDecoderResult.NEED_DATA;
		}
		int position = -1;
		byte[] arrays = buf.array();
		for (int i = 0; i + 3 < buf.limit(); i++) {
			if (arrays[i] == 13 && arrays[i + 1] == 10 && arrays[i + 2] == 13 && arrays[i + 3] == 10) {
				position = i;
				break;
			}
		}

		if (position == -1) {
			return MessageDecoderResult.NEED_DATA;
		}

		headerBytes = new byte[position];
		System.arraycopy(buf.array(), 0, headerBytes, 0, headerBytes.length);

		Pattern pattern = Pattern.compile("((?s).*)((?i)content-length:.*)+(?s).*$");
		Matcher m = pattern.matcher(new String(headerBytes));

		if (m.matches()) {
			String bodyLength = m.group(2).split(":")[1].trim();
			if (null != bodyLength && bodyLength.matches("\\d+")) {
				contentLength = Integer.parseInt(bodyLength);
			}
		}

		/*
		 * if( contentLength == 0 ) { System.out.println( "hahaha" ); }
		 */

		if (buf.remaining() < contentLength) {
			return MessageDecoderResult.NEED_DATA;
		}

		bodyBytes = new byte[buf.limit() - (position + 4)];
		System.arraycopy(buf.array(), position + 4, bodyBytes, 0, bodyBytes.length);
		if (contentLength == bodyBytes.length) {
			return MessageDecoderResult.OK;
		}
		return MessageDecoderResult.NOT_OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		buf.skip(headerBytes.length + 4);
		if (contentLength != 0) {
			buf.get(new byte[buf.remaining()]);
		}
		HttpReqMsg reqMsg = new HttpReqMsg();
		reqMsg.setHeader(new String(headerBytes, "UTF-8"));
		reqMsg.setBody(new String(bodyBytes, "UTF-8"));
		out.write(reqMsg);
		return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}
}
