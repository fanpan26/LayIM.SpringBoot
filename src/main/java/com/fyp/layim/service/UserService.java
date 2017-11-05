package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.*;
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

import javax.management.relation.Relation;
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
    * 笨方法
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
        List<FriendGroup> friendGroups = user.getFriendGroups();
        //先转换好友分组
        List<FriendGroupViewModel> friends = LayimMapper.INSTANCE.mapFriendGroup(friendGroups);
        //在转换每个分组中的用户
       for(FriendGroup friendGroup : friendGroups) {
           List<RelationShip> relationShips = friendGroup.getRelationShips();
           List<UserViewModel> userViewModels = new ArrayList<UserViewModel>();
           //遍历 relationShips 获取userViewModels的集合
         for(RelationShip relationShip : relationShips){
               UserViewModel userViewModel = LayimMapper.INSTANCE.mapUser(relationShip.getFriend());
               userViewModels.add(userViewModel);
           }
           //获取主键
           Long friendGroupId = friendGroup.getId();
           for (FriendGroupViewModel viewModel : friends) {
               if (viewModel.getId().equals(friendGroupId)) {
                   viewModel.setList(userViewModels);
               }
           }
       }
        baseData.setFriend(friends);
        //分组信息
        List<BigGroup> bigGroups  = new ArrayList<BigGroup>();
        //构造详情分组信息
        List<GroupMember> groupMembers = user.getBigGroups();
        for (GroupMember member : groupMembers){
            bigGroups.add(member.getGroup());
        }

        List<BigGroupViewModel> bigGroupViewModels = LayimMapper.INSTANCE.mapBigGroup(bigGroups);
        baseData.setGroup(bigGroupViewModels);

        return ResultUtil.success(baseData);
    }

    public User getUser(Long userId){
        return userRepository.findOne(userId);
    }

}
