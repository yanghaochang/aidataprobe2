package cn.my.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 监控入口
 * 
 * @author liuchenglei
 */
public class TracerThread implements Runnable {
	private ServerSocket traceSocket;
	private Socket traceClient;
	private int port;

	public TracerThread(int port) throws Exception {
		this.port = port;
		traceSocket = new ServerSocket(this.port);
	}

	public void run() {
		while (true) {
			try {
				traceClient = traceSocket.accept();
				if (traceClient != null) {
					new TracerService(traceClient, traceClient.getOutputStream(), traceClient.getInputStream());
				}
				Thread.sleep(5000L);
			} catch (Exception e) {
			}
		}
	}
}
