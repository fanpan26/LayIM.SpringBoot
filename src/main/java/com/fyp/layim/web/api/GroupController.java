package com.fyp.layim.web.api;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/layim")
public class GroupController extends BaseApiController{

    @Autowired
    private GroupService groupService;
    /**
     * 添加一个好友分组
     * */
    @PutMapping(value = "/friend-group")
    public JsonResult friendGroup(HttpServletRequest request, String name) {
        return groupService.addFriendGroup(name, getUserId(request));
    }

    /**
     * 删除用户好友分组
     * */
    @DeleteMapping(value = "/friend-group/{id}")
    public JsonResult friendGroup(HttpServletRequest request,@PathVariable("id") Long id){
       return groupService.deleteFriendGroup(id,getUserId(request));
    }

    /**
     * 创建一个群组
     * */
    @PutMapping(value = "/big-group")
    public JsonResult bigGroup(HttpServletRequest request,String name,String avatar,String description){
        return  groupService.addBigGroup(getUserId(request),name,avatar,description);
    }
}
