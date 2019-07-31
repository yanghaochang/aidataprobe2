package cn.my.gw.msg.codec;

import cn.my.gw.msg.MethodCallRetMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class MethodCallRetMsgEncoder<T extends MethodCallRetMsg> extends MsgHeadEncoder<T> {
	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		byte[] src;
		byte[] dest;

		buf.putLong(msg.getSessionID());

		dest = new byte[8164 * 5];
		src = msg.getResult().getBytes("UTF-8");
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);
	}
}
