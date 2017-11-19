package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/19 23:23
 * @project SpringBootLayIM
 */
public class LayimMsgProperty {
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    private byte type;
}
