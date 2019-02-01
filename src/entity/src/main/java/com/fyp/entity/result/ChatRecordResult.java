package com.fyp.entity.result;

public class ChatRecordResult implements Comparable<ChatRecordResult>{
    private long id;

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }

    private boolean self;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String avatar;
    private long timestamp;
    private String content;

    @Override
    public int compareTo(ChatRecordResult o) {
        if (o.id == this.id) {
            return 0;
        }
        return o.id > this.id ? -1 : 1;
    }
}
