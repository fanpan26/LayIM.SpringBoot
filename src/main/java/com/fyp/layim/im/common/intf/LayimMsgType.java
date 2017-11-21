package com.fyp.layim.im.common.intf;

/**
 * @author fyp
 * @crate 2017/11/19 21:07
 * @project SpringBootLayIM
 */
public interface LayimMsgType {
    /**
     * 单聊
     * */
    byte CLIENT_TO_CLIENT = 1;
    /**
     * 群聊
     * */
    byte CLIENT_TO_GROUP = 2;
}
