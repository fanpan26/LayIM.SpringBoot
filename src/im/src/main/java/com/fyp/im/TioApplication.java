package com.fyp.im;

import com.fyp.im.handler.MyMsgHandler;
import com.fyp.im.listener.MyMsgListener;
import com.fyp.im.listener.MyGroupListener;
import com.fyp.im.listener.MyIpStatListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

@Component
public class TioApplication {

    private static final Logger logger = LoggerFactory.getLogger(TioApplication.class);

    private WsServerStarter wsServerStarter;
    private ServerGroupContext serverGroupContext;

    public TioApplication() throws Exception {
        initServerStarter("127.0.0.1",8889);
        initGroupContext();
    }

    private void initServerStarter(String ip,int port) throws Exception{
        WsServerConfig config = new WsServerConfig(port);
        config.setBindIp(ip);
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


    public void run() throws Exception {
       wsServerStarter.start();
    }
}
