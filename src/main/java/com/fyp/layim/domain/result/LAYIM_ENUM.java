package com.fyp.layim.domain.result;

/**
 * @author fyp
 * @crate 2017/11/4 11:31
 * @project SpringBootLayIM
 */
public enum LAYIM_ENUM {
    /**
     * 用户不存在
    */
    NO_USER(1001,"该用户不存在"),
    GROUP_NOT_EXIST(1002,"群组不存在")
    ;

    private LAYIM_ENUM(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private Integer code;
    private String msg;

}
