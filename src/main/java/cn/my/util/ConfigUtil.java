package cn.my.util;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

public class ConfigUtil {

    public static Config config = ConfigService.getAppConfig();

    public static String getValue(String  key,String defValue){
        return config.getProperty(key,defValue);
    }
    
}
