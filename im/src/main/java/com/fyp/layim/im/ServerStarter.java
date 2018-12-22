package com.fyp.layim.im;

import com.fyp.layim.im.handler.MsgHandler;
import com.fyp.layim.im.listener.MyMsgListener;
import com.fyp.layim.im.listener.MyGroupListener;
import com.fyp.layim.im.listener.MyIpStatListener;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

public class ServerStarter {
    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    public ServerStarter(int port) throws Exception {
        initServerStarter(port);
        initGroupContext();
    }

    private void initServerStarter(int port) throws Exception{
        wsServerStarter = new WsServerStarter(port, new MsgHandler());
    }

    private void initGroupContext(){
        serverGroupContext = wsServerStarter.getServerGroupContext();

        serverGroupContext.setName("Tio for LayIM");
        serverGroupContext.setIpStatListener(new MyIpStatListener());
        serverGroupContext.setGroupListener(new MyGroupListener());
        serverGroupContext.setServerAioListener(new MyMsgListener());
        serverGroupContext.setHeartbeatTimeout(0);
    }

    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    public WsServerStarter getWsServerStarter() {
        return wsServerStarter;
    }

    public static void start() throws Exception{
        new ServerStarter(8888).wsServerStarter.start();
    }

    public static void main(String[] args) throws Exception{
        start();
    }
}
