package com.fyp.im.listener;

import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

public class MyMsgListener implements ServerAioListener {
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) throws Exception {

    }

    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception {

    }

    public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception {

    }

    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {

    }

    public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception {

    }

    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) throws Exception {

    }
}
