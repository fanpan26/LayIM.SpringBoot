package com.fyp.layim.service;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fyp.layim.common.util.IdUtil;
import com.fyp.layim.domain.*;
import com.fyp.layim.domain.base.LayimPage;
import com.fyp.layim.domain.mapper.ApplyMapper;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.domain.viewmodels.ApplyViewModel;
import com.fyp.layim.im.packet.ContextUser;
import com.fyp.layim.repository.ApplyRepository;
import com.fyp.layim.repository.FriendGroupRepository;
import com.fyp.layim.service.auth.ShiroUtil;
import com.xiaoleilu.hutool.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.tio.utils.json.Json;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/12/6 19:42
 * @project SpringBootLayIM
 */
@Service
public class ApplyService {

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private FriendGroupRepository friendGroupRepository;


    /**
     * 保存一条申请记录
     * */
    private JsonResult saveApply(Apply apply){
        applyRepository.save(apply);
        return JsonResult.success();
    }

    /**
     * 提交好友申请
     * */
    public JsonResult saveFriendApply(long toId,String remark){

        remark = HtmlUtils.htmlEscape(remark);
        ContextUser user = ShiroUtil.getCurrentUser();
        long userId = Long.parseLong(user.getUserid());
        if(toId==userId){
            return JsonResult.fail("不可加自己为好友");
        }
        int record = applyRepository.countByToidAndUidAndTypeAndResult(toId,userId,ApplyType.friend,0);
        if(record > 0) {
            return JsonResult.fail("已经申请过");
        }

        Apply apply  = new Apply();
        apply.setType(ApplyType.friend);
        apply.setToid(toId);
        apply.setRemark(remark);

        apply.setUid(userId);
        apply.setAvatar(user.getAvatar());
        apply.setName(user.getUsername());

        apply.setRead(false);
        apply.setResult(0);
        return saveApply(apply);
    }


    /**
     * 获取未读消息的条数
     * */
    public int getUnreadMsgCount(long userId){
        return  applyRepository.countByToidAndIsread(userId,false);
    }

    /**
     * 获取系统消息列表
     * pageIndex=1的时候不要传1，要传0 ，否则第一页的数据的第一条取不出
     * */
    public JsonResult getSystemNotices(int pageIndex,int pageSize,long toId){
        if(pageIndex==1) {
            pageIndex = 0;
        }
        Page<Apply> applies = applyRepository.findAppliesByToidOrderByCreateAtDesc(toId,new PageRequest(pageIndex,pageSize));

        LayimPage<ApplyViewModel> pageRes = new LayimPage<>();
        pageRes.setTotal(applies.getTotalElements());
        pageRes.setPage(applies.getTotalPages());
        pageRes.setData(ApplyMapper.mapApply(applies.getContent()));

        return JsonResult.success(pageRes);
    }

    /**
     * 将用户消息设置成已读
     * */
    public JsonResult setUnreadToRead(long uid){
        applyRepository.updateUnreadToRead(uid);
        return JsonResult.success();
    }

    /**
     * 同意 处理逻辑
     * 更新同意状态，然后添加好友或者加群
     * */
    @Transactional
    public JsonResult agreeApply(long uid,long id,long group){
        Apply myApply = applyRepository.findOne(id);
        //没有该申请
        if(myApply==null){
            return JsonResult.fail("申请不存在");
        }
        //不是自己的申请，不处理
        if(myApply.getToid() != uid){
            return JsonResult.fail("非法的请求");
        }
        //先将消息设置为已经同意
        applyRepository.updateResult(id,ApplyStatus.agree);
        //给对方发送一条已经处理的消息
        Apply handleApply = new Apply();
        handleApply.setType(ApplyType.system);
        handleApply.setToid(myApply.getUid());

        ContextUser user = ShiroUtil.getCurrentUser();
        handleApply.setRemark(user.getUsername() +" 同意了你的好友请求");

        handleApply.setRead(false);
        handleApply.setResult(ApplyStatus.agree);
        applyRepository.save(handleApply);
        //加对方为好友或者群
        return handle(myApply,group);
    }

    /**
     * 根据类型进行处理
     * */
    private JsonResult handle(Apply apply,long groupId){
        switch (apply.getType()) {
            case ApplyType.friend:

                /**
                 * 获取我的好友分组
                 * 获取对方好友分组
                 * 互相加好友
                 * 保存
                 * */
                boolean isFriend =  friendGroupRepository.countByUidInGroup(apply.getToid(),apply.getUid()) > 0;
                if(isFriend){
                    return JsonResult.success();
                }
                //获取对方的分组默认取第一个,将当前用户放到对方好友分组里
                FriendGroup toGroup = friendGroupRepository.getFirstByUserId(apply.getUid());
                User me = new User();
                me.setId(apply.getToid());
                toGroup.getUsers().add(me);
                //将对方用户放到当前用户的好友分组里
                FriendGroup myGroup = friendGroupRepository.getFirstById(groupId);
                User friend = new User();
                friend.setId(apply.getUid());
                myGroup.getUsers().add(friend);

                //保存两个分组数据
                List<FriendGroup>friendGroups = new ArrayList<>(2);
                friendGroups.add(toGroup);
                friendGroups.add(myGroup);

                friendGroupRepository.save(friendGroups);
                return JsonResult.success();
            case ApplyType.group:
                break;
            case ApplyType.system:
                break;
                default:
                    return JsonResult.fail("未知的处理类型");
        }
        return JsonResult.success();
    }
}
