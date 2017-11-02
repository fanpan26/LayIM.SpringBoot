package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.repository.BigGroupRepository;
import com.fyp.layim.repository.FriendGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fyp
 * @crate 2017/11/2 20:30
 * @project SpringBootLayIM
 */
@Service
public class GroupService {
    @Autowired
    private FriendGroupRepository friendGroupRepository;

    @Autowired
    private BigGroupRepository bigGroupRepository;

    /**
    * 添加一个好友分组
    *@param
    *@param
    *@return
    */
    public JsonResult addFriendGroup(FriendGroup friendGroup){
       friendGroup = friendGroupRepository.save(friendGroup);
       if(friendGroup.getId()>0){
           return ResultUtil.success(friendGroup.getId());
       }
       return ResultUtil.fail("添加失败");
    }
}
