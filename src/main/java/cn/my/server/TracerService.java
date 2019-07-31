package cn.my.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 跟踪的网络核心处理 auth liuchenglei
 */
public class TracerService implements Runnable {
	private static Logger loger = Logger.getLogger("sys");

	private OutputStream out;
	private InputStream in;
	private Socket traceClient;

	public static final String endLine = "\r\n";

	public TracerService(Socket traceClient, OutputStream out, InputStream in) {
		this.traceClient = traceClient;
		this.out = out;
		this.in = in;
		loger.info("a trace client connected ..." + (traceClient.toString()));
		new Thread(this).start();
	}

	public void run() {
		try {
			while (true) {
				if (out != null) {
					StringBuffer sb = new StringBuffer();

					sb.append("EXIT:input any key exit!");
					sb.append(endLine);
					sb.append(RoutePolicy.getRoutePolicy().toString());
					sb.append(endLine);
					sb.append(HttpReqMsgQueue.getHttpReqMsgQueue().toString());
					sb.append(endLine);

					send(sb.toString().getBytes());
				}
				int by = in.available();
				if (by > 0)
					break;
				Thread.sleep(2000L);
			}
			in.close();
			out.close();
			traceClient.close();
		} catch (Exception e) {
			loger.error("Trace Thread error", e);
		}
	}

	protected void finalize() {
		try {
			super.finalize();
			out.close();
		} catch (Throwable te) {

		}
	}

	public void send(byte[] buffer) throws IOException {
		out.write(buffer);
		out.flush();
	}
}
