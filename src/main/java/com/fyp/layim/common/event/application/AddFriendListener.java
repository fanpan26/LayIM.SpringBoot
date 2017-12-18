package com.fyp.layim.common.event.application;

import com.fyp.layim.im.common.util.PushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

/**
 * 添加好友事件监听
 * 已经用 guava 的 EventBus 代替
 * */
public class AddFriendListener implements ApplicationListener<AddFriendEvent> {

    private static Logger logger = LoggerFactory.getLogger(AddFriendListener.class);
    @Override
    public void onApplicationEvent(AddFriendEvent addFriendEvent) {
        logger.info("收到好友申请事件");
        long applyId = addFriendEvent.getApplyId();
        PushUtil.pushAddFriendMessage(applyId);
    }
}
