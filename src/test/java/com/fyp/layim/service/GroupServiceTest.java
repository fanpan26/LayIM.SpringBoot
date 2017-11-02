package com.fyp.layim.service;

import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.result.JsonResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author fyp
 * @crate 2017/11/2 20:42
 * @project SpringBootLayIM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void testAddFriendGroup(){
        FriendGroup friendGroup = new FriendGroup();
        friendGroup.setUid(100000L);
        friendGroup.setName("我的好友");
        JsonResult result = groupService.addFriendGroup(friendGroup);
        Assert.assertEquals(Integer.valueOf(0),result.getCode());
    }
}