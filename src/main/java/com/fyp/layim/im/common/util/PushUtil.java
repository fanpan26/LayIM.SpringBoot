package com.fyp.layim.im.common.util;

import com.fyp.layim.im.LayimWebsocketStarter;
import com.fyp.layim.im.packet.LayimToClientNoticeMsgBody;
import com.fyp.layim.im.packet.convert.BodyConvert;
import com.fyp.layim.service.ApplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsResponse;

import java.io.IOException;

/**
 * @author fyp
 * @crate 2017/12/6 22:24
 * @project SpringBootLayIM
 * 服务端主动推送
 */
public final class PushUtil {

    private static ApplyService applyService;

    private static ApplyService getApplyService(){
        if(applyService==null){
            applyService = (ApplyService)SpringUtil.getBean("applyService");
        }
        return applyService;
    }

    private static Logger logger = LoggerFactory.getLogger(PushUtil.class);

    private static LayimWebsocketStarter getStarter(){
        return (LayimWebsocketStarter)SpringUtil.getBean("layimWebsocketStarter");
    }

    private static ServerGroupContext getServerGroupContext(){
        return getStarter().getServerGroupContext();
    }
    /**
     * 向服务端主动推送消息
     * */
    private static void push(ChannelContext channelContext,Object msg) {
        try {
            WsResponse response = BodyConvert.getInstance().convertToTextResponse(msg);
            Aio.send(channelContext, response);
        }catch (IOException ex){

        }
    }

    /**
     * 获取channelContext
     * */
    private static ChannelContext getChannelContext(String toId) {
        ServerGroupContext context = getServerGroupContext();
        //找到用户
        ChannelContext channelContext = context.users.find(context, toId);
        return channelContext;
    }
    /**
     * 向服务端推送消息
     * */
    public static void pushApplyMessage(String toId) {
        logger.info("执行到了发送方法:pushApplyMessage");
        LayimToClientNoticeMsgBody body = new LayimToClientNoticeMsgBody();
        ChannelContext channelContext = getChannelContext(toId);
        //先判断是否在线，再去查询数据库，减少查询次数
        if (channelContext != null && !channelContext.isClosed()) {
            int count = getApplyService().getUnreadMsgCount(Long.parseLong(toId));
            body.setCount(count);

            push(channelContext, body);
        }
    }
}
