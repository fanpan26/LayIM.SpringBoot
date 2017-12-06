package com.fyp.layim.service;

import com.fyp.layim.domain.Apply;
import com.fyp.layim.domain.ApplyType;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.im.packet.ContextUser;
import com.fyp.layim.repository.ApplyRepository;
import com.fyp.layim.service.auth.ShiroUtil;
import com.xiaoleilu.hutool.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

/**
 * @author fyp
 * @crate 2017/12/6 19:42
 * @project SpringBootLayIM
 */
@Service
public class ApplyService {

    @Autowired
    private ApplyRepository applyRepository;

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

        int record = applyRepository.countByToidAndTypeAndResult(toId,ApplyType.friend,0);
        if(record > 0){
            return JsonResult.fail("已经申请过");
        }
        ContextUser user = ShiroUtil.getCurrentUser();

        Apply apply  = new Apply();
        apply.setType(ApplyType.friend);
        apply.setToid(toId);
        apply.setRemark(remark);

        apply.setUid(Long.parseLong(user.getUserid()));
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
}
