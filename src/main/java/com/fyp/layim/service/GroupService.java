package com.fyp.layim.service;

import com.fyp.layim.domain.BigGroup;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.mapper.LayimMapper;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.domain.viewmodels.FriendGroupViewModel;
import com.fyp.layim.domain.viewmodels.LayimGroupMembersViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;
import com.fyp.layim.repository.BigGroupRepository;
import com.fyp.layim.repository.FriendGroupRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/2 20:30
 * @project SpringBootLayIM
 */
@Service
public class GroupService {
    @Autowired
    private BigGroupRepository bigGroupRepository;

    @Autowired
    private FriendGroupRepository friendGroupRepository;

    /**
    * 根据群组ID获取群内所有用户列表
    *@param groupId
    *@return JsonResult
    */
    public JsonResult getGroupMembers(long groupId){
        LayimGroupMembersViewModel membersViewModel = new LayimGroupMembersViewModel();

        BigGroup group = bigGroupRepository.findOne(groupId);
        if(group == null){
            return JsonResult.fail("该群组不存在");
        }
        LayimMapper mapper = new LayimMapper();
        UserViewModel owner = mapper.mapUser(group.getOwner());
        membersViewModel.setOwner(owner);

        List<UserViewModel> memberList = mapper.mapUser(group.getUsers());
        membersViewModel.setList(memberList);

        return JsonResult.success(membersViewModel);
    }

    /**
     * 添加一个好友分组
     * */
    public JsonResult addFriendGroup(String name,Long userId) {

        if(StringUtils.isBlank(name) || name.length() > 10){
             return JsonResult.fail("参数group长度限制为1-20");
        }

        boolean exist = friendGroupRepository.countByName(name) > 0;
        if(exist){
            return JsonResult.fail("已经存在该分组");
        }

        FriendGroup friendGroup = new FriendGroup();
        friendGroup.setName(name);

        User user = new User();
        user.setId(userId);
        friendGroup.setOwner(user);

        friendGroupRepository.save(friendGroup);
        return JsonResult.success();
    }

    /**
     * 删除一个好友分组
     * */
    public JsonResult deleteFriendGroup(Long id,Long uid){
        friendGroupRepository.deleteByIdAndUserId(id,uid);
        return JsonResult.success();
    }

    /**
     * 创建一个群组
     * 中间遇到中间关系表插入数据插不进去的问题，后来发现需要两个实体映射都要写  @JoinTable 注解，否则有一个不写的话，是插入不了数据的
     * */
    public JsonResult addBigGroup(Long userId,String name,String avatar,String description){

        if(StringUtils.isBlank(name) || name.length() > 10) {
            return JsonResult.fail("参数group长度限制为1-20");
        }

        BigGroup bigGroup = new BigGroup();
        bigGroup.setGroupName(name);
        bigGroup.setAvatar(avatar);
        bigGroup.setDescription(description);

        User user = new User();
        user.setId(userId);
        bigGroup.setOwner(user);
        //群员
        ArrayList<User> users= new ArrayList<>(1);
        users.add(user);
        bigGroup.setUsers(users);

        bigGroupRepository.save(bigGroup);
        return JsonResult.success();
    }

    /**
     * 判断一个人是否在另个人的好友列表中
     * */
    public boolean isFriend(long userId,long friendId) {
        return friendGroupRepository.countByUidInGroup(userId,friendId) > 0;
    }

    /**
     * 获取一个用户的好友分组
     * */
    public JsonResult getUserFriendGroups(long userId){
        List<FriendGroup> friendGroups = friendGroupRepository.getAllByUserId(userId);
        List<FriendGroupViewModel> friendGroupViewModels = new LayimMapper().mapFriendGroup(friendGroups);
        return JsonResult.success(friendGroupViewModels);
    }
}
