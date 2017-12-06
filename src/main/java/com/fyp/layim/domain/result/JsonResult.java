package com.fyp.layim.domain.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author fyp
 * @crate 2017/11/1 19:55
 * @project SpringBootLayIM
 */
public class JsonResult {

    private static final int SUCCESS_COCE = 0;
    private static final int FAIL_COCE = 1;

    public JsonResult(){

    }
    public JsonResult(String msg){
        this(FAIL_COCE,msg,null);
    }
    public JsonResult(Object data){
       this(SUCCESS_COCE,"",data);
    }
    public JsonResult(Integer code,String msg,Object data){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getData() {
        return data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return code == SUCCESS_COCE;
    }
    @JsonIgnore
    public boolean isFail(){
        return !isSuccess();
    }
    /**
     * 成功结果，有返回值
     * */
    public static JsonResult success(Object data){
        return new JsonResult(data);
    }
    /**
     * 成功结果无返回值
     * */
    public static JsonResult success(){
        return success(null);
    }
    /**
     * 失败结果
     * */
    public static JsonResult fail(String msg){
        return new JsonResult(msg);
    }
    /**
     * 失败结果
     * */
    public static JsonResult fail(String msg,Object data){
        return new JsonResult(FAIL_COCE,msg, data);
    }

}
