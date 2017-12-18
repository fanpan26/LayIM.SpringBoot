package com.fyp.layim.common.event.bus.handler;

import com.fyp.layim.common.event.bus.body.ApplyEventBody;
import com.fyp.layim.im.common.util.PushUtil;

public class ApplyEventHandler extends AbsEventHandler<ApplyEventBody> {

    @Override
    public void handle(ApplyEventBody body) {
        long toId = body.getTargetId();
        PushUtil.pushApplyMessage(toId);
    }
}
