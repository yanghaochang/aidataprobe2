package cn.my.server;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成唯一序列
 * 
 * @author liuchenglei
 */
public final class TcpGenerateSequence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private final static long MAX_VALUE = Integer.MAX_VALUE; // 整形最大值
	private static long generateCount = 0; // 已经产生的合法随机数的个数
	// private static long sequence = 0;
	private static AtomicInteger ai = new AtomicInteger(1);

	/**
	 * 返回唯一序列号
	 */
	public static int generateSequence() {
		// do
		// {
		// sequence++;
		// if( sequence <= MAX_VALUE )
		// {
		// generateCount++;
		// break;
		// }else
		// {
		// sequence = 1;
		// break;
		// }
		// }
		// while( sequence > MAX_VALUE );

		// return ( int )sequence;
		return ai.getAndIncrement();// 多线程并发不可能得到相同的值
	}

	/**
	 * 生成合法序列号的次数
	 * 
	 * @return
	 */
	public final static long getGernateCount() {
		return generateCount;
	}

}
