package com.fyp.layim.domain.mapper;

import com.fyp.layim.domain.User;
import com.fyp.layim.domain.viewmodels.UserViewModel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author fyp
 * @crate 2017/11/4 11:53
 * @project SpringBootLayIM
 */
public class UserMapperTest {

    @Test
    public void testUserMapper(){
        User user = new User();
        user.setId(1L);
        user.setUserName("小盘子");
        user.setSign("这个是我的签名");
        user.setCreateAt(101L);

        UserViewModel viewModel = LayimMapper.INSTANCE.mapUser(user);

        Assert.assertEquals(viewModel.getAvatar(),user.getAvatar());
    }
}