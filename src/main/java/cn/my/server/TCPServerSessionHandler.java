package cn.my.server;

import cn.my.gw.msg.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuchenglei
 */
public class TCPServerSessionHandler extends IoHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(TCPServerSessionHandler.class);
//	private static final Log intp = LogFactory.getLog("intp");

	public TCPServerSessionHandler() {
		for (int i = 0; i < 20; i++) {
			new Thread(new HttpSendMessageThread(), "httpsendmessagethread").start();
		}
	}

	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	public void sessionOpened(IoSession session) {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
	}

	public void messageSent(IoSession session, Object msg) throws Exception {
		super.messageSent(session, msg);
	}

	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	public void messageReceived(IoSession session, Object msg) throws Exception {
		MsgHead head = (MsgHead) msg;
		int cmd = head.getOperCmd();
		if (cmd == HeartBeatResMsg.OPER_CMD) {
//			HeartBeatResMsg res = (HeartBeatResMsg) msg;
			logger.info(msg.toString());
			return;
		}

		if (cmd == LoginReqMsg.OPER_CMD) {
			LoginReqMsg req = (LoginReqMsg) msg;
			logger.info(msg.toString());
			LoginResMsg res = new LoginResMsg();
			res.setSequence(req.getSequence());
			res.setRequestModule(req.getRequestModule());
			res.setServiceModule(req.getServiceModule());

			String serviceModule = Integer.toHexString(req.getServiceModule());

			if (!RoutePolicy.smgs.containsKey(serviceModule)) {
				res.setStatus(-10);
				session.write(res);
				logger.info(res.toString());
				if (session != null) {
					session.closeOnFlush();
				}
				return;
			}

			if (RoutePolicy.clients.containsValue(serviceModule)) {
				res.setStatus(-11);
				session.write(res);
				logger.info(res.toString());
				if (session != null) {
					session.closeOnFlush();
				}
				return;
			}
			res.setStatus(0);
			session.write(res);
			logger.info(res.toString());

			RoutePolicy.getRoutePolicy().putClient(session, serviceModule);
			return;
		}

		if (msg instanceof NotMatchMsg) {
			NotMatchMsg totMatchMsg = (NotMatchMsg) msg;
			logger.info(msg.toString());
			return;
		}
	}

	/*
	 * private void fireSmgs( IoSession session,MsgHead msg ) throws Exception {
	 * List<IoSession> list = Collections.list( RoutePolicy.clients.keys( ) );
	 * Collections.shuffle( list ); IoSession smgs = null; int serviceModule =
	 * 0; for( int i =0; i<list.size( );i++ ) { smgs = list.get( i );
	 * serviceModule = Integer.parseInt( RoutePolicy.clients.get( smgs ),16 );
	 * if ( serviceModule > 0x70000000 && smgs.getId( ) != session.getId( ) ) {
	 * msg.setServiceModule( serviceModule );
	 * 
	 * smgs.write( msg ); intp.info( msg ); break; } } return; }
	 * 
	 * private void fireClients( IoSession session,MsgHead msg ) throws
	 * Exception { List<IoSession> list = Collections.list(
	 * RoutePolicy.clients.keys( ) ); Collections.shuffle( list ); IoSession
	 * client = null; int serviceModule = 0; for( int i =0; i<list.size( );i++ )
	 * { client = list.get( i ); serviceModule = Integer.parseInt(
	 * RoutePolicy.clients.get( client ),16 ); if ( serviceModule < 0x70000000 )
	 * { msg.setServiceModule( serviceModule );
	 * 
	 * client.write( msg ); intp.info( msg ); break; } } return; }
	 */
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		HeartBeatReqMsg reqMsg = new HeartBeatReqMsg();
		reqMsg.setSequence(TcpGenerateSequence.generateSequence());
		reqMsg.setRequestModule(MyConfig.TCP_REQUEST_MODULE);
		session.write(reqMsg);
		// intp.info( reqMsg );
		/*
		 * MoReqMsg mo = new MoReqMsg( ); mo.setSequence(
		 * GenerateSequence.generateSequence( ) ); mo.setRequestModule(
		 * Config.ROUTE_REQUEST_MODULE ); mo.setMsgID( 9999999 );
		 * mo.setSrcTerminalID( "1391840" ); mo.setDestTerminalID( "1065851821"
		 * ); mo.setMsgContent( "keyword imei imsi" ); mo.setContentLength(
		 * (byte)mo.getMsgContent( ).length( ) ); mo.setKeyword( "BLT" );
		 * mo.setSendTime( "yyyy-mm-dd hh24:mi:ss" ); mo.setSpID( 5555 );
		 * mo.setGateWayID( 1 ); mo.setMoProcessType( 1 ); mo.setLinkID(
		 * "456789" ); mo.setBusinessType( "ocs" );
		 * 
		 * session.write( mo ); intp.info( mo );
		 */
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (session != null) {
			session.closeOnFlush();
		}
		cause.printStackTrace();
		logger.error("exception", cause);
	}
}
