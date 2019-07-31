//package cn.my.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class MyConfig {
//	/**
//	 * spark
//	 */
//	public static String kafka_brokers;
//	public static String groupid;
//	public static String topics;
//
////	public static String key = "";
//
//
//	static{
//
//		try {
//			/*InputStream fs = new FileInputStream(new File("conf/config.properties"));
//			confProp.load(fs);*/
//
//			ApolloConfigUtil.getPrivateConfig();
//			/**
//			 * spark
//			 */
//			kafka_brokers = ConfigUtil.getValue("kafka_brokers", null);//confProp.getProperty("kafka_brokers");
//			groupid = ConfigUtil.getValue("groupid", null);//confProp.getProperty("groupid");
//			topics = ConfigUtil.getValue("topics", null);//confProp.getProperty("topics");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(-1);
//		}
//	}
//
//}
