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
    /**
     * 客户端检查是否在线
     * */
    byte CLIENT_CHECK_ONLINE = 3;
    /**
     * 客户端检查在线人数
     * */
    byte CLIENT_CHECK_ONLINE_COUNT = 4;
    /**
     * 服务端向客户端推送消息
     * */
    byte SERVER_TO_CLIENT_NOTICE = 5;
}
