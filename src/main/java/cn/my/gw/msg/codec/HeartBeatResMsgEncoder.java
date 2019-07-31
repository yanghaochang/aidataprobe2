package cn.my.gw.msg.codec;

import cn.my.gw.msg.HeartBeatResMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class HeartBeatResMsgEncoder<T extends HeartBeatResMsg> extends MsgHeadEncoder<T> {
	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		// System.out.println( HeartBeatResMsg.class+" encodeBody called" );
	}

	public void dispose() throws Exception {
	}
}
