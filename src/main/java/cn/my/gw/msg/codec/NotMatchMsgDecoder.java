package cn.my.gw.msg.codec;

import cn.my.gw.msg.MsgHead;
import cn.my.gw.msg.NotMatchMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class NotMatchMsgDecoder extends MsgHeadDecoder {

	public NotMatchMsgDecoder() {
		super(0, true);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		int msgLength = Integer.parseInt(session.getAttribute("msgLength").toString());
		if (buf.remaining() < msgLength - 24) {
			return null;
		}

		NotMatchMsg notMatchMsg = new NotMatchMsg();
		if (msgLength > 24) {
			byte[] ba = new byte[msgLength - 24];
			buf.get(ba);
			notMatchMsg.setBy(ba);
		}
		return notMatchMsg;
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}

}
