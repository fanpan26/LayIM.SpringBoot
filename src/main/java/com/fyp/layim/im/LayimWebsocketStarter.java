package com.fyp.layim.im;

import com.fyp.layim.im.server.handler.LayimMsgHandler;
import com.fyp.layim.im.server.config.LayimServerConfig;
import com.fyp.layim.im.server.LayimServerStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.server.ServerGroupContext;

import java.io.IOException;

/**
 * @author fyp
 * @crate 2017/11/19 18:32
 * @project SpringBootLayIM
 */
public class LayimWebsocketStarter {
    private static Logger logger = LoggerFactory.getLogger(LayimWebsocketStarter.class);

    private LayimServerStarter layimServerStarter;
    private ServerGroupContext serverGroupContext;

    public LayimWebsocketStarter(LayimServerConfig layimServerConfig) throws IOException {
        layimServerStarter = new LayimServerStarter(layimServerConfig, new LayimMsgHandler());
        serverGroupContext = layimServerStarter.getServerGroupContext();
    }

    public LayimServerStarter getWsServerStarter() {
        return layimServerStarter;
    }

    public void start() throws IOException {
        layimServerStarter.start();
    }

    /**

     * @return the serverGroupContext

     */
    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

}
