package com.fyp.entity.result;

import com.fyp.entity.BigGroup;
import com.fyp.entity.FriendGroup;
import com.fyp.entity.User;

import java.util.List;

public class InitResult {
     public User getMine() {
          return mine;
     }

     public void setMine(User mine) {
          this.mine = mine;
     }

     public List<FriendGroup> getFriend() {
          return friend;
     }

     public void setFriend(List<FriendGroup> friend) {
          this.friend = friend;
     }

     public List<BigGroup> getGroup() {
          return group;
     }

     public void setGroup(List<BigGroup> group) {
          this.group = group;
     }

     private User mine;
     private List<FriendGroup> friend;
     private List<BigGroup> group;


}
