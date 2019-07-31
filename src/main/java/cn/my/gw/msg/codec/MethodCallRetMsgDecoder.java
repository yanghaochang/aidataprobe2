package cn.my.gw.msg.codec;

import cn.my.gw.msg.MethodCallRetMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MethodCallRetMsgDecoder<T extends MsgHead> extends MsgHeadDecoder<T> {
	public MethodCallRetMsgDecoder() {
		super(MethodCallRetMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < MethodCallRetMsg.LEN_BODY) {
			return null;
		}
		MethodCallRetMsg methodCallRetMsg = new MethodCallRetMsg();
		byte[] by;

		methodCallRetMsg.setSessionID(buf.getLong());

		by = new byte[8164 * 5];
		buf.get(by);
		methodCallRetMsg.setResult(byteTOString(by));
		return methodCallRetMsg;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}
}
