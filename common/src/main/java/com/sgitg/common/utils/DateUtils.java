package com.sgitg.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/27/027 14:09
 */
public class DateUtils {
    private static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * 格式化日期
     *
     * @param date date
     * @return 年-月-日
     */
    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date date
     * @return 年-月-日 时:分:秒
     */
    public static String formatDateTime(Date date) {
        return formatDateTime.format(date);
    }


    /**
     * 将时间戳解析成日期
     *
     * @param timeInMillis timeInMillis
     * @return 年-月-日 时:分:秒
     */
    public static String parseDateTime(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDateTime(date);
    }


    /**
     * 将年-月-日 时:分:秒 解析为Date
     *
     * @param datetime datetime
     * @return Date
     */
    private static Date parseDateTime(String datetime) {
        Date mDate = null;
        try {
            mDate = formatDateTime.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }


    /**
     * 以友好的方式显示时间
     *
     * @param sdate sdate
     * @return String
     */
    public static String friendlyTime(String sdate) {
        Date time = parseDateTime(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = formatDate.format(cal.getTime());
        String paramDate = formatDate.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = formatDate.format(time);
        }
        return ftime;
    }

    /**
     * @param millis 要转化的毫秒数。
     * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分钟）。
     */
    public static String millisToStringShort(long millis) {
        String h;
        String m;
        h = "00小时";
        m = "00分钟";
        long temp = millis;
        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        if (temp / hper > 0) {
            h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            h += "小时";
        }
        temp = temp % hper;
        if (temp / mper > 0) {
            m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            m += "分钟";
        }
        return h + m;
    }
}
