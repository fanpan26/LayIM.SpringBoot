package com.fyp.layim.im.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;
import org.tio.websocket.common.WsSessionContext;

/**
 * @author fyp
 * @crate 2017/11/19 18:33
 * @project SpringBootLayIM
 */
public class LayimServerAioListener implements ServerAioListener {
    private static Logger logger = LoggerFactory.getLogger(LayimServerAioListener.class);
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
            logger.info("ServerAioListener:onBeforeClose");
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        WsSessionContext wsSessionContext = new WsSessionContext();
        channelContext.setAttribute(wsSessionContext);
        logger.info("ServerAioListener:onAfterConnected");
    }

    @Override
    public void onAfterClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        logger.info("ServerAioListener:onAfterClose");
    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {

    }

    @Override
    public void onAfterReceived(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
    }
}
