package cn.my.gw.msg.codec;

import cn.my.gw.msg.MethodCallResMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MethodCallResMsgDecoder<T extends MsgHead> extends MsgHeadDecoder<T> {
	public MethodCallResMsgDecoder() {
		super(MethodCallResMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < MethodCallResMsg.LEN_BODY) {
			return null;
		}
		MethodCallResMsg res = new MethodCallResMsg();
		res.setStatus(buf.getInt());
		return res;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

	}
}
