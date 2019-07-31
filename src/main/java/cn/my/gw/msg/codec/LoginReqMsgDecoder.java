package cn.my.gw.msg.codec;

import cn.my.gw.msg.LoginReqMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class LoginReqMsgDecoder<T extends LoginReqMsg> extends MsgHeadDecoder {

	public LoginReqMsgDecoder() {
		super(LoginReqMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < LoginReqMsg.LEN_BODY) {
			return null;
		}
		LoginReqMsg req = new LoginReqMsg();
		byte[] by;

		by = new byte[10];
		buf.get(by);
		req.setUsername(byteTOUTF8String(by));

		by = new byte[16];
		buf.get(by);
		req.setPassword(byteTOUTF8String(by));

		return req;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}
}
