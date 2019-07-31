package cn.my.gw.msg.codec;

import cn.my.gw.msg.MoResMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MoResMsgDecoder<T extends MsgHead> extends MsgHeadDecoder<T> {
	public MoResMsgDecoder() {
		super(MoResMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < MoResMsg.LEN_BODY) {
			return null;
		}
		MoResMsg message = new MoResMsg();
		message.setStatus(buf.getInt());
		return message;
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}
}
