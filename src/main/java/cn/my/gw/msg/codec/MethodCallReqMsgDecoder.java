package cn.my.gw.msg.codec;

import cn.my.gw.msg.MethodCallReqMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MethodCallReqMsgDecoder<T extends MsgHead> extends MsgHeadDecoder<T> {
	public MethodCallReqMsgDecoder() {
		super(MethodCallReqMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < MethodCallReqMsg.LEN_BODY) {
			return null;
		}

		MethodCallReqMsg methodCallReqMsg = new MethodCallReqMsg();
		byte[] by;

		methodCallReqMsg.setSessionID(buf.getLong());

		by = new byte[40];
		buf.get(by);
		methodCallReqMsg.setMethodName(byteTOString(by));

		by = new byte[1024];
		buf.get(by);
		methodCallReqMsg.setAppParams(byteTOString(by));

		by = new byte[100];
		buf.get(by);
		methodCallReqMsg.setSysParams(byteTOString(by));

		methodCallReqMsg.setBusiType(buf.getLong());

		by = new byte[30];
		buf.get(by);
		methodCallReqMsg.setTime(byteTOString(by));

		return methodCallReqMsg;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}

}
