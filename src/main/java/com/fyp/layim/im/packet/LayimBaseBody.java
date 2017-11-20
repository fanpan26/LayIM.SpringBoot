package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/19 20:56
 * @project SpringBootLayIM
 */
public class LayimBaseBody {
    public long getTimestamp() {
        if (timestamp == 0){
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private long timestamp;


    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    private byte msgType;

}
