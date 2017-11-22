package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.BigGroup;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.UserAccount;
import com.fyp.layim.domain.mapper.LayimMapper;
import com.fyp.layim.domain.result.JsonResult;

import com.fyp.layim.domain.result.LAYIM_ENUM;
import com.fyp.layim.domain.viewmodels.FriendGroupViewModel;
import com.fyp.layim.domain.viewmodels.LayimInitDataViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;
import com.fyp.layim.im.packet.ContextUser;
import com.fyp.layim.im.packet.LayimContextUserInfo;
import com.fyp.layim.repository.UserAccountRepository;
import com.fyp.layim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private UserAccountRepository userAccountRepository;
    /**
    * 获取init接口所需要的数据结果
    *@param userId 用户ID
    *@return 返回 JsonResult(LayimInitDataViewModel)
    */
    public JsonResult getBaseList(long userId){
        LayimInitDataViewModel resultViewModel = new LayimInitDataViewModel();

        //开始构造
        //获取用户基本信息
        User user = userRepository.findOne(userId);
        if(user == null){
            return ResultUtil.fail(LAYIM_ENUM.NO_USER);
        }
        //映射用户信息
        UserViewModel mine = LayimMapper.INSTANCE.mapUser(user);
        resultViewModel.setMine(mine);
        //获取好友分组信息
        List<FriendGroup> friendGroups = user.getFriendGroups();
        List<FriendGroupViewModel> friendGroupViewModels = new ArrayList<FriendGroupViewModel>(friendGroups.size());
        //遍历好友分组
        for (FriendGroup friendGroup : friendGroups){
            List<User> usersInGroup = friendGroup.getUsers();
            //先映射群组信息
            FriendGroupViewModel friendGroupViewModel = LayimMapper.INSTANCE.mapFriendGroup(friendGroup);
            //将每个组的人放到好友分组里面
            friendGroupViewModel.setList(LayimMapper.INSTANCE.mapUser(usersInGroup));
            friendGroupViewModels.add(friendGroupViewModel);
        }
        resultViewModel.setFriend(friendGroupViewModels);
        //获取群组信息
        List<BigGroup> bigGroups = user.getBigGroups();
        resultViewModel.setGroup(LayimMapper.INSTANCE.mapBigGroup(bigGroups));

        return ResultUtil.success(resultViewModel);
    }

    /**
     * 用户登录
     * */
    public UserAccount getUserAccount(String username){
        return userAccountRepository.findByUsername(username);
    }

    /**
     * 不加 @Transactional 注解，会导致 no session的问题（LayimMsgHandler调用的时候）
     * */
    @Transactional
    public LayimContextUserInfo getContextUserInfo(Long userId){
        LayimContextUserInfo contextUserInfo = new LayimContextUserInfo();
        ContextUser contextUser = new ContextUser();
        User user = userRepository.findOne(userId);

        if(user != null) {
            contextUser.setUserid(user.getId().toString());
            contextUser.setUsername(user.getUserName());
            contextUser.setAvatar(user.getAvatar());
            contextUserInfo.setContextUser(contextUser);
            List<BigGroup> bigGroups = user.getBigGroups();
            if(bigGroups!=null&&bigGroups.size()>0) {
                //jdk 1.8 lambda 表达式
                List<String> groupIds = bigGroups.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
                contextUserInfo.setGroupIds(groupIds);
            }
            return contextUserInfo;
        }
        return null;
    }

}
