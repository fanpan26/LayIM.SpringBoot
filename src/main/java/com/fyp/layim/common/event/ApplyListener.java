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
 * 问题：Async 的作用？
 * 目前先用 new Thread 实现（有待研究）
 * */
public class ApplyListener implements ApplicationListener<ApplyEvent> {

    private Logger logger = LoggerFactory.getLogger(ApplyListener.class);

    @Override
    public void onApplicationEvent(ApplyEvent applyEvent) {
        new Thread(){
            public void run(){
                Long toId = applyEvent.getToId();
//                try {
//                    Thread.sleep(5000);
//                }catch (InterruptedException e){
//
//                }
                PushUtil.pushApplyMessage(toId.toString());
            }
        }.start();

    }
}
