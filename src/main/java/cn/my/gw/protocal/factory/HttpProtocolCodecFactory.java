package cn.my.gw.protocal.factory;

import cn.my.gw.msg.HttpResMsg;
import cn.my.gw.msg.codec.HttpReqMsgDecoder;
import cn.my.gw.msg.codec.HttpResMsgEncoder;
import cn.my.gw.msg.codec.NotMatchMsgDecoder;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * @author liuchenglei
 */
public class HttpProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public HttpProtocolCodecFactory() {
	}

	public HttpProtocolCodecFactory(boolean server) {
		if (server) {
			super.addMessageDecoder(NotMatchMsgDecoder.class);

			super.addMessageDecoder(HttpReqMsgDecoder.class);
			super.addMessageEncoder(HttpResMsg.class, HttpResMsgEncoder.class);
		} else {
			super.addMessageDecoder(NotMatchMsgDecoder.class);
		}
	}
}
