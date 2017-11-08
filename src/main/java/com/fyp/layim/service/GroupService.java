package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.BigGroup;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.mapper.LayimMapper;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.domain.result.LAYIM_ENUM;
import com.fyp.layim.domain.viewmodels.LayimGroupMembersViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;
import com.fyp.layim.repository.BigGroupRepository;
import com.fyp.layim.repository.FriendGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
    * 根据群组ID获取群内所有用户列表
    *@param groupId
    *@return JsonResult
    */
    public JsonResult getGroupMembers(long groupId){
        LayimGroupMembersViewModel membersViewModel = new LayimGroupMembersViewModel();

        BigGroup group = bigGroupRepository.findOne(groupId);
        if(group == null){
            return ResultUtil.fail(LAYIM_ENUM.GROUP_NOT_EXIST);
        }
        UserViewModel owner = LayimMapper.INSTANCE.mapUser(group.getOwner());
        membersViewModel.setOwner(owner);

        List<UserViewModel> memberList =LayimMapper.INSTANCE.mapUser(group.getUsers());
        membersViewModel.setList(memberList);

        return ResultUtil.success(membersViewModel);
    }
}
