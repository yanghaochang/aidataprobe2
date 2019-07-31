package cn.my.server;

import cn.my.gw.msg.factory.TCPProtocolCodecFactory;
import cn.my.gw.protocal.factory.HttpProtocolCodecFactory;
import cn.my.util.DateFormat;
import cn.my.util.DingdingUtil;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 路由模块启动类
 * 
 * @author hxf
 */
@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序
public class DataProbeServer implements Runnable , ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(DataProbeServer.class);
//	private static final Log LOGGER = LogFactory.getLog(DataProbeServer.class);
	private static DataProbeServer dataProbeServer = null;

	private static NioSocketAcceptor httpAcceptor = null;
	private static NioSocketAcceptor tcpAcceptor = null;

	private DataProbeServer() {
	}
	public synchronized static DataProbeServer getServer() {
		if (dataProbeServer == null) {
			dataProbeServer = new DataProbeServer();
		}
		return dataProbeServer;
	}

	public void run() {
		while (true) {
			try {
				RoutePolicy.getRoutePolicy().loadConfig();
				Thread.sleep(20 * 1000L);
			} catch (Exception e) {
                logger.error("DataProbeServer.class", e);
			}
		}
	}

	private final void closeSystem() {
		if (httpAcceptor != null) {
			try {
				httpAcceptor.unbind();
			} catch (Exception e) {
                logger.error("httpAcceptor.unbind( )失败 ", e);
			}
		}

		if (tcpAcceptor != null) {
			try {
				tcpAcceptor.unbind();
			} catch (Exception e) {
                logger.error("tcpAcceptor.unbind( )失败 " + e);
			}
		}

		try {
			HttpReqMsgQueue.getHttpReqMsgQueue().serialize();
            logger.info("关闭HttpMoMsgQueue队列处理线程成功");
		} catch (Exception e) {
            logger.error("关闭HttpMoMsgQueue队列处理线程失败" + e);
		}

        logger.info("关闭HttpReportMsgQueue队列处理线程！！");
        logger.info("系统正常退出,退出时间" + DateFormat.getDateTimeFormat("yyyyMMddHHmmss"));
	}

	public static void main(String[] args) throws Throwable {
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			long startTime = System.currentTimeMillis();
			final DataProbeServer dataProbeServer = getServer();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					dataProbeServer.closeSystem();
				}
			});
			/*
			 * 检查联通连接 DataProbeServer.getMySGIPSMProxy( );
			 */
			/*
			 * 启动主加载配置线程
			 */
            logger.info("配置线程开始启动");
			Thread configThread = new Thread(getServer(), "T_LOADING_CONFIG_THREAD");
			configThread.start();
            logger.info("配置线程启动完毕");

			/*
			 * 启动跟踪监控线程
			 */
            logger.info("跟踪线程开始启动");
			Thread traceThread = new Thread(new TracerThread(MyConfig.TRACER_PORT), "T_TRACER_THREAD");
			traceThread.start();
            logger.info("跟踪线程启动完毕 监听端口 " + MyConfig.TRACER_PORT);

			/*
			 * 启动虚拟终端服务
			 */
            logger.info("虚拟终端服务开始启动");
			Thread consoleServerThread = new Thread(
					new VirturlConsoleService(MyConfig.VIRTUAL_SERVICE_PORT, dataProbeServer), "T_CONSOLE_SERVER_THREAD");
			consoleServerThread.start();
            logger.info("虚拟终端服务启动完毕 监听端口 " + MyConfig.VIRTUAL_SERVICE_PORT);

            logger.info("availableProcessors:" + Runtime.getRuntime().availableProcessors());
			/*
			 * tcp监听服务启动（客户端监听线程启动）
			 */

