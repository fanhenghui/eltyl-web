/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eltyl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTimeUtil {
	public static Long strToTime(String user_time) {  
		Long re_time = null;  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date d;  
		try {  
			d = sdf.parse(user_time);  
			long l = d.getTime();  
			String str = String.valueOf(l);  
			re_time = Long.parseLong(str.substring(0, 10));  
		} catch (ParseException e) {  
			e.printStackTrace();  
		}  
		return re_time;  
	} 	  
	// 将时间戳转为字符串  
	public static String timeToStr(Long cc_time) {
		if(cc_time==null||cc_time==0)return "";
		String re_StrTime = null;  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		re_StrTime = sdf.format(new Date(cc_time * 1000L));  
		return re_StrTime;  	  
	}  
   
}
