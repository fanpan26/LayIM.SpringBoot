package com.fyp.layim.service;

import com.fyp.layim.common.util.AESUtil;
import com.fyp.layim.domain.BigGroup;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.UserAccount;
import com.fyp.layim.domain.mapper.LayimMapper;
import com.fyp.layim.domain.result.JsonResult;

import com.fyp.layim.domain.viewmodels.FriendGroupViewModel;
import com.fyp.layim.domain.viewmodels.LayimInitDataViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;
import com.fyp.layim.im.common.util.PushUtil;
import com.fyp.layim.im.packet.ContextUser;
import com.fyp.layim.im.packet.LayimContextUserInfo;
import com.fyp.layim.repository.FriendGroupRepository;
import com.fyp.layim.repository.UserAccountRepository;
import com.fyp.layim.repository.UserRepository;
import com.fyp.layim.service.auth.ShiroUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fyp
 * @crate 2017/11/1 23:11
 * @project SpringBootLayIM
 */
@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendGroupRepository friendGroupRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;
    /**
    * 获取init接口所需要的数据结果
    *@return 返回 JsonResult(LayimInitDataViewModel)
    */
    public JsonResult getBaseList(){
        Long userId =ShiroUtil.getUserId();
        LayimInitDataViewModel resultViewModel = new LayimInitDataViewModel();

        //开始构造
        //获取用户基本信息
        User user = userRepository.findOne(userId);
        if(user == null){
            return JsonResult.fail("用户不存在");
        }
        LayimMapper mapper = new LayimMapper();
        //映射用户信息
        UserViewModel mine = mapper.mapUser(user);
        resultViewModel.setMine(mine);
        //获取好友分组信息
        List<FriendGroup> friendGroups = user.getFriendGroups();
        List<FriendGroupViewModel> friendGroupViewModels = new ArrayList<>(friendGroups.size());
        //遍历好友分组
        boolean isOnline;
        for (FriendGroup friendGroup : friendGroups) {
            List<User> usersInGroup = friendGroup.getUsers();
            //先映射群组信息
            FriendGroupViewModel friendGroupViewModel = mapper.mapFriendGroup(friendGroup);
            //将每个组的人放到好友分组里面
            List<UserViewModel> userViewModels = mapper.mapUser(usersInGroup);

            for (UserViewModel userViewModel : userViewModels) {
                isOnline = PushUtil.isOnline(userViewModel.getId());
                userViewModel.setSort(isOnline ? 1 : 0);
            }
            //在线状态排序
            Collections.sort(userViewModels);

            friendGroupViewModel.setList(userViewModels);
            friendGroupViewModels.add(friendGroupViewModel);
        }
        resultViewModel.setFriend(friendGroupViewModels);
        //获取群组信息
        List<BigGroup> bigGroups = user.getBigGroups();
        resultViewModel.setGroup(mapper.mapBigGroup(bigGroups));

        return JsonResult.success(resultViewModel);
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

    /**
     * 获取单条用户信息
     * */
    public User getUser(Long userId){
        return userRepository.findOne(userId);
    }

    /**
     * 获取token，访问ws
     * 本来想用websocker服务调用这边的api，后来想用简单方法，传个token过去，然后，在websocket服务器解析。
     * */
    public JsonResult getUserToken() throws Exception {
        Long userId = ShiroUtil.getUserId();
        if (userId > 0) {
            String key = String.format("%d_%d", userId, System.currentTimeMillis());
            logger.info("当前时间：{}",key);
            String token = AESUtil.encyrpt(key);
            return JsonResult.success(token);
        }
        return JsonResult.fail("用户不存在");
    }

    /**
     * 用户注册
     * */
    @Transactional
    public JsonResult register(UserAccount account,User user) {
        try {
            //添加账户
            UserAccount existAccount = userAccountRepository.findByUsername(account.getUsername());
            if (existAccount != null) {
                return JsonResult.fail("该账号已经存在");
            }
            account = userAccountRepository.save(account);
            user.setId(account.getId());
            //添加用户
            user = userRepository.save(user);
            //添加好友分组
            FriendGroup friendGroup = new FriendGroup();
            friendGroup.setName("我的好友");
            friendGroup.setOwner(user);
            friendGroupRepository.save(friendGroup);
            return JsonResult.success(user.getId());
        }
        catch (Exception ex){
            return JsonResult.fail("注册失败");
        }
    }

    /**
     * 获取当前用户的所有好友
     * */
    @Transactional
    public  List<String> getAllFriends(long userId){
        List<FriendGroup> friendGroups = friendGroupRepository.getAllByUserId(userId);
        List<String> userIds = new ArrayList<>();
        friendGroups.forEach(group->
            userIds.addAll(group.getUsers()
                    .stream()
                    .map(user -> user.getId().toString())
                    .collect(Collectors.toList())));
       return userIds;
    }

}
