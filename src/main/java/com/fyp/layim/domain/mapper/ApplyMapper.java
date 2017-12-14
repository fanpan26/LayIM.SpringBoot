package com.fyp.layim.domain.mapper;

import com.fyp.layim.domain.Apply;
import com.fyp.layim.domain.ApplyType;
import com.fyp.layim.domain.viewmodels.ApplyViewModel;

import java.util.List;
import java.util.stream.Collectors;

public final class ApplyMapper {
    public static List<ApplyViewModel> mapApply(List<Apply> applies){
        if(applies==null || applies.size()==0){
            return null;
        }
        return applies.stream().map(apply -> mapApply(apply)).collect(Collectors.toList());
    }

    private static ApplyViewModel mapApply(Apply apply){
        ApplyViewModel viewModel = new ApplyViewModel();
        viewModel.setId(apply.getId());
        viewModel.setAvatar(apply.getAvatar());
        viewModel.setName(apply.getName());
        viewModel.setContent(getContent(apply.getType(),apply.getRemark()));
        viewModel.setTime(apply.getCreateAt());
        viewModel.setUid(apply.getUid());
        viewModel.setRemark(apply.getRemark());
        viewModel.setResult(apply.getResult());
        viewModel.setGroup(apply.getGroup());
        return viewModel;
    }

    private static String getContent(int type,String remark){
       if(type == ApplyType.friend){
           return "申请添加你为好友";
       }
       if(type==ApplyType.group){
           return "申请加入群";
       }
       if(type==ApplyType.system){
            return remark;
       }
       return "";
    }
}
