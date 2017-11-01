package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author fyp
 * @crate 2017/11/1 22:59
 * @project SpringBootLayIM
 */
@Entity
public class BigGroup extends DomainBase {
    @Column(length = 20)
    private String groupName;

    @Column(length = 120)
    private String avatar;

    private Long createUid;
    @Column(length = 100)
    private String description;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
