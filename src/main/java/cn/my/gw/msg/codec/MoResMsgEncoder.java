package cn.my.gw.msg.codec;

import cn.my.gw.msg.MoResMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class MoResMsgEncoder<T extends MoResMsg> extends MsgHeadEncoder<T> {
	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		buf.putInt(msg.getStatus());
	}

	public void dispose() throws Exception {

	}
}
