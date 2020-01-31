package tn.com.smartsoft.commons.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	
	public static Timestamp getTimestamp(java.sql.Date date) {
		return new Timestamp(date.getTime());
	}
	
	public static Date getCourantDate() {
		Calendar calender = new GregorianCalendar();
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static Timestamp getCourantTimestamp(int n) {
		return new Timestamp(System.currentTimeMillis() + n * 1000);
	}
	
	public static Timestamp getCourantTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Date getCourantDateTime() {
		Calendar calender = new GregorianCalendar();
		return new Date(calender.getTimeInMillis());
	}
	
	public static  Date getDateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.parse(sdf.format(date), new ParsePosition(0));
	}
	
	public static String getDateFormatString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	
	public static Time getCourantHeure() {
		Calendar calender = new GregorianCalendar();
		return new Time(calender.getTime().getTime());
	}
	
	public static int getAnnees(Date date) {
		if (date == null)
			return 0;
		Calendar calender = new GregorianCalendar();
		calender.setTime(date);
		return calender.get(Calendar.YEAR);
	}
	
	public static java.sql.Date getDate(int year, int month, int date) {
		Calendar calender = new GregorianCalendar();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DAY_OF_MONTH, date);
		return new java.sql.Date(calender.getTimeInMillis());
	}
	
	public static int getMaxJourOfMois(Date date) {
		if (date == null)
			return 0;
		Calendar calender = new GregorianCalendar();
		calender.setTime(date);
		return calender.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static java.sql.Date getDateForEndDay(Date date) {
		if (date == null)
			return null;
		Calendar calender = new GregorianCalendar();
		calender.setTime(date);
		calender.set(Calendar.DAY_OF_MONTH, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new java.sql.Date(calender.getTimeInMillis());
	}
	
	public static java.sql.Date getDateForEndDay(int year, int month) {
		Calendar calender = new GregorianCalendar();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DAY_OF_MONTH, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new java.sql.Date(calender.getTimeInMillis());
	}
	
	public static java.sql.Date[] getPriode(int annee, int priodicite, int priode) {
		java.sql.Date dateDebutPriode = DateUtils.getDateForStartDay(annee, priode * priodicite - priodicite + 1);
		java.sql.Date dateFinPriode = DateUtils.getDateForEndDay(annee, priode * priodicite);
		return new java.sql.Date[] { dateDebutPriode, dateFinPriode };
	}
	
	public static java.sql.Date getDateForStartDay(int year, int month) {
		Calendar calender = new GregorianCalendar();
		calender.set(Calendar.YEAR, year);
		calender.set(Calendar.MONTH, month - 1);
		calender.set(Calendar.DAY_OF_MONTH, 1);
		return new java.sql.Date(calender.getTimeInMillis());
	}
	
	public static Timestamp getTimestampStartDate(Date date) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(getDateFormat(date));
		calender.set(Calendar.HOUR, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calender.getTimeInMillis());
	}
	
	public static Timestamp getTimestampEndDate(Date date) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(getDateFormat(date));
		calender.set(Calendar.HOUR, 23);
		calender.set(Calendar.MINUTE, 59);
		calender.set(Calendar.SECOND, 59);
		calender.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calender.getTimeInMillis());
	}
	
	public static int getMois(Date date) {
		if (date == null)
			return 0;
		Calendar calender = new GregorianCalendar();
		calender.setTime(date);
		return calender.get(Calendar.MONTH) + 1;
	}
	
	public static double convertInSecond(Long n) {
		if (n == null)
			return 0L;
		return NumberUtils.diviserNoArrondi(n, 60);
	}
	
	public static int getJours(Date date) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date);
		return calender.get(Calendar.DATE);
	}
	
	public static Time getHeure(Date date) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date);
		return new Time(calender.getTime().getTime());
	}
	
	public static boolean isEgale(Date date1, Date date2) {
		if (date1 == null)
			return date2 == null;
		else
			return getDateFormat(date1).equals(getDateFormat(date2));
	}
	
	public static boolean isInferieurStrict(Date date1, Date date2) {
		if (date1 == null)
			return false;
		return getDateFormat(date1).before(getDateFormat(date2));
	}
	
	public static boolean isSuperieurStrict(Date date1, Date date2) {
		if (date1 == null)
			return false;
		return getDateFormat(date1).after(getDateFormat(date2));
	}
	
	public static boolean isInferieurOuEgale(Date date1, Date date2) {
		return isInferieurStrict(date1, date2) || isEgale(date1, date2);
	}
	
	public static boolean isSuperieurOuEgale(Date date1, Date date2) {
		return isSuperieurStrict(date1, date2) || isEgale(date1, date2);
	}
	
	public static boolean isEntreStrict(Date date, Date startDate, Date endDate) {
		return isSuperieurStrict(date, startDate) && isInferieurStrict(date, endDate);
	}
	
	public static boolean isEntre(Date date, Date startDate, Date endDate) {
		return isEntreStrict(date, startDate, endDate) || isEgale(date, startDate) || isEgale(date, endDate);
	}
	
	public static Date ajouteJours(Date date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.DATE, n);
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static Timestamp ajouteJours(Timestamp date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.DATE, n);
		return new Timestamp(calender.getTimeInMillis());
	}
	
	public static Date soustraitJours(Date date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.DATE, -n);
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static Date ajouteMois(Date date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.MONTH, n);
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static Date ajouteSecond(Date date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.SECOND, n);
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static Date ajouteHeure(Date date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.HOUR_OF_DAY, n);
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static Date soustraitMois(Date date1, int n) {
		Calendar calender = new GregorianCalendar();
		calender.setTime(date1);
		calender.add(Calendar.MONTH, -n);
		return getDateFormat(new Date(calender.getTimeInMillis()));
	}
	
	public static long differenceMilisecondes(Date date1, Date date2) {
		long d1 = 0;
		long d2 = 0;
		Calendar calender1 = new GregorianCalendar();
		if (date1 != null)
			calender1.setTime(date1);
		Calendar calender2 = new GregorianCalendar();
		if (date2 != null)
			calender2.setTime(date2);
		if (calender1.getTime() != null)
			d1 = calender1.getTime().getTime();
		if (calender2.getTime() != null)
			d2 = calender2.getTime().getTime();
		long difference = d2 - d1;
		return difference;
	}
	
	public static long differenceJour(Date date2, Date date1) {
		return (long) (differenceMilisecondes(date1, date2) / (24 * 60 * 60 * 1000.0));
	}
	
	public static int differenceMois(Date date2, Date date1) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(date2);
		endCal.setTime(date1);
		return (endCal.get(Calendar.YEAR) * 12 + endCal.get(Calendar.MONTH)) - (startCal.get(Calendar.YEAR) * 12 + startCal.get(Calendar.MONTH));
	}
	
	public static void main(String[] args) throws Exception {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = f.parse("2012-01-23");
		Date d2 = f.parse("2012-02-02");
		int n = differenceMois(d1, d2);
		System.out.println(n);
	}
	
	public static long differenceEnJours(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays + 1;
	}
	
	public static long differenceEnJours(int year1, int month1, int day1, int year2, int month2, int day2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(year1, month1, day1);
		calendar2.set(year2, month2, day2);
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays + 1;
	}
	
	public static Timestamp getTimestamp(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, hourOfDay, minute, second);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	public static Date getAsDate(String target, String format) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat(format);
			dateformatter.setLenient(false);
			Date date = dateformatter.parse(target.toString(), new ParsePosition(0));
			if (date == null)
				return null;
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Date getAsDateUtil(String target, String format) {
		if (target == null || StringUtils.isBlank(target.toString()))
			return null;
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat(format);
			dateformatter.setLenient(false);
			Date date = dateformatter.parse(target.toString(), new ParsePosition(0));
			return date;
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getMaxDayInMonth(String date) throws Exception {
		GregorianCalendar sDate = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		sDate.setTime(df.parse(date));
		return String.valueOf(sDate.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
	}
	
	public static Timestamp getTimestamp(String date, String time, String format) {
		if (StringUtils.isNotBlank(time))
			date = date + " " + time;
		else
			date = date + " 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dt = sdf.parse(date, new ParsePosition(0));
		return new Timestamp(dt.getTime());
	}
	
	public static Timestamp getTimestamp(String date, String time) {
		return getTimestamp(date, time, "yyyy-MM-dd HH:mm:ss");
	}
}