package com.fyp.layim.im.packet;

import com.fyp.layim.im.common.LayimMsgType;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsPacket;
import org.tio.websocket.common.WsRequest;

/**
 * @author fyp
 * @crate 2017/11/19 21:06
 * @project SpringBootLayIM
 * 消息体继承自WsPacket
 * 区分消息类型
 */
public class LayimPacket extends WsRequest {
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

}
