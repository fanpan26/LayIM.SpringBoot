package com.fyp.layim.common.event;

import com.fyp.layim.im.common.util.PushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * @author fyp
 * @crate 2017/12/6 22:02
 * @project SpringBootLayIM
 */
/**
 * 不加@async 就是同步消息处理
 * */
public class ApplyListener implements ApplicationListener<ApplyEvent> {

    private Logger logger = LoggerFactory.getLogger(ApplyListener.class);

    /**
     * 在程序启动处添加@EnableAsync才会起作用
     * */
    @Async
    @Override
    public void onApplicationEvent(ApplyEvent applyEvent){
        Long toId = applyEvent.getToId();
        PushUtil.pushApplyMessage(toId.toString());
    }


}
