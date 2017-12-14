package com.fyp.layim.web.pages;

import com.fyp.layim.domain.User;
import com.fyp.layim.service.GroupService;
import com.fyp.layim.service.UserService;
import com.fyp.layim.web.base.BaseController;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserPageController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    /**
     * 属性赋值
     * */
    private void setModel(User user,Model model){
        long currentUserId = getUserId();
        long visitUserId = user.getId();
        //是否是自己
        boolean isSelf = currentUserId == visitUserId;
        //两个用户是否已经是好友
        boolean isFriend = groupService.isFriend(currentUserId,visitUserId);

        Map<String,Object> userMap = new HashMap<>(8);
        userMap.put("avatar",user.getAvatar());
        userMap.put("name",user.getUserName());
        userMap.put("addtime", DateUtil.date(user.getCreateAt())+" 加入");
        if(user.getSign()==null ||user.getSign().length()==0) {
            userMap.put("sign", "");
        }else {
            userMap.put("sign", "(" + user.getSign() + ")");
        }
        userMap.put("uid",user.getId());
        userMap.put("self",isSelf || isFriend);

        model.addAttribute("user",userMap);
    }
    /**
     * 用户首页
     * */
    @RequestMapping(value = "/{uid}")
    public String home(@PathVariable("uid") Long uid, Model model){
        User user = userService.getUser(uid);
        if(user==null){
            return "index";
        }
        setModel(user,model);
        return "/user/home";
    }

    /**
     * 用户设置页面
     * */
    @RequestMapping("/set")
    public String set(){
        return "/user/set";
    }
}
