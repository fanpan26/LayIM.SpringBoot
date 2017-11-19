package com.fyp.layim.im;

import com.fyp.layim.im.server.LayimMsgHandler;
import com.fyp.layim.im.server.LayimServerStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public LayimWebsocketStarter(int port, LayimMsgHandler wsMsgHandler) throws IOException {
        layimServerStarter = new LayimServerStarter(port, wsMsgHandler);
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
