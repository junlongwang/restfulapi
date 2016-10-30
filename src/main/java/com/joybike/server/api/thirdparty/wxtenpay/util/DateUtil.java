/* ========================================================
 * 北京五八信息�??术有限公司营运技术部�??发二�??
 * �?? 期：2011-2-28
 * �?? 者：李庆�??
 * �?? 本：0.1
 * =========================================================
 */
package com.joybike.server.api.thirdparty.wxtenpay.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author liqingguo
 * 
 * @version 0.1
 */
public class DateUtil {
	/** yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** yyyy-MM-dd */
	public static final String dtSimple = "yyyy-MM-dd";
	
	public final static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat formatterHMD = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static Log log = LogFactory.getLog(DateUtil.class);
	
	public static SimpleDateFormat getFormatter(String parttern) {
		return new SimpleDateFormat(parttern);
	}

	public static String parse(Date date, String pattern) {
		return getFormatter(pattern).format(date);
	}
	/**
	 * param timePot yyyy-MM-dd
	 * param toBeTestDate
	 * @throws Exception 
	 */
	public static boolean isBefore(String timePot, Date toBeTestDate) throws Exception {
		Date timePotDate = formatter.parse(timePot);
		return toBeTestDate.before(timePotDate);
	}
	/**
	 * param timePot 	yyyy-MM-dd
	 * param toBeTestDate
	 * @throws Exception 
	 */
	public static boolean isAfter(String timePot, Date toBeTestDate) throws Exception {
		Date timePotDate = formatter.parse(timePot);
		return toBeTestDate.after(timePotDate);
	}
	public static Date strToDate(String dateStr) {

		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(dateStr, pos);
		return strtodate;
	}

	/**
	 * 查询给定的两个日期相差的天数
	 * 
	 * @param start
	 *            �?始时�?
	 * @param end
	 *            结束时间
	 * @return 相差天数 当日期相差小
	 */
	public static long dayDiff(Date start, Date end) {
		java.sql.Date startdate = java.sql.Date.valueOf(new java.sql.Date(start
				.getTime()).toString());
		java.sql.Date enddate = java.sql.Date.valueOf(new java.sql.Date(end
				.getTime()).toString());
		long startday = TimeUnit.DAYS.convert(startdate.getTime(),
				TimeUnit.MILLISECONDS);
		long endday = TimeUnit.DAYS.convert(enddate.getTime(),
				TimeUnit.MILLISECONDS);
		return endday - startday;
	}

	public static long dayDiff(String start, String end) throws ParseException {
		Date startDate = formatterHMD.parse(start);
		Date endDate = formatterHMD.parse(end);
		java.sql.Date startdate = java.sql.Date.valueOf(new java.sql.Date(
				startDate.getTime()).toString());
		java.sql.Date enddate = java.sql.Date.valueOf(new java.sql.Date(endDate
				.getTime()).toString());
		return dayDiff(startdate, enddate);
	}

	/**
	 * @time:2012-01-14
	 * @description:获取日期:推算日期
	 * @param datetime
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayDate(String datetime, int days)
			throws ParseException {
		Date date = DateUtil.formatterHMD.parse(datetime);
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.DAY_OF_MONTH, days);
		return calen.getTime();
	}

	public static Date getFirstDayOfMonth(String dateTime)
			throws ParseException {
		Date date = DateUtil.formatterHMD.parse(dateTime);
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.set(Calendar.DAY_OF_MONTH, 1);
		return calen.getTime();
	}

	/**
	 * 获取输入日期当月的第�??�??
	 */

	/**
	 * @time:2012-01-14
	 * @description:获取日期:推算日期
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayDate(Date date, int days) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.DAY_OF_MONTH, days);
		return calen.getTime();
	}

	public static String getDateFormatString(Date date, String parttern) {
		SimpleDateFormat sdf = new SimpleDateFormat(parttern);
		return sdf.format(date);
	}

	public static String getDateSimpleFormatString(Date date) {
		return formatterHMD.format(date);
	}

	public static String getDateStandardFormatString(Date date) {
		return formatter.format(date);
	}

	public static void main(String[] args) throws ParseException {
		long day = dayDiff("2012-01-03", "2012-01-01");
		Date date = getDayDate("2012-01-03", -1);
		String start = "";
		String end = "2012-02-07";
		if (Math.abs(dayDiff(start, end)) > 0) {

		}

		// System.out.println(DateUtil.formatterHMD.format(date));
	}

	public static String getFormateDate(int days, SimpleDateFormat sdf) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(5, days);
		return sdf.format(calendar.getTime());
	}

	public static Date getDateMaxTime(Date date) {
		if (date == null) {
			return null;
		}
		String dateStr = formatterHMD.format(date) + " 23:59:59";
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dtSimple(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtSimple).format(date);
	}

	/**
	 * 获取格式
	 * 
	 * @param format
	 * @return
	 */
	public static DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}
}
