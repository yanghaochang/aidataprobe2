package cn.my.gw.msg.codec;

import cn.my.gw.msg.MsgHead;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * 消息头编码
 * 
 * @author liuchenglei
 */
public abstract class MsgHeadEncoder<T extends MsgHead> implements MessageEncoder<T> {
	public void encode(IoSession session, T msg, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(1024);
		buf.setAutoExpand(true);

		buf.putInt(msg.getMsgLength());
		buf.putInt(msg.getVersion());
		buf.putInt(msg.getSequence());
		buf.putInt(msg.getRequestModule());
		buf.putInt(msg.getServiceModule());
		buf.putInt(msg.getOperCmd());

		encodeBody(session, msg, buf);
		buf.flip();
		out.write(buf);
		/*
		 * byte[] by = buf.array( ) ; for ( int i=0; i<buf.limit( ); i++ ) {
		 * byte b = by[i]; System.out.print( Integer.toHexString( b&0xff )+" "
		 * ); } System.out.println( );
		 */
	}

	protected abstract void encodeBody(IoSession session, T msg, IoBuffer out) throws Exception;
}
