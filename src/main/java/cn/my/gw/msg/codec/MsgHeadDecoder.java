package cn.my.gw.msg.codec;

import cn.my.gw.msg.MsgHead;
import cn.my.gw.msg.NotMatchMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public abstract class MsgHeadDecoder<T extends MsgHead> implements MessageDecoder {
	private final int cmd;
	private boolean isDefault = false;

	public MsgHeadDecoder(int cmd) {
		this.cmd = cmd;
	}

	public MsgHeadDecoder(int cmd, boolean isDefault) {
		this.cmd = cmd;
		this.isDefault = isDefault;
	}

	public MessageDecoderResult decodable(IoSession session, IoBuffer buf) {
		/*
		 * byte[] by = buf.array( ) ; for ( int i=0; i<buf.limit( ); i++ ) {
		 * byte b = by[i]; System.out.print( Integer.toHexString(b&0xff)+" "); }
		 * System.out.println( );
		 */
		if (buf.remaining() < 4) {
			return MessageDecoderResult.NEED_DATA;
		}

		int len = buf.getInt();
		if (buf.remaining() < len - 4) {
			return MessageDecoderResult.NEED_DATA;
		}
		buf.getInt();
		buf.getInt();
		buf.getInt();
		buf.getInt();
		if (cmd == buf.getInt()) {
			return MessageDecoderResult.OK;
		}

		if (isDefault) {
			return MessageDecoderResult.OK;
		}

		return MessageDecoderResult.NOT_OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		int msgLength = buf.getInt(); // 跳过 length
		session.setAttribute("msgLength", msgLength);
		int version = buf.getInt(); // 跳过 version
		int sequence = buf.getInt();
		int requestModule = buf.getInt();
		int serviceModule = buf.getInt();
		int cmd = buf.getInt(); // 跳过此消息的命令字

		IoBuffer newbuf = IoBuffer.allocate(msgLength - 24);
		byte[] by = new byte[msgLength - 24];
		buf.get(by);
		newbuf.put(by);
		newbuf.flip();

		MsgHead msg = decodeBody(session, newbuf);
		if (msg == null) {
			NotMatchMsg notMsg = new NotMatchMsg();
			notMsg.setBy(by);
			msg = notMsg;
		}
		msg.setMsgLength(msgLength);
		msg.setVersion(version);
		msg.setSequence(sequence);
		msg.setRequestModule(requestModule);
		msg.setServiceModule(serviceModule);
		msg.setOperCmd(cmd);
		out.write(msg);
		return MessageDecoderResult.OK;
	}

	public String byteTOString(byte[] src) {
		int i = 0;
		for (i = 0; i < src.length; i++) {
			if (src[i] == (byte) 0) {
				break;
			}
		}
		try {
			return new String(src, 0, i);
		} catch (Exception e) {
			e.printStackTrace();
			return new String(src, 0, i);
		}
	}

	public String byteTOUTF8String(byte[] src) {
		int i = 0;
		for (i = 0; i < src.length; i++) {
			if (src[i] == (byte) 0) {
				break;
			}
		}
		try {
			return new String(src, 0, i, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return new String(src, 0, i);
		}
	}

	public String byteTOGBKString(byte[] src) {
		int i = 0;
		for (i = 0; i < src.length; i++) {
			if (src[i] == (byte) 0) {
				break;
			}
		}
		try {
			return new String(src, 0, i, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			return new String(src, 0, i);
		}

	}

	protected abstract MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception;
}
