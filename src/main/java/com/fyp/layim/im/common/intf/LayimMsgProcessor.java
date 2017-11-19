package com.fyp.layim.im.common.intf;

import com.fyp.layim.im.packet.LayimPacket;
import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsResponse;

import java.io.IOException;

/**
 * @author fyp
 * @crate 2017/11/19 23:34
 * @project SpringBootLayIM
 */
public interface LayimMsgProcessor {
    WsResponse process(LayimPacket layimPacket, ChannelContext channelContext) throws Exception;
}
