package com.fyp.layim.service;

import com.fyp.layim.common.util.ResultUtil;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fyp
 * @crate 2017/11/1 23:11
 * @project SpringBootLayIM
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
}
