package com.fyp.layim.domain.result;

/**
 * @author fyp
 * @crate 2017/11/1 19:55
 * @project SpringBootLayIM
 */
public class JsonResult {

    public JsonResult(){

    }
    public JsonResult(String msg){
        this(1,msg,null);
    }
    public JsonResult(Object data){
       this(0,"success",data);
    }
    public JsonResult(Integer code,String msg,Object data){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private String msg;
    private Object data;

    public  boolean isSuccess(){
        return this.code == 0;
    }
}
