package com.fyp.layim.web.biz;

import com.fyp.layim.common.event.application.AddFriendEvent;
import com.fyp.layim.common.event.application.ApplyEvent;
import com.fyp.layim.common.event.bus.EventUtil;
import com.fyp.layim.common.event.bus.LayimEventType;
import com.fyp.layim.common.event.bus.body.AddFriendEventBody;
import com.fyp.layim.common.event.bus.body.ApplyEventBody;
import com.fyp.layim.common.event.bus.body.EventBody;
import com.fyp.layim.domain.result.ApplyResult;
import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.service.ApplyService;
import com.fyp.layim.service.GroupService;
import com.fyp.layim.service.UserService;
import com.fyp.layim.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * @author fyp
 * @crate 2017/11/2 22:50
 * @project SpringBootLayIM
 */
@RestController
@RequestMapping("/layim")
public class UserController extends BaseController {

    /**
     * 用于事件发布
     * applicationContext.publishEvent(event)
     * */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ApplyService applyService;

    /**
     * layim基础初始化数据
     * 好友信息，群组信息，个人信息
     * */
    @GetMapping(value = "/base")
    public JsonResult getBaseData(){
        return userService.getBaseList();
    }

    /**
     * 根据群ID获取群内的所有人
     * */
    @GetMapping(value="/members")
    public JsonResult getMembers( long id){
        return groupService.getGroupMembers(id);
    }

    /**
     * 获取用户token，调用api会用到token
     * */
    @GetMapping(value = "/token")
    public JsonResult getToken() throws Exception{
        return userService.getUserToken();
    }

    /**
     * 好友申请
     * */
    @PostMapping(value = "/apply-friend")
    public JsonResult apply(@RequestParam("toid") Long toId,@RequestParam("remark") String remark,@RequestParam("group") long group){
        boolean isFriend = groupService.isFriend(getUserId(),toId);
        if(isFriend){
            return JsonResult.success();
        }
        JsonResult result = applyService.saveFriendApply(toId, remark,group);
        //申请成功，发布申请事件，通知 toId处理消息，如果不在线，不会进行处理
        if(result.isSuccess()){
            publishApplyEvent(toId);
            //applicationContext.publishEvent(new ApplyEvent("apply",toId));
        }
        return result;
    }

    /**
     * 用户收到的通知分页
     * */
    @GetMapping(value = "/notice/{pageIndex}")
    public JsonResult apply(@PathVariable("pageIndex") int pageIndex){
        return applyService.getSystemNotices(pageIndex,20,getUserId());
    }

    /**
     * 用户未读消息个数
     * */
    @GetMapping(value = "/apply-unread")
    public JsonResult msgCount(){
        int count = applyService.getUnreadMsgCount(getUserId());
        return JsonResult.success(count);
    }

    /**
     * 设置消息已读
     * */
    @PostMapping(value = "/apply-read")
    public JsonResult unReadToRead(){
        return applyService.setUnreadToRead(getUserId());
    }

    /**
     * 处理申请消息 同意
     * @param id 为申请ID
     * */
    @PostMapping(value = "/apply/agree/{id}")
    public JsonResult handleFriendApply(@PathVariable("id") long id,long group){
        JsonResult result = applyService.agreeApply(getUserId(),id,group);
        //申请处理成功之后，给对方发送一条消息（要重构）
        if(result.isSuccess()&&result.getData()!=null){
            ApplyResult applyResult = (ApplyResult)result.getData();
            //推送系统消息
            //applicationContext.publishEvent(new ApplyEvent("apply",applyResult.getUid()));
            //推送添加好友成功的消息
           // applicationContext.publishEvent(new AddFriendEvent("addFriend", id));
            publishApplyEvent(applyResult.getUid());
            publishAddFriendEvent(id);
        }
        return result;
    }

    /**
     * 获取一个用户的好友分组
     * */
    @GetMapping(value = "/friend-groups")
    public JsonResult getUserFriendGroups(){
        return groupService.getUserFriendGroups(getUserId());
    }

    /**
     * 发布申请事件
     * */
    private void publishApplyEvent(long targetId){
        EventBody eventBody = new EventBody();
        eventBody.setEventType(LayimEventType.applyNotice);
        eventBody.setEventData(new ApplyEventBody(targetId));
        EventUtil.publish(eventBody);
    }
    /**
     * 发布添加好友成功事件
     * */
    private void publishAddFriendEvent(long applyId){
        EventBody eventBody = new EventBody();
        eventBody.setEventType(LayimEventType.addFriendNotice);
        eventBody.setEventData(new AddFriendEventBody(applyId));
        EventUtil.publish(eventBody);
    }
}
