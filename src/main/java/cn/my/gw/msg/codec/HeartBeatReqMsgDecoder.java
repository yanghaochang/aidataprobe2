package cn.my.gw.msg.codec;

import cn.my.gw.msg.HeartBeatReqMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class HeartBeatReqMsgDecoder extends MsgHeadDecoder {

	public HeartBeatReqMsgDecoder() {
		super(HeartBeatReqMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		return new HeartBeatReqMsg();
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}
}
