package com.fyp.layim.im.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fyp
 * @crate 2017/11/20 19:30
 * @project SpringBootLayIM
 */
@ConfigurationProperties("layim.websocket")
public class LayimServerProperties {

    public LayimServerProperties(){
        port = 8081;
        heartBeatTimeout = 0;
        ip = null;
    }
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getHeartBeatTimeout() {
        return heartBeatTimeout;
    }

    public void setHeartBeatTimeout(int heartBeatTimeout) {
        this.heartBeatTimeout = heartBeatTimeout;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private int port;
    private int heartBeatTimeout;
    private String ip;
}
