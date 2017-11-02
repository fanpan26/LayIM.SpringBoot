package com.fyp.layim.domain.viewmodels;

/**
 * @author fyp
 * @crate 2017/11/2 22:58
 * @project SpringBootLayIM
 */
public class UserViewModel extends BaseViewModel {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String avatar;
    private String status;
    private String sign;
}
