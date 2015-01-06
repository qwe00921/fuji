package com.fsti.fjdicClient.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.TextUtils;

public class DateUtils {

    public final static String  y_mm_dd   = "yyyy-MM-dd";
    public final static String  y_m_d     = "yyyy-M-d";
    public final static String  y_m_d_hm  = "yyyy-MM-dd HH:mm";
    public final static String  y_m_d_hms = "yyyy-MM-dd HH:mm:ss";
    private final static String m_d_hm    = "MM-dd HH:mm";
    public final static String  h_m       = "HH:mm";
    public final static String  h_m_s     = "HH:mm:ss";
    public final static String  ymd       = "yyyy年MM月dd日";
    public final static String  ymd2      = "yyyy年M月d日";
    public final static String  y_m       = "yyyy年M月";
    public final static String  ymdAndhm  = "yyyy年MM月dd日 HH:mm";
    public final static String  mmdd      = "MM月dd日";
    public final static String  m_d       = "MM/dd";

    /**
     * @功能描述: TODO String日期截取友好显示
     * @修改: qius
     */
    public static String dateToString(String date) {
        if (StringUtils.isEmpty(date))
            return "";
        return date.split(" ")[0].replaceFirst("-", "年").replaceFirst("-", "月") + "日";
    }

    /**
     * @功能描述: TODO String日期截取时分友好显示
     * @修改: qius
     */
    public static String dateToStringHours(String date) {
        if (StringUtils.isEmpty(date))
            return "";
        return date.split(" ")[1].substring(0, 5);
    }

    public static String stringToData(String date) {
        if (StringUtils.isEmpty(date))
            return "";
        return date.replaceFirst("年", "-").replaceFirst("月", "-").replaceFirst("日", " ");
    }

