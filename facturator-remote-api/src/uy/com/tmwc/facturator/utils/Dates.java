package uy.com.tmwc.facturator.utils;

import java.util.Date;

public class Dates {
	@SuppressWarnings("deprecation")
	public static void addDaysToDate(Date date, int days) {
		date.setDate(date.getDate() + days);
	}

	@SuppressWarnings("deprecation")
	public static void addMonthsToDate(Date date, int months) {
		if (months != 0) {
			int month = date.getMonth();
			int year = date.getYear();

			int resultMonthCount = year * 12 + month + months;
			int resultYear = resultMonthCount / 12;
			int resultMonth = resultMonthCount - resultYear * 12;

			date.setMonth(resultMonth);
			date.setYear(resultYear);
		}
	}

	public static int getDaysBetween(Date start, Date finish) {
		start = copyDate(start);
		resetTime(start);
		finish = copyDate(finish);
		resetTime(finish);

		long aTime = start.getTime();
		long bTime = finish.getTime();

		long adjust = 3600000L;
		adjust = bTime > aTime ? adjust : -adjust;

		return (int) ((bTime - aTime + adjust) / 86400000L);
	}

	public static Date copyDate(Date date) {
		if (date == null) {
			return null;
		}
		Date newDate = new Date();
		newDate.setTime(date.getTime());
		return newDate;
	}

	@SuppressWarnings("deprecation")
	private static void resetTime(Date date) {
		long msec = date.getTime();
		msec = msec / 1000L * 1000L;
		date.setTime(msec);

		date.setHours(12);
		date.setMinutes(0);
		date.setSeconds(0);
	}

	@SuppressWarnings("deprecation")
	public static Date createDate(int year, int month, int day) {
		return new Date(year - 1900, month - 1, day);
	}

	@SuppressWarnings("deprecation")
	public static Date getPastMonth() {
		Date date = new Date();
		addMonthsToDate(date, -1);
		date.setDate(1);
		return date;
	}

	public static Date getLastDayOfMonth(int year, int month) {
		Date date = createDate(year, month, 1);
		addMonthsToDate(date, 1);
		addDaysToDate(date, -1);
		return date;
	}
}