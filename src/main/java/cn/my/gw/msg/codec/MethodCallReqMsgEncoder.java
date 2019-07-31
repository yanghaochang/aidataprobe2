package cn.my.gw.msg.codec;

import cn.my.gw.msg.MethodCallReqMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class MethodCallReqMsgEncoder<T extends MethodCallReqMsg> extends MsgHeadEncoder<T> {

	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		byte[] src;
		byte[] dest;

		buf.putLong(msg.getSessionID());

		dest = new byte[40];
		src = msg.getMethodName().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[1024 * 1024];
		src = msg.getAppParams().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[100];
		src = msg.getSysParams().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		buf.putLong(msg.getBusiType());

		dest = new byte[30];
		src = msg.getTime().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);
	}

	public void dispose() throws Exception {
	}
}
