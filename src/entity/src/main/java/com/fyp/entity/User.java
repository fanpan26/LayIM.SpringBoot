package com.fyp.entity;

public class User {
    public static final User Anonymous = new User("匿名","");

    public User(){

    }
    public User(String name,String avatar){
        setUsername(name);
        setAvatar(avatar);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String avatar;
    private String username;
    private String sign;
}
