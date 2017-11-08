package com.fyp.layim.web;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.service.GroupService;
import com.fyp.layim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;

/**
 * @author fyp
 * @crate 2017/11/2 22:50
 * @project SpringBootLayIM
 */
@RestController
@RequestMapping("/layim")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping(value = "/base/{uid}")
    public JsonResult getBaseData(@PathVariable("uid") Long userId){
        return userService.getBaseList(userId);
    }

    @GetMapping(value="/members")
    public JsonResult getMembers( long id){
        return groupService.getGroupMembers(id);
    }

}
