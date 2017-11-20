package com.fyp.layim.im.common.intf;

import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;

/**
 * @author fyp
 * @crate 2017/11/19 23:34
 * @project SpringBootLayIM
 */
public interface LayimMsgProcessor {
    WsResponse process(WsRequest layimPacket, ChannelContext channelContext) throws Exception;
}
