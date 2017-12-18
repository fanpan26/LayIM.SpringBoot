package com.fyp.layim.domain.mapper;

import com.fyp.layim.domain.BigGroup;
import com.fyp.layim.domain.FriendGroup;
import com.fyp.layim.domain.User;
import com.fyp.layim.domain.viewmodels.BigGroupViewModel;
import com.fyp.layim.domain.viewmodels.FriendGroupViewModel;
import com.fyp.layim.domain.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LayimMapper {
    public LayimMapper() { }

    public UserViewModel mapUser(User user) {
        if (user == null) {
            return null;
        } else {
            UserViewModel userViewModel = new UserViewModel();
            userViewModel.setUsername(user.getUserName());
            userViewModel.setId(user.getId());
            userViewModel.setAvatar(user.getAvatar());
            userViewModel.setSign(user.getSign());
            return userViewModel;
        }
    }

    public List<UserViewModel> mapUser(List<User> user) {
        if (user == null) {
            return null;
        } else {
            List<UserViewModel> list = new ArrayList(user.size());
            Iterator var3 = user.iterator();

            while(var3.hasNext()) {
                User user1 = (User)var3.next();
                list.add(this.mapUser(user1));
            }

            return list;
        }
    }

    public FriendGroupViewModel mapFriendGroup(FriendGroup friendGroup) {
        if (friendGroup == null) {
            return null;
        } else {
            FriendGroupViewModel friendGroupViewModel = new FriendGroupViewModel();
            friendGroupViewModel.setGroupname(friendGroup.getName());
            friendGroupViewModel.setId(friendGroup.getId());
            return friendGroupViewModel;
        }
    }

    public List<FriendGroupViewModel> mapFriendGroup(List<FriendGroup> friendGroups) {
        if (friendGroups == null) {
            return null;
        } else {
            List<FriendGroupViewModel> list = new ArrayList(friendGroups.size());
            Iterator var3 = friendGroups.iterator();

            while(var3.hasNext()) {
                FriendGroup friendGroup = (FriendGroup)var3.next();
                list.add(this.mapFriendGroup(friendGroup));
            }

            return list;
        }
    }

    public BigGroupViewModel mapBigGroup(BigGroup bigGroup) {
        if (bigGroup == null) {
            return null;
        } else {
            BigGroupViewModel bigGroupViewModel = new BigGroupViewModel();
            bigGroupViewModel.setGroupname(bigGroup.getGroupName());
            bigGroupViewModel.setId(bigGroup.getId());
            bigGroupViewModel.setAvatar(bigGroup.getAvatar());
            return bigGroupViewModel;
        }
    }

    public List<BigGroupViewModel> mapBigGroup(List<BigGroup> bigGroups) {
        if (bigGroups == null) {
            return null;
        } else {
            List<BigGroupViewModel> list = new ArrayList(bigGroups.size());
            Iterator var3 = bigGroups.iterator();

            while(var3.hasNext()) {
                BigGroup bigGroup = (BigGroup)var3.next();
                list.add(this.mapBigGroup(bigGroup));
            }

            return list;
        }
    }
}