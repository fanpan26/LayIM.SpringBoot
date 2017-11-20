package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/20 7:40
 * @project SpringBootLayIM
 */
public class UnknownReponseBody extends LayimBaseBody {
    public UnknownReponseBody(byte type){
        msg = "未知的消息命令："+ type;
    }
    private String msg;

    public String getMsg(){
        return msg;
    }
}
