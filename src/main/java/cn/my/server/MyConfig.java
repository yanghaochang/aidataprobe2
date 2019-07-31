/*
 * 本类是业务平台的配置信息
 * 是方便对象对立而建立的javabean
 */
package cn.my.server;
import java.util.Properties;
import cn.my.util.ApolloConfigUtil;
import cn.my.util.ConfigUtil;

/**
 * <p>
 * 平台配置信息javabean
 * </p>
 *
 * @author hxf
 * @version $revision
 */
public class MyConfig {
    public static int HTTP_PORT;
    public static int TCP_PORT;
    public static int TRACER_PORT;
    public static int VIRTUAL_SERVICE_PORT;

    public static int TCP_REQUEST_MODULE;
    public static boolean IS_RUNNING_TCPSERVER;
    public static boolean IS_ORDER_SERVER;
    public static boolean IS_RUNNING_HTTPSERVER;
    // hbase add
    public static String HBASE_CUSTOM_URL;
    public static String HBASE_DEVICE_URL;

    public static long DB_TIME_THRESHOLD;
    public static long SERVICE_TIME_THRESHOLD;

    public static String SYS_ERROR_MONITOR;
    public static String SYS_SERVICE_MONITOR;
    public static String DOWNLOAD_UPDATE_MONITOR;
    public static String DOWNLOAD_MORE_DP_MONITOR;
    public static String HBASE_SPACE;
    public static String HBASE_MG_SPACE;
    public static String HBASE_MORE_SPACE;
    public static String DOWNLOAD_URL;
    public static String MORE_DP_DOWNLOAD_URL;

    //public static final String PATH = MyConfig.class.getResource("../../../").toString();

    private String id;
    private String ip;
    private int routeModule;
    private int serviceModule;
    private String username;
    private String password;

    /**
     * kafka
     */
    public static String kafka_brokers;

    static {
        Properties confProp = new Properties();
        try {
            ApolloConfigUtil.getPrivateConfig();
			/*InputStream fs = new FileInputStream(new File("conf/config.properties"));
			confProp.load(fs);*/
            //HBASE_MORE_SPACE = ConfigUtil.getValue("hbase_more_space",null); //confProp.getProperty("hbase_more_space");
           // HBASE_MG_SPACE =  ConfigUtil.getValue("hbase_more_space",null);//confProp.getProperty("hbase_mg_space");
           // HBASE_SPACE =  ConfigUtil.getValue("hbase_more_space",null);//confProp.getProperty("hbase_space");
            TCP_PORT =  Integer.parseInt(ConfigUtil.getValue("tcp_port",null));//Integer.parseInt(confProp.getProperty("tcp_port"));
            TRACER_PORT =  Integer.parseInt(ConfigUtil.getValue("tracer_port",null));//Integer.parseInt(confProp.getProperty("tracer_port"));
            VIRTUAL_SERVICE_PORT = Integer.parseInt(ConfigUtil.getValue("virtual_service_port",null));//Integer.parseInt(confProp.getProperty("virtual_service_port"));
            HTTP_PORT =  Integer.parseInt(ConfigUtil.getValue("http_port",null));//Integer.parseInt(confProp.getProperty("http_port"));
            TCP_REQUEST_MODULE =  Integer.parseInt(ConfigUtil.getValue("tcp_request_module",null).replaceAll("0x", ""), 16);//Integer.parseInt(confProp.getProperty("tcp_request_module").replaceAll("0x", ""), 16);
            IS_RUNNING_TCPSERVER =  Boolean.parseBoolean(ConfigUtil.getValue("is_running_tcpserver",null)); //Boolean.parseBoolean(confProp.getProperty("is_running_tcpserver"));
            IS_RUNNING_HTTPSERVER =  Boolean.parseBoolean(ConfigUtil.getValue("is_running_httpserver",null));//Boolean.parseBoolean(confProp.getProperty("is_running_httpserver"));
            IS_ORDER_SERVER =  Boolean.parseBoolean(ConfigUtil.getValue("is_order_server",null));//Boolean.parseBoolean(confProp.getProperty("is_order_server"));
           // HBASE_CUSTOM_URL =  ConfigUtil.getValue("hbase_custom_url",null);//confProp.getProperty("hbase_custom_url");
           // HBASE_DEVICE_URL =  ConfigUtil.getValue("hbase_device_url",null);//confProp.getProperty("hbase_device_url");

          //  DB_TIME_THRESHOLD =  Long.parseLong(ConfigUtil.getValue("db_time_threshold",null));//Long.parseLong(confProp.getProperty("db_time_threshold"));
            SERVICE_TIME_THRESHOLD =  Long.parseLong(ConfigUtil.getValue("service_time_threshold",null));//Long.parseLong(confProp.getProperty("service_time_threshold"));

           SYS_ERROR_MONITOR =  ConfigUtil.getValue("sys_error_monitor",null);//confProp.getProperty("sys_error_monitor");
            SYS_SERVICE_MONITOR =  ConfigUtil.getValue("sys_service_monitor",null);//confProp.getProperty("sys_service_monitor");
            //DOWNLOAD_UPDATE_MONITOR= ConfigUtil.getValue("download_update_monitor",null);//confProp.getProperty("download_update_monitor");
           // DOWNLOAD_MORE_DP_MONITOR= ConfigUtil.getValue("download_more_dp_monitor",null);//confProp.getProperty("download_more_dp_monitor");
           // DOWNLOAD_URL =  ConfigUtil.getValue("download_url",null);//confProp.getProperty("download_url");
           // MORE_DP_DOWNLOAD_URL =  ConfigUtil.getValue("more_dp_url",null);//confProp.getProperty("more_dp_url");


            /**
             * kafka
             */
            kafka_brokers = ConfigUtil.getValue("kafka_brokers",null);//confProp.getProperty("kafka_brokers");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRouteModule() {
        return routeModule;
    }

    public void setRouteModule(int routeModule) {
        this.routeModule = routeModule;
    }

    public int getServiceModule() {
        return serviceModule;
    }

    public void setServiceModule(int serviceModule) {
        this.serviceModule = serviceModule;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
