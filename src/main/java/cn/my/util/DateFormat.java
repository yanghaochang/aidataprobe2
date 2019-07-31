package cn.my.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

	public static final String DATE_MATCHING = "yyyy-MM-dd HH:mm:ss.ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ss");
	private static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
	private static Date d;
	private static Calendar c;
	private static String upload_day_time;
	private static int upload_hours_time;
	private static int upload_week_time;
	private static int pload_month_time;
	public static String getDateTimeFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	public static String getDateTimeFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static void main(String [] args) throws ParseException {
		while (true) {
			String now = DateFormat.getDateTimeFormat(DateFormat.DATE_MATCHING);
			System.out.println(now);


			d = sdf.parse(now);
			c = Calendar.getInstance();    //获取东八区时间
			c.setTime(d);
			upload_day_time = day.format(d);    //获取当前天数
			upload_hours_time = c.get(Calendar.HOUR_OF_DAY);       //获取当前小时
			upload_week_time = c.get(Calendar.WEEK_OF_YEAR);
			pload_month_time = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
			System.out.println(upload_day_time);
			System.out.println(upload_hours_time);
			System.out.println(upload_week_time);
			System.out.println(pload_month_time);
		}
	}

}
