package com.fyp.layim.common.event.bus.handler;

import com.fyp.layim.common.event.bus.body.AddFriendEventBody;
import com.fyp.layim.im.common.util.PushUtil;

public class AddFriendEventHandler extends AbsEventHandler<AddFriendEventBody>{
    @Override
    public void handle(AddFriendEventBody body) {
        long applyId = body.getApplyId();
        PushUtil.pushAddFriendMessage(applyId);
    }
}
