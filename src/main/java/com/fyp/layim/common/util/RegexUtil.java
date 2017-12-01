package com.fyp.layim.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 验证某个值是否符合正则
     * */
    public static  boolean isValid(String input,String regex){
        Pattern accountPattern = Pattern.compile(regex);
        Matcher matcher = accountPattern.matcher(input);
        return matcher.find();
    }
}
