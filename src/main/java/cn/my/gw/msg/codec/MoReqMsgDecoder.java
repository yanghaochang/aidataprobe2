package cn.my.gw.msg.codec;

import cn.my.gw.msg.MoReqMsg;
import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MoReqMsgDecoder<T extends MsgHead> extends MsgHeadDecoder<T> {
	public MoReqMsgDecoder() {
		super(MoReqMsg.OPER_CMD);
	}

	protected MsgHead decodeBody(IoSession session, IoBuffer buf) throws Exception {
		if (buf.remaining() < MoReqMsg.LEN_BODY) {
			return null;
		}

		byte[] dest;

		MoReqMsg mo = new MoReqMsg();

		mo.setMsgID(buf.getInt());

		dest = new byte[21];
		buf.get(dest);
		mo.setSrcTerminalID(byteTOString(dest));

		dest = new byte[21];
		buf.get(dest);
		mo.setDestTerminalID(byteTOString(dest));

		dest = new byte[127];
		buf.get(dest);
		mo.setMsgContent(byteTOString(dest));

		mo.setContentLength(buf.get());

		dest = new byte[20];
		buf.get(dest);
		mo.setKeyword(byteTOString(dest));

		dest = new byte[30];
		buf.get(dest);
		mo.setSendTime(byteTOString(dest));

		mo.setSpID(buf.getInt());
		mo.setGateWayID(buf.getInt());
		mo.setMoProcessType(buf.getInt());

		dest = new byte[20];
		buf.get(dest);
		mo.setLinkID(byteTOString(dest));

		dest = new byte[20];
		buf.get(dest);
		mo.setBusinessType(byteTOString(dest));
		return mo;
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
	}
}
