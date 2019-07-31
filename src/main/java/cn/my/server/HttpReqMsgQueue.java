package cn.my.server;

import cn.my.gw.msg.HttpReqMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class HttpReqMsgQueue implements Runnable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3002086883213005788L;

	private static HttpReqMsgQueue httpReqMsgQueue = null;
	private final LinkedBlockingQueue<HttpReqMsg> sQueue;
	private static Logger logger = LoggerFactory.getLogger(HttpReqMsgQueue.class);
//	private static final Log intp = LogFactory.getLog("intp");

	private HttpReqMsgQueue() {
		sQueue = new LinkedBlockingQueue<HttpReqMsg>();
	}

	public static synchronized HttpReqMsgQueue getHttpReqMsgQueue() {
		if (httpReqMsgQueue == null) {
			httpReqMsgQueue = new HttpReqMsgQueue();
		}
		return httpReqMsgQueue;
	}

	public void add(HttpReqMsg msg) throws InterruptedException {
		logger.info(msg + " offered!");
		sQueue.put(msg);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Queue:" + HttpReqMsgQueue.class.getName() + "\t");
		sb.append(size());
		sb.append(TracerService.endLine);
		return sb.toString();
	}

	public HttpReqMsg getObject() throws InterruptedException {
		HttpReqMsg httpReqMsg = null;
		httpReqMsg = sQueue.take();
		if (httpReqMsg != null) {
			logger.info(httpReqMsg + " polled!");
		}
		return httpReqMsg;
	}

	public int size() {
		return sQueue.size();
	}

	public void run() {
		HttpReqMsgQueue httpReqMsgQueue = HttpReqMsgQueue.getHttpReqMsgQueue();
		HttpReqMsg httpReqMsg = null;
		while (true) {
			try {
				httpReqMsg = httpReqMsgQueue.getObject();
				if (httpReqMsg != null) {
					continue;
				}
				Thread.sleep(20L);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} finally {

			}
		}
	}

	public void deserialize() throws Exception {
		ObjectInputStream ois = null;
		File inFile = new File("serialData/mo.binary");
		try {
			if (inFile.exists()) {
				InputStream is = new FileInputStream(inFile);
				if (is.available() <= 0) {
					inFile.delete();
					return;
				}
				ois = new ObjectInputStream(new FileInputStream(inFile));
				HttpReqMsgQueue httpReqMsgQueue = getHttpReqMsgQueue();
				HttpReqMsg mo = null;
				LinkedList<HttpReqMsg> list = (LinkedList<HttpReqMsg>) ois.readObject();
				for (int i = 0; i < list.size(); i++) {
					mo = list.get(i);
					httpReqMsgQueue.add(mo);
				}
				ois.close();
				inFile.delete();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			throw ee;
		}

	}

	public void serialize() throws Exception {
		if (size() == 0) {
			return;
		}

		ObjectOutputStream out;
		File outputFile = new File("serialData/mo.binary");
		try {
			if (outputFile.exists()) {
				out = new ObjectOutputStream(new FileOutputStream(outputFile, false));
			} else {
				out = new ObjectOutputStream(new FileOutputStream(outputFile));
			}

			HttpReqMsgQueue httpReqMsgQueue = getHttpReqMsgQueue();
			LinkedList<HttpReqMsg> list = new LinkedList<HttpReqMsg>();

			HttpReqMsg mo = httpReqMsgQueue.getObject();
			while (mo != null) {
				list.add(mo);
				mo = httpReqMsgQueue.getObject();
			}

			out.writeObject(list);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
