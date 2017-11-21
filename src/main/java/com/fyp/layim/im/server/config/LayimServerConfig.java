package com.fyp.layim.im.server.config;

/**
 * @author fyp
 * @crate 2017/11/19 18:34
 * @project SpringBootLayIM
 */
public class LayimServerConfig {
    private String bindIp = null;


    private Integer bindPort = 9322;

    public void setBindPort(Integer bindPort) {
        this.bindPort = bindPort;
    }

    public Integer getHeartBeatTimeout() {
        return heartBeatTimeout;
    }

    public void setHeartBeatTimeout(Integer heartBeatTimeout) {
        this.heartBeatTimeout = heartBeatTimeout;
    }

    private Integer heartBeatTimeout = 0;

    private String charset = "utf-8";

    /**

     *

     * @author tanyaowu

     */
    public LayimServerConfig(Integer bindPort) {

        this.bindPort = bindPort;
    }

    /**

     * @return the bindIp

     */
    public String getBindIp() {
        return bindIp;
    }

    /**

     * @return the bindPort

     */
    public Integer getBindPort() {
        return bindPort;
    }

    /**

     * @return the charset

     */
    public String getCharset() {
        return charset;
    }

    /**

     * @param bindIp the bindIp to set

     */
    public void setBindIp(String bindIp) {
        this.bindIp = bindIp;
    }

    /**

     * @param charset the charset to set

     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
