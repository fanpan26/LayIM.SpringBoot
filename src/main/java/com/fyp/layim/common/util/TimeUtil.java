package com.fyp.layim.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换
 * */
public class TimeUtil {

    private static SimpleDateFormat HTTP_DATE_FORMAT;

    public static String formatDate(long millis) {
        Date date = new Date(millis);
        return HTTP_DATE_FORMAT.format(date);
    }
    static {
        HTTP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    }
}
