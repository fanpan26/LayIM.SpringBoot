package com.fyp.layim.common.event.bus;

import com.fyp.layim.common.event.bus.body.ApplyEventBody;
import com.fyp.layim.common.event.bus.body.EventBody;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventUtilTest {

    @Test
    public void publishTest(){
        EventBody<ApplyEventBody> eventBody = new EventBody<>();
        eventBody.setEventType(1);
        eventBody.setEventData(new ApplyEventBody(10000L));
        EventUtil.publish(eventBody);
    }
}