package cn.my.util;
import com.ctrip.framework.apollo.ConfigFile;
import com.ctrip.framework.foundation.Foundation;
import org.apache.log4j.Logger;
public class ApolloConfigUtil {
private static Logger logger = Logger.getLogger("profession");
public static void getPrivateConfig() {
	System.setProperty("app.id", "110496");
	// dev 开发环境 http://ap.58huihuahua.com http://192.168.6.103:8087
	String meta = null;
	if(Foundation.server().getEnvType().equalsIgnoreCase("dev")){
		meta="http://ap.fqt188.com:18081";
	}else if(Foundation.server().getEnvType().equalsIgnoreCase("fat")){
		meta="http://ap.fqt188.com:18082";
	} else if(Foundation.server().getEnvType().equalsIgnoreCase("uat")){
		meta="http://ap.fqt188.com:18083";
	}else if(Foundation.server().getEnvType().equalsIgnoreCase("pro")){
		meta="http://ap.fqt188.com:8080";
	}
	System.setProperty("apollo.meta",meta);
	System.setProperty("apollo.cacheDir", "conf_cache_dir");
	//System.out.println("config1:"+ConfigUtil.getValue("lw_dbUrl", null));
/*
	Config config1 = ConfigService.getAppConfig();
	Set<String> propertiesNames1 = config1.getPropertyNames();
	for (String key : propertiesNames1) {
		System.out.println("------------key:" + key  + "---value:" 
				+ config1.getProperty(key, null));
	}*/

	//ConfigFile config2 = ConfigService.getConfigFile("service", ConfigFileFormat.XML);
	//print(config2);

	
}


private static void printEnvInfo() {
	String message = String.format("AppId: %s, Env: %s, DC: %s, IP: %s", Foundation.app()
			.getAppId(), Foundation.server().getEnvType(), Foundation.server().getDataCenter(),
			Foundation.net().getHostAddress());
	System.out.println(message);
}

private static void print(ConfigFile configFile) {
    if (!configFile.hasContent()) {
      System.out.println("No config file content found for " + configFile.getNamespace());
      return;
    }
    System.out.println("=== Config File Content for " + configFile.getNamespace() + " is as follows: ");
    System.out.println(configFile.getContent());
  }

//	public static void main(String[] args) {
//		//getPrivateConfig();
//		//printEnvInfo();
//		String meta = null;
//		if(Foundation.server().getEnvType().equalsIgnoreCase("dev")){
//			meta="http://apollo.58huihuahua.com:18081";
//		}else if(Foundation.server().getEnvType().equalsIgnoreCase("fat")){
//			meta="http://apollo.58huihuahua.com:18082";
//		} else if(Foundation.server().getEnvType().equalsIgnoreCase("uat")){
//			meta="http://apollo.58huihuahua.com:18083";
//		}else if(Foundation.server().getEnvType().equalsIgnoreCase("pro")){
//			meta="http://apollo.51huihuahua.com:8080";
//		}
//		System.out.println("meta:"+meta);
//	}
	
}
