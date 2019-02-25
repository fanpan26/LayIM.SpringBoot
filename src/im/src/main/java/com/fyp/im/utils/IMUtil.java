package com.fyp.im.utils;

import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;


public class IMUtil {

    private static WsResponse buildResponse(byte[] body) {
        return WsResponse.fromBytes(body);
    }

    public static void sendToGroup(ChannelContext channelContext, long targetId, byte[] body) {
        String currentUserId = channelContext.userid;
        Tio.sendToGroup(channelContext.getGroupContext(), String.valueOf(targetId), buildResponse(body), ctx -> !ctx.userid.equals(currentUserId));
    }

    public static void send(ChannelContext channelContext, long targetId, byte[] body) {
        Tio.sendToUser(channelContext.getGroupContext(), String.valueOf(targetId), buildResponse(body));
    }
}
