package com.fyp.im;

import com.fyp.im.handler.MyMsgHandler;
import com.fyp.im.listener.MyMsgListener;
import com.fyp.im.listener.MyGroupListener;
import com.fyp.im.listener.MyIpStatListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;

public class ServerStarter {

    private static final Logger logger = LoggerFactory.getLogger(ServerStarter.class);

    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    public ServerStarter(int port) throws Exception {
        initServerStarter(port);
        initGroupContext();
    }

    private void initServerStarter(int port) throws Exception{
        WsServerConfig config = new WsServerConfig(8888);
        config.setBindIp("127.0.0.1");
        wsServerStarter = new WsServerStarter(config, new MyMsgHandler());
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
        logger.debug("WebSocket Server 启动啦");
    }
}
