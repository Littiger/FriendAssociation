package com.turing.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc   日期工具类：计算两个日期之间的相差的天数
 * @author WYH
 * @time   2020-12-14
 */
public class DateUtils {

	/*public static void main(String[] args) throws ParseException {

		String strDate="2019-12-16";
		Date now=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(DateUtils.differentDaysByMillisecond(format.format(now),strDate ));
		int result=DateUtils.differentDaysByMillisecond(format.format(now),strDate );
		if ((result>=0)&&(result<=5))
		{
			System.out.println("提前送上生日的祝福...祝你年年有今日，岁岁有今朝");
		}
		else
		{
			System.out.println("不用发");
		}
		
	}*/

/**
 * @desc  最定义函数--求两个日期相差的天数
 * @param strDate
 * @param strDate2
 * @return
 * @throws ParseException 
 */
public static int differentDaysByMillisecond(String strDate,String strDate2) throws ParseException
{
	//1.初始化日期格式化类
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	//2.将传入的字符串类型的日期转变为日期类型
	 Date fDate = format.parse(strDate);//开始日期
	 Date oDate = format.parse(strDate2);//目标日期

	//3.初始化日历工具类
	 Calendar aClaendar = Calendar.getInstance();
	
	 //4.将上面获取到的日期转变为天
	 aClaendar.setTime(fDate);
	 int day1 = aClaendar.get(Calendar.DAY_OF_YEAR);
	 
	 aClaendar.setTime(oDate);
	 int day2 = aClaendar.get(Calendar.DAY_OF_YEAR);

     return day2-day1;
}
	
	
}
