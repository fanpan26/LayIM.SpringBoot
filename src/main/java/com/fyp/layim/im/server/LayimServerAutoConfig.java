package com.fyp.layim.im.server;

import com.fyp.layim.im.server.config.LayimServerConfig;
import com.fyp.layim.im.server.config.LayimServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fyp
 * @crate 2017/11/20 19:16
 * @project SpringBootLayIM
 */
@Configuration
@EnableConfigurationProperties(LayimServerProperties.class)
public class LayimServerAutoConfig {

    @Autowired
    LayimServerProperties properties;

    @Bean
    LayimWebsocketStarter layimWebsocketStarter() throws Exception{
        LayimServerConfig config = new LayimServerConfig(properties.getPort());
        config.setBindIp(properties.getIp());
        config.setHeartBeatTimeout(properties.getHeartBeatTimeout());

        LayimWebsocketStarter layimWebsocketStarter = new LayimWebsocketStarter(config);
        //启动程序
        layimWebsocketStarter.start();
        //返回
        return layimWebsocketStarter;
    }
}
