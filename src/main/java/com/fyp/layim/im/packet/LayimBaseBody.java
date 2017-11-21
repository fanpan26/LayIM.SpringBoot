package com.fyp.layim.im.packet;

import java.io.Serializable;

/**
 * @author fyp
 * @crate 2017/11/19 20:56
 * @project SpringBootLayIM
 */
public class LayimBaseBody extends LayimMsgProperty implements Serializable{
    private static final Long serialVersionUID = 1L;
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
}
