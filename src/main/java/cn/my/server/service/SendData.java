package cn.my.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.my.server.common.EventType;
import cn.my.server.dao.SendKafkaUtils;
import cn.my.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SendData{
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");
    private static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger logger = LoggerFactory.getLogger(SendData.class);
//    private static final Log ERRORMESSAGE = LogFactory.getLog("errorMessage");
//    private static final Log intp = LogFactory.getLog("intp");



    public  static void hdfsData(JSONObject requestObj,String now) throws Exception{
        String str;
         JSONObject common;
        JSONArray events;
         JSONObject event;
         Date d;
        Calendar c;
          String upload_day_time;
          int upload_hours_time;
          int upload_week_time;
          int pload_month_time;
          String event_type;
          JSONObject spark_json;
          String upload_time;
        try {
            String check_id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            common = requestObj.getJSONObject("common");
            events = requestObj.getJSONArray("events");
            if (events != null && events.size() > 0) {

                for (int i = 0; i < events.size(); i++) {
                    spark_json = new JSONObject();
                    event = events.getJSONObject(i);
                    event_type = event.getString("event_type");
                    spark_json.putAll(common);
                    spark_json.putAll(event);


                    spark_json.put("sdk_time", now);
                    spark_json.put("check_id", check_id);

                    //upload_time = event.getString("ts");
                    //spark_json.put("upload_time", upload_time);


                    d = sdf.parse(now);
                    c = Calendar.getInstance();    //获取东八区时间
                    c.setTime(d);
                    upload_day_time = day.format(d);    //获取当前天数
                    upload_hours_time = c.get(Calendar.HOUR_OF_DAY);       //获取当前小时
                    upload_week_time = c.get(Calendar.WEEK_OF_YEAR);
                    pload_month_time = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
                    spark_json.put("upload_day_time", upload_day_time);
                    spark_json.put("upload_hour_time", upload_hours_time);
                    spark_json.put("upload_week_time", upload_week_time);
                    spark_json.put("upload_month_time", pload_month_time);

                    if (!spark_json.containsKey("uuid")) {
                        spark_json.put("uuid", "");
                    }
                    if (!spark_json.containsKey("user_id")) {
                        spark_json.put("user_id", "");
                    }
                    if (!spark_json.containsKey("upload_time")) {
                        spark_json.put("upload_time", "");
                    }
//                if(EventType.event_type_list.contains(event_type)){
                    if (StringUtils.isEmpty(spark_json.get("event_type")) || StringUtils.isEmpty(spark_json.get("event_code"))) {
                        logger.error("json数据格式错误：" + spark_json);
                    } else {
                        SendKafkaUtils.toKafka(EventType.topic + common.getString("product_name"), spark_json.toJSONString());
                    }
//                    try {
//
//                    new Thread() {
//                        public void run() {
//
//                        }
//                    }.start();
//                    } catch (Exception e) {
//                        logger.error("hdfsData kafka存储错误：" + spark_json);
//                    }
                }
            }
        }
        catch (Exception e){
            logger.error(e.getMessage(),requestObj);
        }
    }


    public static void hdfsOtherData(JSONObject requestObj,String now) throws ParseException{
        String event_type = requestObj.getString("event_type");
//        if(EventType.event_type_list.contains(event_type)){
            requestObj.put("id", UUIDUtils.getUUID20());
            requestObj.put("dt", now);
            try{
                JSONObject common=requestObj.getJSONObject("event_type");;
                SendKafkaUtils.toKafka(common.getString("product_name"), requestObj.toJSONString());
//                new Thread() {
//                    public void run() {
//                        this.toKafka(common.getString("product_name"), requestObj.toJSONString());
//                    }
//                    private void toKafka(String product_name, String toJSONString) {
////                        long startTime = System.currentTimeMillis();
//                        SendKafkaUtils.toKafka(EventType.topic+product_name, toJSONString);
////                        long time = System.currentTimeMillis() - startTime;
////                        logger.info("kafka存储耗时：" + time);
//                    }
//                }.start();
            }catch(Exception e){
                logger.error("hdfsOtherData kafka存储错误：" + requestObj);
            }
    }

    public static void main(String[] args) throws ParseException {
        String str = "{ \"uuid\": \"uuid\", \"common\":{ \"message_id\":  \"1 \" }, \"events\":[{  \"event_code\": \"test\" }]}";
        System.out.println(str);
        JSONObject requestObj = JSONObject.parseObject(str);
        System.out.println(requestObj);
        //SendData.hdfsData(requestObj);
        boolean bl = requestObj.containsKey("uuid");
        System.out.println(bl);

/*		  String upload_time = "2019-01-02 19:11:12.11";
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");
		  Date d = sdf.parse(upload_time);
		  Calendar c = Calendar.getInstance();    //获取东八区时间
		  c.setTime(d);
		  String upload_day_time = day.format(d);    //获取当前天数
		  int upload_hours_time = c.get(Calendar.HOUR_OF_DAY);       //获取当前小时
		  int upload_week_time = c.get(Calendar.WEEK_OF_YEAR);
		  int pload_month_time = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份

		  System.out.println(upload_day_time);
		  System.out.println(upload_hours_time);
		  System.out.println(upload_week_time);
		  System.out.println(pload_month_time);*/

    }


}