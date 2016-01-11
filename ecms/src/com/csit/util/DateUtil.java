package com.csit.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:日期处理函数
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-7
 * @author lys
 * @vesion 1.0
 */
public class DateUtil {

	/**
	 * @Description: 计算两日期相差的天数
	 * @Create: 2012-12-21 下午3:45:44
	 * @author lys
	 * @update logs
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static  int daysOfTwo(Date fDate, Date oDate) {
	       Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(fDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return day2 - day1;
    }
	/**
	 * @Description: 按pattern格式化输出Date
	 * @Create: 2012-12-21 下午3:53:48
	 * @author lys
	 * @update logs
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	/**
	 * @Description: 日期按yyyy-MM-dd格式化
	 * @Create: 2012-12-21 下午3:55:39
	 * @author lys
	 * @update logs
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return dateToString(date,"yyyy-MM-dd");
	}
	
	/**
	 * @Description: 将类型是pattern的日期字符串转化成Date型数据
	 * @Create: 2013-1-14 下午11:58:24
	 * @author lys
	 * @update logs
	 * @param pattern
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String pattern,String dateString) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(dateString);
	}
	/**
	 * @Description:将类型是yyyy-MM-dd的日期字符串转化成Date型数据 
	 * @Create: 2013-1-14 下午11:59:34
	 * @author lys
	 * @update logs
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String dateString) throws ParseException{
		return toDate("yyyy-MM-dd",dateString);
	}
	/**
	 * @Description: 取得当前时间的Timestamp
	 * @Created Time: 2013-4-16 下午4:40:59
	 * @Author lys
	 * @return
	 */
	public static Timestamp getNowTimestamp(){
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	
	/**
	 * @Description: 取得当前时间的Timestamp,由于SqlServer 时间的精度为1/300秒
	 * 将 datetime 值舍入到 .000、.003、或 .007 秒的增量 http://www.fengfly.com/plus/view-172343-1.html
	 * @Created Time: 2013-4-16 下午4:40:59
	 * @Author lys
	 * @return
	 */
	public static Timestamp getNowTimestampSqlServer(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000");
		Date now = Calendar.getInstance().getTime();
		try {
			return new Timestamp(df.parse(df.format(now)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
