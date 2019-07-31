package cn.my.server;

import com.alibaba.fastjson.JSONObject;
import cn.my.util.ConfigUtil;
import cn.my.util.DingdingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysSwitchConfig {
    private static Logger logger = LoggerFactory.getLogger(SysSwitchConfig.class);
//    private static final Log ERRORMESSAGE = LogFactory.getLog("errorMessage");
    private static final String switchs = "1";

    public JSONObject getSwitch() throws Exception {
        JSONObject json = new JSONObject();
        try {
	/*		Properties confProp = new Properties();
			InputStream fs = new FileInputStream(new File("conf/sys.properties"));
			confProp.load(fs);*/
            json.put("hbase_switch", ConfigUtil.getValue("hbase_switch",null));
            json.put("mongodb_switch", ConfigUtil.getValue("mongodb_switch",null));
            json.put("dingding_switch", ConfigUtil.getValue("dingding_switch",null));
            json.put("route_switch", ConfigUtil.getValue("route_switch",null));
            json.put("mysql_switch", ConfigUtil.getValue("mysql_switch",null));
            json.put("hbase1_switch", ConfigUtil.getValue("hbase1_switch",null));
        } catch (Exception e) {
            String errorMessage = "sys.properties加载失败，开关使用默认值" + SysSwitchConfig.switchs;
            logger.error(errorMessage, e);
            json.put("hbase_switch", SysSwitchConfig.switchs);
            json.put("mongodb_switch", SysSwitchConfig.switchs);
            json.put("dingding_switch", SysSwitchConfig.switchs);
            json.put("mysql_switch", SysSwitchConfig.switchs);
            json.put("hbase1_switch", SysSwitchConfig.switchs);
            DingdingUtil.sendMessage(errorMessage, MyConfig.SYS_ERROR_MONITOR);
        }
        return json;
    }

}
