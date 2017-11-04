package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.mapper.LayimMapper;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.domain.result.LAYIM_ENUM;
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
    *@param userId 当前用户ID
    *@return 返回layim基础数据
    */
    public JsonResult getBaseList(Long userId) {
        LayimBaseViewModel baseData = new LayimBaseViewModel();

        User user = userRepository.findOne(userId);
        if (user == null) {
            return ResultUtil.fail(LAYIM_ENUM.NO_USER);
        }
        //自己的信息
        UserViewModel mine = LayimMapper.INSTANCE.mapUser(user);
        baseData.setMine(mine);
        //好友列表信息
        List<FriendGroupViewModel> friendGroups = LayimMapper.INSTANCE.mapFriendGroup(user.getFriendGroups());
        baseData.setFriend(friendGroups);
        //分组信息
        List<BigGroupViewModel> groups = LayimMapper.INSTANCE.mapBigGroup(user.getBigGroups());
        baseData.setGroup(groups);

        return ResultUtil.success(baseData);
    }

}
