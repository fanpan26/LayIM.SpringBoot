package com.fyp.im.utils;

import java.io.UnsupportedEncodingException;

public final class SafeEncoder {

    public static String encode(byte[] bytes) {
        try {
            return new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ;
            return null;
        }
    }

    public static byte[] encode(String value) {
        try {
            return value.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ;
            return null;
        }
    }
}
