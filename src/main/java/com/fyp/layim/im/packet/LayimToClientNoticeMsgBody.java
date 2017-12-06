package com.fyp.layim.im.packet;

import com.fyp.layim.im.common.intf.LayimMsgType;

/**
 * @author fyp
 * @crate 2017/12/6 22:33
 * @project SpringBootLayIM
 */
public class LayimToClientNoticeMsgBody extends LayimBaseBody{
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    @Override
    public byte getMtype() {
        return LayimMsgType.SERVER_TO_CLIENT_NOTICE;
    }

    @Override
    public void setMtype(byte mtype) {

    }
}
