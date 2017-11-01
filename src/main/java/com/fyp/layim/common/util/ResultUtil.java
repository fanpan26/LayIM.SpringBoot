package com.fyp.layim.common.util;

import com.fyp.layim.domain.result.JsonResult;

/**
 * @author fyp
 * @crate 2017/11/1 20:06
 * @project SpringBootLayIM
 */
public class ResultUtil {
    public static JsonResult success(Object data){
        return new JsonResult(data);
    }
    public static JsonResult success(){
        return success(null);
    }
    public static JsonResult fail(String msg){
        return new JsonResult(msg);
    }
}
