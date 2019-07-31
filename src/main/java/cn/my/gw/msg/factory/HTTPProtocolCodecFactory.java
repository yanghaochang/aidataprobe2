package cn.my.gw.msg.factory;

import cn.my.gw.msg.HttpResMsg;
import cn.my.gw.msg.MethodCallRetMsg;
import cn.my.gw.msg.codec.*;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * @author liuchenglei
 */
public class HTTPProtocolCodecFactory extends DemuxingProtocolCodecFactory
{
    public HTTPProtocolCodecFactory( )
    {
    }

    public HTTPProtocolCodecFactory(boolean server )
    {
        if( server )
        {
            super.addMessageDecoder( NotMatchMsgDecoder.class );
            
            super.addMessageDecoder( HttpReqMsgDecoder.class );
            super.addMessageEncoder( HttpResMsg.class, HttpResMsgEncoder.class );
            
            super.addMessageDecoder( MethodCallRetMsgDecoder.class );
            super.addMessageEncoder( MethodCallRetMsg.class, MethodCallRetMsgEncoder.class );
        }
        else
        {
            super.addMessageDecoder( NotMatchMsgDecoder.class );
            
            super.addMessageDecoder( HttpReqMsgDecoder.class );
            super.addMessageEncoder( HttpResMsg.class, HttpResMsgEncoder.class );
        }
    }
}
