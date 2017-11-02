package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.domain.viewmodels.BigGroupViewModel;
import com.fyp.layim.domain.viewmodels.FriendGroupViewModel;
import com.fyp.layim.domain.viewmodels.LayimBaseViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;

import com.fyp.layim.repository.BigGroupRepository;
import com.fyp.layim.repository.FriendGroupRepository;
import com.fyp.layim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author fyp
 * @crate 2017/11/1 23:11
 * @project SpringBootLayIM
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendGroupRepository friendGroupRepository;

    @Autowired
    private BigGroupRepository bigGroupRepository;

    /**
     * 添加一个用户
     * */
    public JsonResult addUser(User user){
        User userRes = userRepository.save(user);
        if(userRes.getId()>0){
            return ResultUtil.success();
        }
        return ResultUtil.fail("添加失败");
    }

    /**
    * 方法说明
    *@param
    *@param
    *@return
    */
    public JsonResult getBaseList(Long userId){
        LayimBaseViewModel baseData = new LayimBaseViewModel();

        //自己的信息
        UserViewModel mine = new UserViewModel();
        mine.setUsername("小盘子");
        mine.setSign("SpringBoot学习中");
        mine.setAvatar("https://vignette.wikia.nocookie.net/dragonball/images/d/da/Kid-Goku-psd61058.png/revision/latest?cb=20120213205410");
        mine.setId(userId);

        baseData.setMine(mine);
        //好友列表信息
        ArrayList<FriendGroupViewModel> friends = new ArrayList<FriendGroupViewModel>();

        FriendGroupViewModel frined1 = new FriendGroupViewModel();
        frined1.setId(1L);
        frined1.setGroupname("我的好友");
        frined1.setOnline(10);

        ArrayList<UserViewModel> users1 = new ArrayList<UserViewModel>();

        UserViewModel user1 = new UserViewModel();
        user1.setId(100001L);
        user1.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjvcDBSg8-TYMzRSbw75MJAawM5dF9StHSisVhdhWmL6vK8K66UQ");
        user1.setSign("教练，我想打篮球");
        user1.setUsername("三井寿");

        users1.add(user1);

        frined1.setList(users1);
        friends.add(frined1);

        baseData.setFriend(friends);
        //分组信息
        ArrayList<BigGroupViewModel> groups  = new ArrayList<BigGroupViewModel>();

        BigGroupViewModel bigGroup1 = new BigGroupViewModel();
        bigGroup1.setId(1000001L);
        bigGroup1.setGroupname("SpringBoot爱好者群");
        bigGroup1.setAvatar("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTp9Q8BuXHj30KbOHPY7qlnR10oI4cCpplRcBFThQFzZ4bx3mBz");
        groups.add(bigGroup1);

        baseData.setGroup(groups);
        return ResultUtil.success(baseData);
    }
}
