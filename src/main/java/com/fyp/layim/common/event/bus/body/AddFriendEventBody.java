package com.fyp.layim.common.event.bus.body;

public class AddFriendEventBody {

    public AddFriendEventBody(long applyId) {
        this.applyId = applyId;
    }

    public long getApplyId() {
        return applyId;
    }
    private long applyId;

}
