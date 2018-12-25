package com.fyp.utils.jwt;

public class JwtVertifyResult {

    public static final JwtVertifyResult NotVertified = new JwtVertifyResult(0L);

    public  JwtVertifyResult(Long userId){
        this.userId = userId;
    }

    private Long userId;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private String errMsg;

    public Long getUserId(){
        return userId;
    }

    public boolean isVertified(){
        return userId > 0;
    }

    public static JwtVertifyResult result(String msg){
        JwtVertifyResult result = new JwtVertifyResult(0L);
        result.setErrMsg(msg);
        return result;
    }

}
