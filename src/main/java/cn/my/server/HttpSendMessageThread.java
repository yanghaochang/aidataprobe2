package cn.my.server;

import cn.my.gw.msg.HttpReqMsg;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentHashMap;

public class HttpSendMessageThread implements Runnable {

	private HttpReqMsg reqMsg;
	ConcurrentHashMap<IoSession, String> clients = RoutePolicy.clients;

	public HttpSendMessageThread() {

	}

	public void run() {
		for (;;) {
			try {
				reqMsg = HttpReqMsgQueue.getHttpReqMsgQueue().getObject();
				if (reqMsg != null) {
					for (IoSession tcpsession : clients.keySet()) {
						if (tcpsession.isConnected()) {
							tcpsession.write(reqMsg);
						}
					}
				}
			} catch (Exception e) {
				break;
			}
		}
	}
}
