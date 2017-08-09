package com.aissure.packet.packet.utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/8/8.
 */

public class TimeUtil {
    public static boolean isMuteTime(Config config) {
        String start = config.getMuteStart();
        String end = config.getMuteEnd();
        return isMuteTime(start, end);

    }

    public static boolean isMuteTime(String start, String end) {
        int startHour, startMinute, endHour, endMinute;
        if (start.contains(":") && end.contains(":")) {
            startHour = Integer.parseInt(start.substring(0, start.indexOf(":")));
            startMinute = Integer.parseInt(start.substring(start.indexOf(":") + 1, start.length()));
            endHour = Integer.parseInt(end.substring(0, end.indexOf(":")));
            endMinute = Integer.parseInt(start.substring(end.indexOf(":") + 1, end.length()));

        } else {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        if (startHour <= endHour) {
            if ((hour >= startHour && minute >= startMinute) && (hour <= endHour && minute <= endMinute)) {
                return true;
            }
        } else {
            if ((hour >= startHour && minute >= startMinute) || (hour <= endHour && minute <= endMinute)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为夜间
     */
    public static boolean isNightTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 23 || hour < 7) {
            return true;
        }
        return false;
    }
}
