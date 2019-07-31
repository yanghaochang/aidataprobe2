package cn.my.gw.msg.codec;

import cn.my.gw.msg.HttpResMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class HttpResMsgEncoder<T extends HttpResMsg> implements MessageEncoder<T> {
	public void encode(IoSession session, T msg, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(2048);
		buf.setAutoExpand(true);
		/*
		 * String httpBody = client.getBody( ); httpHeader = httpHeader.replace(
		 * "${Content-Length}",httpBody.getBytes( "UTF-8" ).length+"" );
		 * //buf.put( (httpHeader+httpBody).getBytes( "UTF-8" ) );
		 */
		buf.put((msg.getHeader() + "\r\n\r\n").getBytes("UTF-8"));
		if (msg.getBody() != null) {
			buf.put(msg.getBody().getBytes("UTF-8"));
		}

		buf.flip();

		out.write(buf);
	}

	public void dispose() throws Exception {

	}
}
