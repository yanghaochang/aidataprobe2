package cn.my.util;

import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties properties;

	public static void reviseProperties() {

		properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream("/home/huaxuefeng/log4j/log4j.properties");
			properties.load(fis);// 将输入流加载到配置对象,以使配置对象可以读取config.propertis信息
			fis.close();
		    PropertyConfigurator.configure(properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
