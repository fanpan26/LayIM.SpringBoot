package com.fyp.layim.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author fyp
 * @crate 2017/11/2 21:27
 * @project SpringBootLayIM
 */
public class IdUtilTest {

    @Test
    public void testNextUserId(){
        for (Integer i=0;i<10000;i++) {
            Long id = IdUtil.nextUserId();
            System.out.println(id);
        }
    }
}