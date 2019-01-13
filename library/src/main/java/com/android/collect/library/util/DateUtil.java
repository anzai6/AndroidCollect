package com.android.collect.library.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static int OneDayMills = 1000 * 60 * 60 * 24;
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    public static SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");

    public static String getCurrentRecordDate() {
        return format2.format(new Date());
    }


    /**
     * 当天日期
     *
     * @return
     */
    public static String getTodayDate() {
        return format.format(new Date());
    }

    /**
     * 7天前的日期
     *
     * @return
     */
    public static String getWeekBeforeDate() {
        return format.format(new Date(System.currentTimeMillis() - OneDayMills
                * 7));
    }

    /**
     * 获取前num天的那一天
     */
    public static String getLastNumDate(int num) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(System.currentTimeMillis());
        ca.add(Calendar.DATE, -num);
        return format.format(ca.getTime());
    }

    /**
     * 获取n个月前的那一天
     */
    public static String getMonthBeforeDate(int n) {
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(System.currentTimeMillis());
        ca.add(Calendar.MONTH, 0 - n);
        return format.format(ca.getTime());
    }

    /**
     * 获取本月的第一天
     */
    public static String getThisMonthFirst() {
        Calendar calendar = Calendar.getInstance();
        int week_num = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        if (week_num <= 24) {
            return format.format(new Date(System.currentTimeMillis() - week_num
                    * OneDayMills));
        } else {
            long day_24_millis = 24 * OneDayMills;
            Date date = new Date(System.currentTimeMillis() - day_24_millis);
            return format.format(new Date(date.getTime() - (week_num - 24)
                    * OneDayMills));
        }
    }

    /**
     * 格式化 20140101 为 2014-01-01
     *
     * @param date  要格式化的日期
     * @param fuhao 指定的符号
     */
    public static String formatDate(String date, String fuhao) {
        if (Util.isStringNull(date) || date.length() != 8) {
            return Util.toStr(date);
        }
        String p1 = date.substring(0, 4);
        String p2 = date.substring(4, 6);
        String p3 = date.substring(6, 8);
        return new StringBuffer().append(p1).append(fuhao).append(p2)
                .append(fuhao).append(p3).toString();
    }

    /**
     * 计算字符串日期和当前日期的间隔天数
     *
     * @param date 格式(2014-01-01)
     */
    public static int getJianGeDay(String date) {

        return getDays(getTodayDate(), date);
    }

    /**
     * 获得两个字符串日期格式之间的天数
     *
     * @param date1 格式：2014-11-11
     * @param date2
     * @return
     */
    public static int getDays(String date1, String date2) {
        return (int) (Math.abs(getDateMillis(date2) - getDateMillis(date1)) / OneDayMills);
    }

    /**
     * 得到字符串日期格式的毫秒值
     *
     * @param date yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static long getDateMillis(String date) {
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 格式化 20140101 为 2014年01月01日
     *
     * @param date 要格式化的日期
     * @param date 指定的符号
     */
    public static String formatChineseDate(String date) {
        if (Util.isStringNull(date) && date.length() != 8) {
            return Util.toStr(date);
        }
        String p1 = date.substring(0, 4);
        String p2 = date.substring(4, 6);
        String p3 = date.substring(6, 8);
        return new StringBuffer().append(p1).append("年").append(p2).append("月")
                .append(p3).append("日").toString();
    }

    /**
     * 格式化 1401 为 14年3月
     *
     * @param date 要格式化的日期
     * @param date 指定的符号
     */
    public static String formatDateYM(String date) {
        if (Util.isStringNull(date) && date.length() != 8) {
            return Util.toStr(date);
        }
        String p1 = date.substring(0, 2);
        String p2 = date.substring(2, 4);
        return new StringBuffer().append(p1).append("年").append(p2).append("月").toString();
    }

    /**
     * 格式化 20121212 为2012-12-12
     *
     * @param date 要格式化的日期
     * @param date 指定的符号
     */
    public static String formatDate(String date) {
        if (Util.isStringNull(date) || "0".equals(date)) {
            return "- -";
        }
        if (date.length() != 8) {
            return date;
        }
        String p1 = date.substring(0, 4);
        String p2 = date.substring(4, 6);
        String p3 = date.substring(6, 8);
        return new StringBuffer().append(p1).append("-").append(p2).append("-")
                .append(p3).toString();
    }

    /**
     * 将yyyyMMdd日期字符格式化为yyyy-MM-dd
     *
     * @param date 20100304
     * @return 2010-03-04
     */
    public static String formatDateString(String date) {
        if (Util.isStringNull(date)) {
            return "";
        }
        if (date.contains("-")) {
            date = date.replaceAll("-", "");
        }
        // 用于判断返回的日期字符串是否符合要求
        if (date.length() < 8) {
            return "--";
        }
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }


    /**
     * 格式化 1401 为 14年3月
     *
     * @param date 要格式化的日期
     * @param date 指定的符号
     */
    public static String formatDateMD(String date) {
        if (Util.isStringNull(date) && date.length() != 8) {
            return Util.toStr(date);
        }
        String p1 = date.substring(0, 2);
        String p2 = date.substring(2, 4);
        return new StringBuffer().append(p1).append(".").append(p2).append("").toString();
    }

    public static String formatTime(String time) {
        if (Util.isStringNull(time) || time.length() != 6) {
            return "";
        }
        String s1 = time.substring(0, 2);
        String s2 = time.substring(2, 4);
        String s3 = time.substring(4, 6);
        String ch = ":";
        return new StringBuffer().append(s1).append(ch).append(s2).append(ch).append(s3).toString();
    }

    /**
     * 获得两个字符串日期格式之间的天数
     *
     * @param date1 格式：2014-11-11
     * @param date2
     * @return
     */
    public static int compareDate(String date1, String date2) {

        return new Long(getDateMillis(date1)).compareTo(new Long(getDateMillis(date2)));
//		return (int) (Math.abs(getDateMillis(date2) - getDateMillis(date1)) / OneDayMills);
    }

    /**
     * @param millisecond 毫秒
     * @return 得到 持续时间      * 天 * 时 * 分 * 秒
     */
    public static String getDuringTime(long millisecond) {
        StringBuffer buffer = new StringBuffer();
        int day = (int) (millisecond / 1000 / 60 / 60 / 24);
        int hour = (int) (millisecond / 1000 / 60 / 60 % 24);
        int minute = (int) (millisecond / 1000 / 60 % 60);
        int second = (int) (millisecond / 1000 % 60);

        if (day > 0) {
            buffer.append(day + "天");
        }
        if (hour > 0) {
            buffer.append(hour + "小时");
        }
        if (minute > 0) {
            buffer.append(minute + "分钟");
        }
        if (second > 0) {
//			buffer.append(second + "秒");
        }
        return buffer.toString();
    }

    /**
     * 将yyyyMMdd日期字符格式化为yyyy年MM月dd日
     *
     * @param date 20100304
     * @return 2010-03-04
     */
    public static String formatDateString8(String date) {
        if (null == date) {
            return "";
        }
        date = date.trim();
        if ("".equals(date) || "null".equals(date)) {
            return "";
        }

        if (date.contains("-")) {
            date = date.replaceAll("-", "");
        }
        // 用于判断返回的日期字符串是否符合要求
        if (date.length() < 8) {
            return "";
        }
        return date.substring(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
    }

    public static String getCurrentFormatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        return time;
    }

    /**
     * 将yyyyMMddhhmmss日期字符格式化为yyyy-MM-dd hh:mm:ss
     *
     * @param date 20170526084113
     * @return 2017年05月26日 08点41分13秒
     */
    public static String formatDateTimeString(String date) {
        if (null == date) {
            return "";
        }
        date = date.trim();
        if ("".equals(date) || "null".equals(date)) {
            return "";
        }

        if (date.contains("-")) {
            date = date.replaceAll("-", "");
        }
        // 用于判断返回的日期字符串是否符合要求
        if (date.length() < 14) {
            return "";
        }
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " " + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
    }

    /**
     * 获得指定日期的前N天
     *
     * @param day(20121212)
     * @return
     * @throws Exception
     */
    public static String getDayBefore(String day, int n) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = format3.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int dayCount = c.get(Calendar.DATE);
        c.set(Calendar.DATE, dayCount - n);

        String dayBefore = format.format(c.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后N天
     *
     * @param day (20121212)
     * @param n
     * @return
     */
    public static String getDayAfter(String day, int n) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = format3.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int dayCount = c.get(Calendar.DATE);
        c.set(Calendar.DATE, dayCount + n);

        String dayAfter = format.format(c.getTime());
        return dayAfter;
    }

}
