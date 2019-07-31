package cn.my.server.common;

import java.util.ArrayList;
import java.util.List;

public class EventType {
	/**
	 * event_type
	 */
	
	private static final String app_end="app_end";
	private static final String log = "log";
	private static final String cold_start = "cold_start";
	private static final String quit = "quit";
	private static final String backgroud = "backgroud";
	private static final String hot_start = "hot_start";
	private static final String register = "register";
	private static final String login = "login";
	private static final String exit = "exit";
	private static final String home = "home";
	private static final String show = "show";
	private static final String click = "click";
	private static final String scroll = "scroll";
	private static final String view_page = "view_page";
	private static final String custom = "custom";
	//业务后台数据
	private static final String backstage="backstage";
	//第三方数据
	private static final String third_party="third_party";
	//topic定义前缀
	public static final String topic = "tpc_";
	//其他数据
	private static final String other_data="other_data";
	
	public static List<String> event_type_list=new ArrayList<String>();
	
	static{
		event_type_list.add(app_end);
		event_type_list.add(log);
		event_type_list.add(cold_start);
		event_type_list.add(quit);
		event_type_list.add(backgroud);
		event_type_list.add(hot_start);
		event_type_list.add(register);
		event_type_list.add(login);
		event_type_list.add(exit);
		event_type_list.add(home);
		event_type_list.add(show);
		event_type_list.add(click);
		event_type_list.add(scroll);
		event_type_list.add(view_page);
		event_type_list.add(custom);
		event_type_list.add(backstage);
		event_type_list.add(third_party);
		event_type_list.add(other_data);
	}
	
	
}
