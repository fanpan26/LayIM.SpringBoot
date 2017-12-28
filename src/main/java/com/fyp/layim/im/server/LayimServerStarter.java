package com.fyp.layim.im.server;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import com.fyp.layim.im.common.processor.LayimMsgProcessorManager;
import com.fyp.layim.im.server.config.LayimServerConfig;
import com.fyp.layim.im.server.handler.LayimServerAioHandler;
import com.fyp.layim.im.server.listener.LayimServerAioListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.intf.TioUuid;
import org.tio.core.ssl.SslConfig;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;
import org.tio.websocket.common.WsTioUuid;
import org.tio.websocket.server.handler.IWsMsgHandler;
/**
 * @author fyp
 * @crate 2017/11/19 18:34
 * @project SpringBootLayIM
 */
public class LayimServerStarter {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(LayimServerStarter.class);

    private LayimServerConfig layimServerConfig = null;

    private IWsMsgHandler wsMsgHandler = null;

    private LayimServerAioHandler layimServerAioHandler = null;

    private LayimServerAioListener layimServerAioListener = null;

    private ServerGroupContext serverGroupContext = null;

    private AioServer aioServer = null;

    /**

     * @return the wsServerConfig

     */
    public LayimServerConfig getWsServerConfig() {
        return layimServerConfig;
    }

    /**

     * @return the wsMsgHandler

     */
    public IWsMsgHandler getWsMsgHandler() {
        return wsMsgHandler;
    }

    /**

     * @return the wsServerAioHandler

     */
    public LayimServerAioHandler getWsServerAioHandler() {
        return layimServerAioHandler;
    }

    /**

     * @return the wsServerAioListener

     */
    public LayimServerAioListener getWsServerAioListener() {
        return layimServerAioListener;
    }

    /**

     * @return the serverGroupContext

     */
    public ServerGroupContext getServerGroupContext() {
        return serverGroupContext;
    }

    public LayimServerStarter(int port, IWsMsgHandler wsMsgHandler) throws Exception {
        this(port, wsMsgHandler, null, null);
    }

    public LayimServerStarter(int port, IWsMsgHandler wsMsgHandler, SynThreadPoolExecutor tioExecutor, ThreadPoolExecutor groupExecutor) throws Exception {
        this(new LayimServerConfig(port), wsMsgHandler, tioExecutor, groupExecutor);
    }

    public LayimServerStarter(LayimServerConfig wsServerConfig, IWsMsgHandler wsMsgHandler) throws Exception {
        this(wsServerConfig, wsMsgHandler, null, null);
    }

    public LayimServerStarter(LayimServerConfig wsServerConfig, IWsMsgHandler wsMsgHandler, SynThreadPoolExecutor tioExecutor, ThreadPoolExecutor groupExecutor) throws Exception {
        this(wsServerConfig, wsMsgHandler, new WsTioUuid(), tioExecutor, groupExecutor);
    }

    private void initSsl(ServerGroupContext serverGroupContext) throws Exception {
        String keyStoreFile = "D:/tio.jks";//
        String trustStoreFile = "D:/tio.jks";
        String keyStorePwd = "tiotest";  //密码

        if (StringUtils.isNotBlank(keyStoreFile) && StringUtils.isNotBlank(trustStoreFile)) {
            SslConfig sslConfig = SslConfig.forServer(keyStoreFile, trustStoreFile, keyStorePwd);
            serverGroupContext.setSslConfig(sslConfig);
        }
    }

    public LayimServerStarter(LayimServerConfig wsServerConfig, IWsMsgHandler wsMsgHandler, TioUuid tioUuid, SynThreadPoolExecutor tioExecutor, ThreadPoolExecutor groupExecutor) throws Exception {
        this.layimServerConfig = wsServerConfig;
        this.wsMsgHandler = wsMsgHandler;

        layimServerAioHandler = new LayimServerAioHandler(wsServerConfig, wsMsgHandler);
        layimServerAioListener = new LayimServerAioListener();

        serverGroupContext = new ServerGroupContext(layimServerAioHandler, layimServerAioListener, tioExecutor, groupExecutor);
        //心跳时间，暂时设置为0
        serverGroupContext.setHeartbeatTimeout(wsServerConfig.getHeartBeatTimeout());
        serverGroupContext.setName("Tio Websocket Server for LayIM");

        aioServer = new AioServer(serverGroupContext);
        serverGroupContext.setTioUuid(tioUuid);
        //initSsl(serverGroupContext);
        //初始化消息处理器
        LayimMsgProcessorManager.init();
    }

    public void start() throws IOException {
        aioServer.start(layimServerConfig.getBindIp(), layimServerConfig.getBindPort());
    }
}