    /**
     * 字符串 "yyyy年MM月dd日 HH:mm";
     */
    public static boolean formatDate(String sdate, String strformat) {
        SimpleDateFormat format = new SimpleDateFormat(strformat, Locale.getDefault());
        format.setLenient(false);// 这个的功能是不把1996-13-3 转换为1997-1-3
        Date date = null;
        try {
            date = format.parse(sdate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 日期之间的格式转换
     */
    public static String strFormatToOtherFormat(String sdate, String curformat, String chancedformat) {
        if (StringUtils.isEmpty(sdate))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(curformat, Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(sdate);
        } catch (ParseException e) {
        }
        // 有异常要捕获
        format = new SimpleDateFormat(chancedformat, Locale.getDefault());
        String newD = format.format(date);
        return newD;
    }

    /**
     * 字符串转日期
     */
    public static String strformatDate(String strdate, String strformat) {
        SimpleDateFormat format = new SimpleDateFormat(strformat, Locale.getDefault());
        format.setLenient(false);// 这个的功能是不把1996-13-3 转换为1997-1-3
        return format.format(strdate);
    }

    /**
     * 日期转字符串
     */
    public static String dateformatStr(Date strdate, String strformat) {
        SimpleDateFormat sdf = new SimpleDateFormat(strformat, Locale.getDefault());
        return sdf.format(strdate);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static String dateToStr(String strDate) {

        SimpleDateFormat sdfx = new SimpleDateFormat(ymd);
        SimpleDateFormat sdfh = new SimpleDateFormat(y_mm_dd);

        String nowTime2 = "";
        try {
            nowTime2 = sdfh.format(sdfx.parse(strDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nowTime2;
    }

    /**
     * @功能描述: 根据生日日期获取年龄
     * @修改: Q
     */
    public static int getIntByDate(String date, String format) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(format, Locale.getDefault());
            String currentTime = formatDate.format(new Date().getTime());
            Date today = formatDate.parse(currentTime);
            Date brithDay = null;
            try {
                brithDay = formatDate.parse(date);
            } catch (ParseException e) {
                brithDay = today;
            }
            return today.getYear() - brithDay.getYear();
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getResidueCar(String date, String serviceTime, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(serviceTime))
            return 0;
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(format, Locale.getDefault());
            Date today = formatDate.parse(serviceTime);
            Date brithDay = null;
            try {
                brithDay = formatDate.parse(date);
            } catch (ParseException e) {
                brithDay = today;
            }
            return today.getYear() - brithDay.getYear();
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getSubtractDate(String time1, String time2, String format) {
        if (TextUtils.isEmpty(time1)) {
            return 0;
        }
        if (TextUtils.isEmpty(time1)) {
            return 0;
        }
        SimpleDateFormat df_date = new SimpleDateFormat(format, Locale.getDefault());
        Date date1;
        Date date2;
        long hour = 0;
        try {
            date1 = df_date.parse(time1);
            date2 = df_date.parse(time2);
            hour = (date1.getTime() - date2.getTime());
        } catch (ParseException e) {
        }
        return hour;
    }

    /**
     * @方法名称: getBirthdateByAge
     * @功能描述: 根据年龄获取出生年份
     * @参数 @param ages
     * @参数 @return
     * @返回类型 String
     * @调用举例:
     * @修改: Q
     */
    public static String getBirthdateByAge(String ages) {
        Calendar Currentcalendar = Calendar.getInstance();// 获取现在时间
        String year = String.valueOf(Currentcalendar.get(Calendar.YEAR));// 获取年份
        // 用文本框输入年龄
        int age = 0;
        try {
            age = NumberUtil.parseInt(ages);
        } catch (NumberFormatException e) {
        }
        int birth = NumberUtil.parseInt(year) - age;
        return birth + "01-01";
    }

    public static Calendar getCalendarByDate(String date, String dateformat) {
        if (StringUtils.isEmpty(date)) {
            return Calendar.getInstance();
        }
        SimpleDateFormat df = new SimpleDateFormat(dateformat, Locale.getDefault());
        Date dt1;
        try {
            dt1 = df.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt1);
            return calendar;
        } catch (ParseException e) {
        }
        return Calendar.getInstance();
    }

    /**
     * 根据日期获得星期
     * 
     * @param date
     * @return
     */
    public static String getWeekOfDate(String date, String dateformat) {
        if (StringUtils.isEmpty(date)) {
            return "";
        }
        String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        DateFormat df = new SimpleDateFormat(dateformat);
        Date dt1;
        try {
            dt1 = df.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt1);
            int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            return weekDaysName[intWeek];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getSubtractBydate(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        DateFormat df_date = new SimpleDateFormat(y_m_d_hms, Locale.getDefault());
        Date date;
        long hour = 0;
        try {
            date = df_date.parse(time);
            hour = (new Date().getTime() - date.getTime()) / (60 * 60 * 1000);
        } catch (ParseException e) {
        }
        return hour;
    }

    public static String getCurrentDateFormat(String format) {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat(format, Locale.getDefault()).format(cal.getTime());

    }

    /**
     * 函数名称 : getCurrentDateLaterHalf 功能描述 : 参数及返回值说明：
     * 
     * @param format
     * @return 修改记录： 日期：2014年7月14日 上午10:19:34 修改人：Q 描述 ：获取后半小时
     */
    public static String getCurrentDateLaterHalf(String format) {
        Date date = new Date();
        long time = (date.getTime() / 1000) + 60 * 30;
        date.setTime(time * 1000);
        return getDateFormat(date, format);

    }

    /**
     * 函数名称 : getDateNearByNum 功能描述 : 参数及返回值说明：
     * 
     * @param format
     * @return 修改记录： 日期：2014年7月14日 上午10:19:34 修改人：Q 描述 ：获取当前(num)临近日期
     */
    public static String getDateNearByNum(int num, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num);
        return new SimpleDateFormat(format, Locale.getDefault()).format(cal.getTime());

    }

    public static String getDateFormat(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);

    }

    public static String getDateFormat(String datestr, String format) {
        Date date = null;
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat(y_m_d_hms);
            date = sdf.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat(format, Locale.getDefault()).format(date);

    }

    public static int compareDateToDate(String dates, String otherdate, String format) {
        if (StringUtils.isEmpty(dates)) {
            return -1;
        }
        if (StringUtils.isEmpty(otherdate)) {
            return 1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date one = sdf.parse(dates);
            Date other = sdf.parse(otherdate);
            return one.compareTo(other);
        } catch (ParseException e) {
            return -1;
        }
    }

    public static int compareCurrentDateToLaterNumDate(int num, String dates, String format) {
        if (StringUtils.isEmpty(dates)) {
            return -1;
        }
        Date numdate = new Date();
        numdate.setMinutes(numdate.getMinutes() + num);
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date one = sdf.parse(dates);
            return one.compareTo(numdate);
        } catch (ParseException e) {
            return -1;
        }
    }

    public static int compareCurrentDate(String date, String format) {
        return compareDateToDate(date, getCurrentDateFormat(format), format);
    }

    /**
     * 取yyyy-MM-dd HH:mm:ss时间
     * 
     * @return
     */
    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(y_m_d_hms, Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    public static String getTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    public static String getStartTime(String time) {
        return stringToData(time) + getTime().split(" ")[1];
    }

    public static String getStartTime(int year, int mouth, String day) {
        return year + "-" + (mouth > 9 ? mouth : ("0" + mouth)) + "-" + (NumberUtil.parseInt(day) > 9 ? day : ("0" + day)) + " " + getTime().split(" ")[1];
    }

    /**
     * @方法名称: formatDateTime
     * @功能描述: 格式化时间（今天：早上 8：11，下午 13：12，晚上19：11；昨天：昨天 8：11等）
     * @param time
     *            时间
     * @参数 @return
     * @返回类型 String
     * @调用举例:
     * @修改: Q
     */

    public static String formatDateTime(String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat(y_m_d_hm, Locale.getDefault());
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
            time = format.format(date);
        } catch (ParseException e) {
            return "";
        }
        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); // 今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            int hour = current.get(Calendar.HOUR_OF_DAY);
            if (hour >= 0 && hour < 6) {
                return "凌晨" + time.split(" ")[1];
            } else if (hour >= 6 && hour < 12) {
                return "早上 " + time.split(" ")[1];
            } else if (hour >= 12 && hour < 13) {
                return "中午 " + time.split(" ")[1];
            } else if (hour >= 13 && hour < 19) {
                return "下午 " + time.split(" ")[1];
            } else if (hour >= 19 && hour <= 23) {
                return "晚上 " + time.split(" ")[1];
            }
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
            int index = time.indexOf("-") + 1;
            return time.substring(index, time.length());
        }
        return time;
    }

    /**
     * 后一天
     * 
     * @param today
     * @return
     */
    public static String addOneday(String today) {
        SimpleDateFormat f = new SimpleDateFormat(y_mm_dd);
        try {
            Date d = new Date(f.parse(today).getTime() + 24 * 3600 * 1000);
            return f.format(d);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 前一天
     * 
     * @param date
     * @return
     */
    public static String subOneday(String date, boolean iscompare) {
        SimpleDateFormat f = new SimpleDateFormat(y_mm_dd);
        try {
            Date date1 = f.parse(date);
            date1.setDate(date1.getDate() - 1);
            Date currendate = f.parse(getCurrentDateFormat(y_mm_dd));
            if (iscompare) {
                if (date1.compareTo(currendate) < 0) {
                    return date;
                }

            }
            // Date d = new Date(f.parse(date).getTime() - (24 * 3600 * 1000));
            return f.format(date1);
        } catch (Exception ex) {
            return "";
        }
    }

    public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat(y_m_d_hms);
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }

    public static int getMinute(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime))
            return 0;
        long startT = fromDateStringToLong(startTime);
        long endT = fromDateStringToLong(endTime);

        long ss = (endT - startT) / (1000); // 共计秒数
        int MM = (int) ss / 60; // 共计分钟数
        // int hh = (int) ss / 3600; // 共计小时数
        // int dd = (int) hh / 24; // 共计天数
        // System.out.println("共" + dd + "天 准确时间是：" + hh + " 小时 " + MM + " 分钟" + ss + " 秒 共计：" + ss * 1000 + " 毫秒");
        return MM;
    }

    public static String getTimeByThms(long time) {

        // long ss = (endT - startT) / (1000); // 共计秒数
        int date = (int) (time / (24 * 60 * 60));
        int hh = (int) (time % (24 * 60 * 60)) / (60 * 60);
        int MM = (int) (time % (24 * 60 * 60) % (60 * 60) / 60);
        int ss = (int) (time % (24 * 60 * 60) % (60 * 60) % 60); // 共计分钟数
        StringBuilder sbuild = new StringBuilder();
        return sbuild.append(date).append("天").append(hh).append("小时").append(MM).append("分钟").append(ss).append("秒").toString();
    }

    public static String addTimes(String starttime, int hour, int minute) {
        SimpleDateFormat format = new SimpleDateFormat(y_m_d_hms, Locale.getDefault());
        if (StringUtils.isEmpty(starttime)) {
            return null;
        }
        Date date = null;
        try {
            date = format.parse(starttime);
            date.setHours(date.getHours() + hour);
            date.setMinutes(date.getMinutes() + minute);
            return format.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String addMinutes(String starttime, int minute) {
        if (StringUtils.isEmpty(starttime)) {
            return null;
        }
        Calendar today = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(y_m_d_hms);
        try {
            today.setTime(df.parse(starttime));
            today.add(Calendar.MINUTE, minute);
            return df.format(today.getTime());
        } catch (ParseException e) {
            return null;
        }
        // try {
        // date = format.parse(starttime);
        // date.setHours(date.getHours() + hour);
        // date.setMinutes(date.getMinutes() + minute);
        // return format.format(date);
        // } catch (ParseException e) {
        // return null;
        // }
    }

    /**
     * 将日期信息转换成今天、明天、后天、星期
     */
    // public static String getDateDetail(String serviceTime, String createTime) {
    // if (StringUtils.isEmpty(serviceTime) || StringUtils.isEmpty(createTime))
    // return "";
    // Calendar today = Calendar.getInstance();
    // Calendar target = Calendar.getInstance();
    //
    // DateFormat df = new SimpleDateFormat(y_m_d_hms);
    // try {
    // today.setTime(df.parse(serviceTime));
    // today.set(Calendar.HOUR_OF_DAY, 0);
    // today.set(Calendar.MINUTE, 0);
    // today.set(Calendar.SECOND, 0);
    // target.setTime(df.parse(createTime));
    // target.set(Calendar.HOUR_OF_DAY, 0);
    // target.set(Calendar.MINUTE, 0);
    // target.set(Calendar.SECOND, 0);
    // } catch (ParseException e) {
    // e.printStackTrace();
    // return null;
    // }
    // long intervalMilli = target.getTimeInMillis() - today.getTimeInMillis();
    // int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));
    // return showDateDetail(xcts, target);
    // }

}
