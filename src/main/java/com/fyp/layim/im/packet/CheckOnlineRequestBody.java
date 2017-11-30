package com.fyp.layim.im.packet;

public class CheckOnlineRequestBody extends LayimBaseBody {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;
}
