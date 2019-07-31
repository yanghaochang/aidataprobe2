package cn.my.gw.msg.codec;

import cn.my.gw.msg.LoginResMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class LoginResMsgDecoder extends MsgHeadDecoder {

	public LoginResMsgDecoder() {
		super(LoginResMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < LoginResMsg.LEN_BODY) {
			return null;
		}
		LoginResMsg res = new LoginResMsg();
		res.setStatus(buf.getInt());
		return res;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}
}
