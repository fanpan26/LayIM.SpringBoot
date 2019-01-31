package com.fyp.im.utils;

import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;


public class IMUtil {

    private static WsResponse buildReponse(byte[] body) {
        return WsResponse.fromBytes(body);
    }

    public static void sendToGroup(ChannelContext channelContext, long targetId, byte[] body) {
        String currentUserId = channelContext.userid;
        Tio.sendToGroup(channelContext.getGroupContext(), String.valueOf(targetId), buildReponse(body), ctx -> !ctx.userid.equals(String.valueOf(currentUserId)));
    }

    public static void send(ChannelContext channelContext, long targetId, byte[] body) {
        Tio.sendToUser(channelContext.getGroupContext(), String.valueOf(targetId), buildReponse(body));
    }
}
