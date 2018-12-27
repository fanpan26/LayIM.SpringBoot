package com.fyp.im.utils;

import com.fyp.im.ServerStarter;
import com.fyp.im.processor.ClientOfflineHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.server.ServerGroupContext;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsResponse;

import java.util.Optional;

public class IMUtil {

    private static final Logger logger = LoggerFactory.getLogger(IMUtil.class);
    /**
     * 获取对方的ID
     * */
    private static ChannelContext getChannelContextByTargetId(ChannelContext channelContext,long targetId) {
        SetWithLock<ChannelContext> setWithLock = Tio.getChannelContextsByUserid(channelContext.getGroupContext(), targetId + "");
        if(setWithLock == null){
            return null;
        }
        if (setWithLock.getObj().size() > 0) {
            Optional<ChannelContext> targetChannelContext = setWithLock.getObj().stream().findFirst();
            if(targetChannelContext.isPresent()){
                return targetChannelContext.get();
            }
            return null;
        }
        return null;
    }

    /**
     * 是否在线
     * */
    private static boolean isOnline(ChannelContext channelContext){
        return channelContext!=null && channelContext.isClosed==false && channelContext.isRemoved==false;
    }

    private ServerGroupContext getServerGroupContext() {
        return ServerStarter.Instance.getServerGroupContext();
    }

    private static WsResponse buildReponse(byte[] body){
        return WsResponse.fromBytes(body);
    }

    public static void send(ChannelContext channelContext, long targetId, byte[] body){
        send(channelContext,targetId,body,null);
    }

    public static void send(ChannelContext channelContext, long targetId, byte[] body, ClientOfflineHandler offlineHandler) {
        ChannelContext targetChannelContext = getChannelContextByTargetId(channelContext, targetId);
        if (isOnline(targetChannelContext)) {
            logger.info("发送消息给{},消息长度{}", targetId, body.length);
            Tio.send(targetChannelContext, buildReponse(body));
        } else {
            logger.info("【{}】当前不在线",targetId);
            if(offlineHandler!=null){
                offlineHandler.handle(targetId,body);
            }
        }
    }
}
