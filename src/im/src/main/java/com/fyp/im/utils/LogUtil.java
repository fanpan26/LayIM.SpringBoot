package com.fyp.im.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void debug(String message, Object... args) {
        logger.debug(message, args);
    }
}
