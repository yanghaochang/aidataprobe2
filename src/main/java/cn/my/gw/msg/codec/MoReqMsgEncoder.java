package cn.my.gw.msg.codec;

import cn.my.gw.msg.MoReqMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class MoReqMsgEncoder<T extends MoReqMsg> extends MsgHeadEncoder<T> {
	protected void encodeBody(IoSession session, T msg, IoBuffer buf) throws Exception {
		byte[] dest;
		byte[] src;

		buf.putInt(msg.getMsgID());

		dest = new byte[21];
		src = msg.getSrcTerminalID().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[21];
		src = msg.getDestTerminalID().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[127];
		src = msg.getMsgContent().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		buf.put(msg.getContentLength());

		dest = new byte[20];
		src = msg.getKeyword().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[30];
		src = msg.getSendTime().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		buf.putInt(msg.getSpID());
		buf.putInt(msg.getGateWayID());
		buf.putInt(msg.getMoProcessType());

		dest = new byte[20];
		src = msg.getLinkID().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);

		dest = new byte[20];
		src = msg.getBusinessType().getBytes();
		System.arraycopy(src, 0, dest, 0, src.length > dest.length ? dest.length : src.length);
		buf.put(dest);
	}

	public void dispose() throws Exception {
	}
}
