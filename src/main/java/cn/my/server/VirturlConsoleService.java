package cn.my.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VirturlConsoleService implements Runnable {
	private int port = 10003;
	private DataProbeServer dataProbeServer;

	public VirturlConsoleService(int port, DataProbeServer dataProbeServer) {
		this.port = port;
		this.dataProbeServer = dataProbeServer;
	}

	public void run() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
		} catch (IOException ee) {
			System.err.println(ee);
		}

		Socket connection = null;
		while (true) {
			try {
				connection = server.accept();
				OutputStream out = connection.getOutputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				out.write(getBytes(">>$"));
				out.flush();
				String line = in.readLine();
				while (line != null && !line.equals("") && !line.equals("exit")) {
					try {
						if (line.equalsIgnoreCase("help")) {
							out.write(getBytes("close  关闭服务\r\n"));
							out.write(getBytes("exit  退出虚拟控制台\r\n"));
							out.flush();
						} else if (line.equalsIgnoreCase("close")) {
							out.write(getBytes(
									"系统关闭中 请稍后使用以下 命令启动:nohup ${PWD}/dataProbeServer.sh>nohup.out 2>&1 &\r\n"));
							out.flush();
							Runtime.getRuntime().exit(-1);
						} else if (line.startsWith("trace")) {
							String str[] = line.split(" ");
							if (str.length == 3) {
								/*
								 * if ( str[1].matches( "" ) ) int count = 1;
								 * long mobile = Long.parseLong( str[1] ); if(
								 * str.length > 4 ) { count = Integer.parseInt(
								 * str[4] ); }
								 */
								StringBuffer sb = new StringBuffer();
								for (int i = 0; i < 10; i++) {
									sb.delete(0, sb.length());
									sb.append(RoutePolicy.getRoutePolicy().toString());
									sb.append("\r\n");
									sb.append(HttpReqMsgQueue.getHttpReqMsgQueue().toString());
									sb.append("\r\n");
									out.write(getBytes(sb.toString()));
									out.flush();
								}
							} else {
								out.write(getBytes("格式错误trace 4 3,代表每隔4秒输出一次  共输出3次\r\n"));
								out.write(getBytes(">>$\r\n"));
								out.flush();
							}
						} else if (line.startsWith("count")) {

						} else {
							out.write(getBytes("close  关闭服务\r\n"));
							out.write(getBytes("exit  退出虚拟控制台\r\n"));
							out.flush();
						}
						line = in.readLine();
					} catch (Exception e) {
					}
				}

				out.write(getBytes("exit Ok !\r\n"));
				out.flush();

				out.close();
				in.close();
				connection.close();
			} catch (Exception ioe) {
				System.err.println(ioe);
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (IOException e) {
				}
			}
		}
	}

	private static final byte[] getBytes(String args) throws Exception {
		return args.getBytes("UTF-8");
	}

	public String toString() {
		return super.toString() + " " + dataProbeServer.getClass();
	}

	public static void main(String[] arg) throws Exception {
		VirturlConsoleService se = new VirturlConsoleService(10003, null);
		new Thread(se).start();
	}
}