//				logger.info("TCP网关开始启动（路由开始启动）");
//			tcpAcceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors());
//			tcpAcceptor.setReuseAddress(true);
//			tcpAcceptor.getFilterChain().addLast("codec",
//					new ProtocolCodecFilter(new TCPProtocolCodecFactory(true)));
//			// acceptor.getFilterChain( ).addLast( "LOGGER",new
//			// LoggingFilter( ) );
//			tcpAcceptor.setHandler(new TCPServerSessionHandler());
//			tcpAcceptor.bind(new InetSocketAddress(MyConfig.TCP_PORT));
//			logger.info("TCP网关启动完毕（路由启动完毕）监听端口 " + MyConfig.TCP_PORT);


			/*
			 * 客户端监听线程启动
			 */
            logger.info( "路由开始启动" );
			NioSocketAcceptor acceptor = new NioSocketAcceptor( Runtime.getRuntime( ).availableProcessors( )* 4 );
			acceptor.setReuseAddress( true );
			acceptor.getFilterChain( ).addLast( "codec",new ProtocolCodecFilter( new TCPProtocolCodecFactory( true ) ) );
			//acceptor.getFilterChain( ).addLast("exector", new ExecutorFilter( Executors.newCachedThreadPool()));
			//acceptor.getFilterChain( ).addLast( "logger",new LoggingFilter( ) );
			acceptor.setHandler( new TCPServerSessionHandler( ) );
			acceptor.bind( new InetSocketAddress( MyConfig.TCP_PORT ) );
            logger.info( "路由启动完毕  监听端口:"  + MyConfig.TCP_PORT   );

			/*
			 * http监听服务启动
			 */
			if (MyConfig.IS_RUNNING_HTTPSERVER) {
                logger.info("HTTP网关开始启动");
				httpAcceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() * 4);
				httpAcceptor.setReuseAddress(true);

				// 设置过滤器
				httpAcceptor.getFilterChain().addLast("codec",
						new ProtocolCodecFilter(new HttpProtocolCodecFactory(true)));

				// 建立线程池
				// java.util.concurrent.Executor threadPool =
				// Executors.newFixedThreadPool(60);
				java.util.concurrent.Executor threadPool = Executors.newCachedThreadPool();
				// 加入过滤器（Filter）到httpAcceptor
				httpAcceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(threadPool));

				// 设置读取数据的缓冲区大小
				httpAcceptor.getSessionConfig().setReadBufferSize(2048);
				// 读写通道无操作进入空闲状态
				httpAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60 * 60 * 10);
				// 设置输出缓冲区的大小
				httpAcceptor.getSessionConfig().setReceiveBufferSize(3000);// 设置输入缓冲区的大小
				httpAcceptor.getSessionConfig().setSendBufferSize(1024);

				// 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
				httpAcceptor.getSessionConfig().setTcpNoDelay(true);
				// 设置主服务监听端口的监听队列的最大值为60，如果当前已经有60个连接，再新的连接来将被服务器拒绝
				httpAcceptor.setBacklog(100);
				// 绑定逻辑处理器

				httpAcceptor.setHandler(new HTTPServerSessionHandler());
				httpAcceptor.bind(new InetSocketAddress(MyConfig.HTTP_PORT));
                logger.info("HTTP网关监听端口 " + MyConfig.HTTP_PORT);

                logger.info("HTTP网关启动完毕 ");
			}
			long time = System.currentTimeMillis() - startTime;
            logger.info("系统启动耗时(单位ms)：" + time);

			DingdingUtil.sendMessage("系统启动耗时(ms)：" + time, MyConfig.SYS_SERVICE_MONITOR);
		} catch (Exception e) {
			DingdingUtil.sendErrorMessage("系统启动异常", e.toString(), MyConfig.SYS_ERROR_MONITOR,e.getStackTrace()[0].getLineNumber());
//			DingdingUtil.sendErrorMessageToDingding("\"系统启动异常：\"", e.toString(), Config.SYS_ERROR_MONITOR,ResultUtil.DINGDING_STARTUP_TIME);
            logger.error("系统启动议程异常,错误行号：" + e.getStackTrace()[0].getLineNumber(), e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
