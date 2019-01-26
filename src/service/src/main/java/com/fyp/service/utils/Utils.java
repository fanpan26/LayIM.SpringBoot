package com.fyp.service.utils;

public class Utils {
    /**
     * 生成房间ID
     */
    public static long generateRoomId(long from, long to, String msgType) {
        if (!"group".equals(msgType)) {
            if (from > to) {
                return Long.valueOf(from + "" + to);
            }
            return Long.valueOf(to + "" + from);
        }
        return to;
    }
}
