package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author fyp
 * @crate 2017/12/6 19:31
 * @project SpringBootLayIM
 */
@Entity
public class Apply extends DomainBase {
    private long uid;
    private String avatar;
    private String name;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getToid() {
        return toid;
    }

    public void setToid(long toid) {
        this.toid = toid;
    }

    private String remark;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRead() {
        return isread;
    }

    public void setRead(boolean read) {
        this.isread = read;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private boolean isread;
    private int result;
    private int type;
    private long toid;

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    @Column(name = "group_id")
    private long group;
}
