package com.quifeng.utils.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc   日期工具类
 * @author JZH
 * @time   2020-12-20
 */
public class DateUtils {
//	public static void main(String[] args) throws ParseException {
//		System.out.println(differTime("2020年12月20日 20:00:00", 5));
//	}
	public static void main(String[] args) {
		
		System.out.println((System.currentTimeMillis()-1608720402230l)/1000/60/60);
	}
	/**
	 * @desc  求两个日期相差的天数
	 * @param strDate
	 * @param strDate2
	 * @return
	 * @throws ParseException 
	 */
	public static int differentDaysByMillisecond(String strDate,String strDate2) throws ParseException{
		//1.初始化日期格式化类
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//2.将传入的字符串类型的日期转变为日期类型
		Date fDate = format.parse(strDate);
		Date oDate = format.parse(strDate2);
		//3.初始化日历工具类
		Calendar aCalendar = Calendar.getInstance();
		//4.将上面获取到的日期转变为天
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		
		return day2-day1;
	}
	
	/**
	 * 获取当前时间 格式：yyyy年MM月dd日 HH:mm:ss
	 * @return
	 */
	public static final String CurrentYMDHSMTime() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		java.util.Date currentDate = new java.util.Date();
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}
	
	/**
	 * 判断是否超过指定分钟
	 * @param strTime 开始时间
	 * @param min 指定分钟数
	 * @return  返回1：超过        返回0：没超过
	 * @throws ParseException
	 */
	public static int differTime(String strTime,int min) throws ParseException{
		//初始化日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//字符串变为日期类型
		Date date = format.parse(strTime);
		//如果时间不超过5分钟
		if(new Date().getTime() - date.getTime() < (min*60*1000)){
			return 0;
		}
		//超过
		return 1;
	}
	/**
	 * 根据毫秒返回时间 大于7天返回具体时间、大于1天返回几天、小于一天返回小时
	 * @param lo
	 * @return
	 */
	public static String MillToTime(String lo){
		long time = Long.parseLong(lo);
		//大于7天
		if(System.currentTimeMillis()-time > 604800000){
			Date date = new Date(time);
	        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	        return sd.format(date);
		}
		//大于1天
		else if(System.currentTimeMillis()-time > 86400000){
			return Math.abs(time/1000/60/60/24)+"天前";
		}
		//小于一天
		else if(System.currentTimeMillis()-time > 3600000){
			return Math.abs((System.currentTimeMillis()-time)/1000/60/60)+"小时前";
		}
		return "不到1小时前";
		
		
        
	}
}
