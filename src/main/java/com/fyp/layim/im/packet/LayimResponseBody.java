package com.fyp.layim.im.packet;

/**
 *
 * */
public class LayimResponseBody extends LayimMsgProperty {
    public LayimResponseBody(String msg){
        this.msg = msg;
    }
    /**
     * 统一客户端消息响应
     * */
    @Override
    public byte getMtype() {
        return -1;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

}
