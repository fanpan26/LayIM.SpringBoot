package com.fyp.layim.im.packet;

import java.util.List;

public class LayimContextUserInfo {
    public ContextUser getContextUser() {
        return contextUser;
    }

    public void setContextUser(ContextUser contextUser) {
        this.contextUser = contextUser;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    private ContextUser contextUser;
    private List<String> groupIds;
}
