package htos.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	public static String DateConversionString(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String str = sdf.format(date);
	    return str;
	  }

	  public static String DateConversion8String(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String str = sdf.format(date);
	    return str;
	  }

	  public static String DateConversion14String(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String str = sdf.format(date);
	    return str;
	  }

	  public static String DateConversion19String(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = sdf.format(date);
	    return str;
	  }

	  public static String getDatecurrentTime()
	  {
	    long time = System.currentTimeMillis();
	    String datetime = new Long(time).toString();
	    return datetime;
	  }

	  public static String StringConversionString(String date)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    String str = null;
	    try {
	      str = sdf.format(sdf.parse(date));
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String StringConversion19String(String date)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String str = null;
	    try {
	      str = sdf.format(sdf.parse(date));
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String StringConversion14String(String date)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    String str = null;
	    try {
	      str = sdf.format(sdf.parse(date));
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static Date StringConversionDate(String dateString)
	  {
	    Date str = null;
	    if (StringUtil.isEmpty(dateString)) {
	      return str;
	    }
	    SimpleDateFormat sdf = null;
	    if (dateString.length() == 8) {
	      sdf = new SimpleDateFormat("yyyyMMdd");
	    } else if (dateString.length() == 10) {
	      sdf = new SimpleDateFormat("yyyy-MM-dd");
	    } else if (dateString.length() == 14) {
	      sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    } else if (dateString.length() == 19) {
	      dateString = dateString.replace("T", " ").replaceAll("/", "-");
	      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    } else {
	      sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
	    }
	    try {
	      str = sdf.parse(dateString);
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static Date StringDate(String dateString) {
	    Date str = null;
	    if (StringUtil.isEmpty(dateString)) {
	      return str;
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    dateString = dateString.replaceAll("/", "-");
	    try {
	      str = sdf.parse(dateString);
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String StringConversion10String(String date)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String str = null;
	    if (StringUtil.isEmpty(date))
	      return str;
	    try
	    {
	      str = sdf.format(sdf.parse(date));
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static int daysBetween(String smdate, String bdate)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
	    try {
	      cal.setTime(sdf.parse(smdate));
	      long time1 = cal.getTimeInMillis();
	      cal.setTime(sdf.parse(bdate));
	      long time2 = cal.getTimeInMillis();
	      long between_days = (time2 - time1) / 86400000L;

	      return Integer.parseInt(String.valueOf(between_days));
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }return 0;
	  }
	  
	  public static int daysBetween(Date smdate, Date bdate)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(smdate);
	    long time1 = cal.getTimeInMillis();
	    cal.setTime(bdate);
	    long time2 = cal.getTimeInMillis();
	    long between_days = (time2 - time1) / 86400000L;

	    return Integer.parseInt(String.valueOf(between_days));
	    
	  }

	  public static String DateConversionString9CH(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yy年MM月dd日");
	    String str = sdf.format(date);
	    return str;
	  }

	  public static String DateString6CH(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	    String str = sdf.format(date);
	    return str;
	  }

	  public static String DateConversionString11CH(Date date)
	  {
	    if (date == null) {
	      return "";
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    String str = sdf.format(date);
	    return str;
	  }
	  
	  public static boolean getCompareToDate(String date1, String date2)
	  {
	    boolean flag = false;
	    if ((!StringUtil.isEmpty(date1)) && (!StringUtil.isEmpty(date2))) {
	      return date1.compareTo(date2) >= 0;
	    }
	    return flag;
	  }
}
