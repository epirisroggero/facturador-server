package uy.com.tmwc.facturator.utils;

import java.util.Date;

public class DateUtils
{
  public static Date newDate(int year, int month, int day)
  {
    return new Date(year - 1900, month - 1, day);
  }
}