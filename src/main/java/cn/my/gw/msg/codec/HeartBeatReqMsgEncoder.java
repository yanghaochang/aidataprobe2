package cn.my.gw.msg.codec;

import cn.my.gw.msg.HeartBeatReqMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class HeartBeatReqMsgEncoder<T extends HeartBeatReqMsg> extends MsgHeadEncoder<T> {
	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		// System.out.println( HeartBeatReqMsgEncoder.class+" encodeBody called"
		// );
	}

	public void dispose() throws Exception {
	}
}