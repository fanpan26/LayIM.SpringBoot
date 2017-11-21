package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/19 23:23
 * @project SpringBootLayIM
 */
public class LayimMsgProperty {
    public byte getMtype() {
        return mtype;
    }

    public void setMtype(byte mtype) {
        this.mtype = mtype;
    }

    private byte mtype;
}
