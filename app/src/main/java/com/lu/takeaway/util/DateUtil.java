package com.lu.takeaway.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String formateDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
	/**
	 * 
	 * @param 2015-10-14 05:25:21
	 * @return 10-14 05:25
	 */
	public static String orderDate(String timeString){
		return timeString.substring(timeString.indexOf("-")+1, timeString.lastIndexOf(":"));
	}
}
