package com.sgitg.common.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

/**
 * StringUtils
 *
 * @author 周麟
 * @created 2018/1/4 11:23
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            //去掉多余的0
            s = s.replaceAll("0+?$", "");
            //如最后一位是.则去掉
            s = s.replaceAll("[.]$", "");
        }
        return s;
    }

    public static SpannableString deal(String title) {
        String keyStart = "<em class='highlight'>";
        String keyEnd = "</em>";
        if (!title.contains(keyStart) && !title.contains(keyEnd)) {
            return new SpannableString(title);
        }
        int start = title.indexOf(keyStart);
        int end = title.indexOf(keyEnd) - keyStart.length();
        title = title.replace(keyStart, "");
        title = title.replace(keyEnd, "");
        SpannableString sb = new SpannableString(title);
        sb.setSpan(new ForegroundColorSpan(Color.parseColor("#F44336")), start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        return sb;
    }
}
