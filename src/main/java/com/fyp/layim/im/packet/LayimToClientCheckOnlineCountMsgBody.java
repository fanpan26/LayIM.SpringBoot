package com.fyp.layim.im.packet;

import com.fyp.layim.im.common.intf.LayimMsgType;

public class LayimToClientCheckOnlineCountMsgBody extends LayimBaseBody {

    public LayimToClientCheckOnlineCountMsgBody(int count){
        this.count = count;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }
    private int count;

    @Override
    public byte getMtype() {
        return LayimMsgType.CLIENT_CHECK_ONLINE_COUNT;
    }
}
