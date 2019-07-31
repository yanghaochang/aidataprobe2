package cn.my.gw.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpClient {
	private static Logger logger = LoggerFactory.getLogger(HttpClient.class);
//    private static final Log LOGGER = LogFactory.getLog(HttpClient.class);
	public static void main(String[] args) throws Exception {
		// SocketChannel client = SocketChannel.open( );
		// client.connect( new InetSocketAddress( "127.0.0.1",80 ) );
		// client.configureBlocking( false );


		// client.connect( new InetSocketAddress( "http://open.xiaoniu.com",80
		// ),30 );
		// client.connect( new InetSocketAddress( "open.xiaoniu.com",80 ),30 );
        int num=1;

//		while (true) {
			Socket client = new Socket();
//			client.connect(new InetSocketAddress("172.16.11.233", 8010), 30);
			client.connect(new InetSocketAddress("127.0.0.1", 8010), 30);
			OutputStream os = client.getOutputStream();
			StringBuffer httpMsgBuf = new StringBuffer();

			String body = "{\n" + " \"common\": {\n" + "  \"upload_time\": \"2019-05-22 02:04:33.01\",\n" + "  \"os_system\": \"1\",\n" + "  \"os_version\": \"7.1.2\",\n" + "  \"mpc\": \"中国联通\",\n" + "  \"model\": \"Redmi 4X\",\n" + "  \"android_id\": \"cea919cc6ce6827\",\n" + "  \"imei\": \"myself\",\n" + "  \"screen_height\": 1280,\n" + "  \"screen_width\": 720,\n" + "  \"message_id\": \"1\",\n" + "  \"uuid\": \"1313p131\",\n" + "  \"product_name\": \"11\"\n" + " },\n" + " \"events\": [{\n" + "  \"longitude\":\"\",\n" + "  \"user_id\": \"asd\",\n" + "  \"phone_num\": \"\",\n" + "  \"network_type\": \"WIFI\",\n" + "  \"ip\": \"192.168.0.100\",\n" + "  \"ts\": \"2019-05-05 02:04:32.46\",\n" + "  \"event_type\": \"cold_start\",\n" + "  \"event_name\": \"冷启动\",\n" +
					"  \"event_code\": \"active\",\n" +

					"  \"app_version\": \"1.1.3\",\n" + "  \"market_name\": \"sc_meinv#_jinritoutiao630\",\n" + "  \"page_type\": \"android\",\n" + "  \"cv\": \"1.1.2.1\"\n" + " }]\n" + "}";

			httpMsgBuf.append("POST /apis?parm=1&a=b公司 HTTP/1.1\r\n");
			httpMsgBuf.append("Connection: keep-alive\r\n");
			httpMsgBuf.append("User-Agent: fireworks\r\n");
			httpMsgBuf.append("Host: open.xiaoniu.com 80\r\n");
			httpMsgBuf.append("Cookie:BAIDUID=072CF2CD01EB3F9FC4751508C7B9DC13:FG=1; BDRCVFR[kpkBv0s1F-3]=aeXf-1x8UdYcs; USERID=0759735f7adb2c8511ef7ed6\r\n");
			httpMsgBuf.append("Content-Length: " + body.getBytes("UTF-8").length + "\r\n\r\n");
		// httpMsgBuf.append( "Content-Length: "+body.length()+"\r\n\r\n" );

			httpMsgBuf.append(body);

			// os.write( ByteBuffer.wrap( httpMsgBuf.toString( ).getBytes( "UTF-8" )
			// ) );
			os.write(httpMsgBuf.toString().getBytes("UTF-8"));
			InputStreamReader in = new InputStreamReader(client.getInputStream(), "utf-8");
			int c;
			while ((c = in.read()) != -1) {
				System.out.print((char) c);
			}
			os.close();
			in.close();
			client.close();
            num++;
			logger.info("当前发送数据量:"+num);
//		}
	}
}
