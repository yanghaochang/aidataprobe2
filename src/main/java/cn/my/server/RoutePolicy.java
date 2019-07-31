package cn.my.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 此类主要用于实现 连接到某一路由上的业务平台的动态实现
 *
 * 以及根据配置实现简单的策略路由
 *
 * @author liuchenglei
 */
public class RoutePolicy implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RoutePolicy.class);
//	private static final Log logger = LogFactory.getLog("sys");

	private static final ReentrantReadWriteLock rwl;

	private static RoutePolicy routePolicy = null;

	public static final ConcurrentHashMap<String, MyConfig> smgs;

	public static final ConcurrentHashMap<IoSession, String> clients;

	static {
		rwl = new ReentrantReadWriteLock();
		smgs = new ConcurrentHashMap<String, MyConfig>();
		clients = new ConcurrentHashMap<IoSession, String>();
	}

	/*
	 * 定义私有构造函数 实现单例模式
	 */
	private RoutePolicy() {
		new Thread(this).start();
	}

	public static synchronized RoutePolicy getRoutePolicy() {
		if (routePolicy == null) {
			routePolicy = new RoutePolicy();
		}
		return routePolicy;
	}

	public void putClient(IoSession session, String serviceModule) {
		try {
			rwl.writeLock().lock();
			clients.putIfAbsent(session, serviceModule);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwl.writeLock().unlock();
		}
	}

	public void removeClient(IoSession session) {
		try {
			rwl.writeLock().lock();
			if (clients.containsKey(session)) {
				logger.info("客户端0x" + RoutePolicy.clients.get(session) + "退出");
				clients.remove(session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rwl.writeLock().unlock();
		}
	}

	public void run() {
		/**
		 * some code will implements here
		 */
		while (true) {
			try {
				for (IoSession session : clients.keySet()) {
					if (!session.isConnected()) {
						removeClient(session);
					}
				}
				Thread.sleep(5000L);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

	public void loadConfig() throws Exception {
		ConcurrentHashMap<String, MyConfig> configMap = new ConcurrentHashMap<String, MyConfig>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builders = factory.newDocumentBuilder();
		builders.setErrorHandler(null);
		Document document = builders.parse(getClass().getClassLoader().getResourceAsStream("client.xml"));
		NodeList nodeList = document.getElementsByTagName("client");

		for (int i = 0; i < nodeList.getLength(); i++) {
			MyConfig config = new MyConfig();
			Node node = nodeList.item(i);
			if (node.hasAttributes()) {
				config.setId(node.getAttributes().item(0).getNodeValue());
			}

			NodeList childNodesList = node.getChildNodes();
			for (int k = 0; k < childNodesList.getLength(); k++) {
				Node childNodes = childNodesList.item(k);
				if (childNodes.getNodeName().equalsIgnoreCase("ip")) {
					config.setIp(childNodes.getTextContent());
					continue;
				}

				if (childNodes.getNodeName().equalsIgnoreCase("routeModule")) {
					config.setRouteModule(Integer.parseInt(childNodes.getTextContent().substring("0x".length())));
					continue;
				}

				if (childNodes.getNodeName().equalsIgnoreCase("serviceModule")) {
					config.setServiceModule(Integer.parseInt(childNodes.getTextContent().substring("0x".length())));
					continue;
				}

				if (childNodes.getNodeName().equalsIgnoreCase("username")) {
					config.setUsername(childNodes.getTextContent());
					continue;
				}

				if (childNodes.getNodeName().equalsIgnoreCase("password")) {
					config.setPassword(childNodes.getTextContent());
					continue;
				}
			}
			configMap.putIfAbsent(config.getServiceModule() + "", config);
		}

		try {
			rwl.writeLock().lock();
			smgs.clear();
			smgs.putAll(configMap);
		} finally {
			rwl.writeLock().unlock();

			StringBuffer sb = new StringBuffer();

			for (MyConfig config : RoutePolicy.smgs.values()) {
				sb.append("0x" + config.getServiceModule());
				sb.append(";");
			}
			// logger.info( "客户端配置个数 " + sb.toString( ) );
			sb.delete(0, sb.length());

			for (String key : RoutePolicy.clients.values()) {
				sb.append("0x" + key);
				sb.append(";");
			}
			// logger.info( "客户端活跃个数 " + sb.toString( ) );
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("------------------客户端配置个数--------------" + TracerService.endLine);
		for (MyConfig config : RoutePolicy.smgs.values()) {
			sb.append("0x" + config.getServiceModule());
			sb.append(TracerService.endLine);
		}
		sb.append("------------------客户端活跃个数--------------" + TracerService.endLine);

		for (String key : RoutePolicy.clients.values()) {
			sb.append("0x" + key);
			sb.append(TracerService.endLine);
		}
		sb.append("---------------------------------------");
		return sb.toString();
	}
}
