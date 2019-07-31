package cn.my.gw.msg.codec;

import cn.my.gw.msg.LoginReqMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class LoginReqMsgEncoder<T extends LoginReqMsg> extends MsgHeadEncoder<T> {
	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		byte[] src;
		byte[] dest;

		dest = new byte[10];
		src = msg.getUsername().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[16];
		src = msg.getPassword().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);
	}

	public void dispose() throws Exception {
	}
}
