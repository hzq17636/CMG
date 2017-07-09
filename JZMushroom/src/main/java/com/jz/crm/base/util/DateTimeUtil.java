package com.jz.crm.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.exception.ApplicationException;

/**
 * Process datetime util class.
 * 
 * @author lizeyi 2014-04-09
 */
public class DateTimeUtil {

	public DateTimeUtil() {
	}

	public static String now(String pattern) {
		return dateToString(new Date(), pattern);
	}

	public static String dateToString(Date date, String pattern, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
		return sdf.format(date);
	}

	public static String dateToString(Date date, String pattern) {
		Locale locale = Locale.getDefault();
		return dateToString(date, pattern, locale);
	}

	public static Date stringToDate(String strDate, String pattern, Locale locale) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
		return simpleDateFormat.parse(strDate);
	}

	public static Date stringToDate(String strDate, String pattern) throws Exception {
		if (strDate == null || "".equals(strDate)) {
			return null;
		}
		Locale locale = Locale.getDefault();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
		return simpleDateFormat.parse(strDate);
	}

	public static long stringToLong(String strDate, String pattern, Locale locale) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
		Date date = simpleDateFormat.parse(strDate);
		return date.getTime();
	}

	public static long stringToLong(String strDate, String pattern) throws ParseException {
		Locale locale = Locale.getDefault();
		return stringToLong(strDate, pattern, locale);
	}

	public static String getSystemDateForStartDate() {
		Date utilDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(utilDate);
		return "to_date('" + str + "','yyyy-mm-dd hh24:mi:ss')";
	}

	public static String getSystemDateForEndDate() {
		Date utilDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(utilDate);
		return "to_date('" + str + "','yyyy-mm-dd hh24:mi:ss')";
	}

	public static String getDateStrBySubDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 0 - day);
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantContext.TIME_FORMAT);
		String time = sdf.format(calendar.getTime());
		return time;
	}

	public static String getSystemTimesTampForStartDate() {
		Date utilDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000000");
		String str = sdf.format(utilDate);
		return "to_timestamp('" + str + "','yyyy-mm-dd hh24:mi:ssxff')";
	}

	public static String getSystemTimesTampForEndDate() {
		Date utilDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.999999");
		String str = sdf.format(utilDate);
		return "to_timestamp('" + str + "','yyyy-mm-dd hh24:mi:ssxff')";
	}

	public static String getJobTimeTimeZone(Date date) {
		/*
		 * begin dbms_scheduler.create_job('WELL.JOB_TIMEZONE',
		 * 'STORED_PROCEDURE', 'p_test', start_date =>
		 * to_timestamp_tz('Europe/Luxembourg 07:55:00 26-NOV-2012', 'TZR
		 * HH24:MI:SS DD-MON-RRRR'), enabled => true, repeat_interval => null);
		 * end; / select to_timestamp_tz('Asia/Shanghai 2012-12-06
		 * 09:00:00','TZR YYYY-MM-DD HH24:MI:SS') from dual;
		 */
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String getJobTime = TimeZone.getDefault().getID() + ' ' + df.format(date);
		return getJobTime;
	}

	public static String getExportTicketDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static int compareDate(String date1, String date2, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return -1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return 1;
			} else if (dt1.getTime() == dt2.getTime()) {
				return 2;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static int compareDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat(ConstantContext.TIME_FORMAT);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return -1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return 1;
			} else if (dt1.getTime() == dt2.getTime()) {
				return 2;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static int compareDate(Date dt1, Date dt2) {
		try {
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return -1;
			} else if (dt1.getTime() == dt2.getTime()) {
				return 2;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static boolean compareCurrentDate(String time, int hour) {
		DateFormat df = new SimpleDateFormat(ConstantContext.TIME_FORMAT);
		Date beginTime;
		try {
			beginTime = df.parse(time);
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(beginTime);
			calendar.add(Calendar.HOUR_OF_DAY, hour);
			if (calendar.getTime().getTime() > now.getTime()) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean isValidStartTime(String startTime, int days) throws Exception {
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat(ConstantContext.TIME_FORMAT);
		String nowDate = format.format(d);

		Calendar ca = Calendar.getInstance();
		ca.setTime(DateTimeUtil.stringToDate(startTime, ConstantContext.DATE_FORMAT));
		ca.add(Calendar.DATE, days);// 30为增加的天数，可以改变的
		d = ca.getTime();
		String backTime = format.format(d);

		int result = compareDate(backTime, nowDate);
		if (result == 1) {
			return false;
		}
		return true;
	}

	public static int getSubDay(Date now, Date date) {
		int days = (int) ((now.getTime() - date.getTime()) / 86400000);
		return days;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(DateTimeUtil.dateToString(DateTimeUtil.stringToDate("201410",
		// "yyyyMM"),"yyyy"));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		simpleDateFormat.parse("Mon Nov 14 00:00:00 CST 2022");
		Date tmp = new Date();
		System.out.println(tmp.getTime());

		Date date = new Date(1442306212681L);
		// System.out.println(DateTimeUtil.stringToDate("Mon Nov 14 00:00:00 CST 2022",
		// "EEE MMM dd HH:mm:ss Z yyyy"));

	}

}
