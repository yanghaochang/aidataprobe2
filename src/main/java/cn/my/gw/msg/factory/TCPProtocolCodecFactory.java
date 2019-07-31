package cn.my.gw.msg.factory;

import cn.my.gw.msg.*;
import cn.my.gw.msg.codec.*;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * 协议解析工厂
 * @author liuchenglei
 */
public class TCPProtocolCodecFactory extends DemuxingProtocolCodecFactory
{
    public TCPProtocolCodecFactory( )
    {
    }

    public TCPProtocolCodecFactory(boolean server )
    {
        if( server )
        {
            super.addMessageDecoder( NotMatchMsgDecoder.class );

            super.addMessageDecoder( MoReqMsgDecoder.class );
            super.addMessageEncoder( MoResMsg.class, MoResMsgEncoder.class );
            super.addMessageDecoder( MoResMsgDecoder.class );
            super.addMessageEncoder( MoReqMsg.class, MoReqMsgEncoder.class );
            
            super.addMessageDecoder( MethodCallResMsgDecoder.class );
            super.addMessageEncoder( MethodCallResMsg.class,MethodCallResMsgEncoder.class );
            super.addMessageDecoder( MethodCallReqMsgDecoder.class );
            super.addMessageEncoder( MethodCallReqMsg.class,MethodCallReqMsgEncoder.class );
            
            super.addMessageDecoder( MethodCallRetMsgDecoder.class );
            super.addMessageEncoder( MethodCallRetMsg.class,MethodCallRetMsgEncoder.class );
            
            super.addMessageDecoder( HttpReqMsgDecoder.class );
            super.addMessageEncoder( HttpResMsg.class,HttpResMsgEncoder.class );
            
            super.addMessageDecoder( HeartBeatResMsgDecoder.class );
            super.addMessageEncoder( HeartBeatReqMsg.class,HeartBeatReqMsgEncoder.class );
            super.addMessageDecoder( HeartBeatReqMsgDecoder.class );
            super.addMessageEncoder( HeartBeatResMsg.class,HeartBeatResMsgEncoder.class );
            
            super.addMessageDecoder( LoginReqMsgDecoder.class );
            super.addMessageEncoder( LoginResMsg.class,LoginResMsgEncoder.class );
            super.addMessageDecoder( LoginResMsgDecoder.class );
            super.addMessageEncoder( LoginReqMsg.class,LoginReqMsgEncoder.class );
        }
        else
        {
            super.addMessageDecoder( NotMatchMsgDecoder.class );

            super.addMessageDecoder( LoginReqMsgDecoder.class );
            super.addMessageEncoder( LoginResMsg.class,LoginResMsgEncoder.class );
            super.addMessageDecoder( LoginResMsgDecoder.class );
            super.addMessageEncoder( LoginReqMsg.class,LoginReqMsgEncoder.class );

            super.addMessageDecoder( HeartBeatResMsgDecoder.class );
            super.addMessageEncoder( HeartBeatReqMsg.class,HeartBeatReqMsgEncoder.class );
            super.addMessageDecoder( HeartBeatReqMsgDecoder.class );
            super.addMessageEncoder( HeartBeatResMsg.class,HeartBeatResMsgEncoder.class );

            super.addMessageDecoder( MoReqMsgDecoder.class );
            super.addMessageEncoder( MoResMsg.class,MoResMsgEncoder.class );
            super.addMessageDecoder( MoResMsgDecoder.class );
            super.addMessageEncoder( MoReqMsg.class,MoReqMsgEncoder.class );
            
            super.addMessageDecoder( MethodCallResMsgDecoder.class );
            super.addMessageEncoder( MethodCallResMsg.class,MethodCallResMsgEncoder.class );
            super.addMessageDecoder( MethodCallReqMsgDecoder.class );
            super.addMessageEncoder( MethodCallReqMsg.class,MethodCallReqMsgEncoder.class );
            
            super.addMessageDecoder( MethodCallRetMsgDecoder.class );
            super.addMessageEncoder( MethodCallRetMsg.class,MethodCallRetMsgEncoder.class );
        }
    }
}
