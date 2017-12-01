package com.fyp.layim.web.form;

import com.fyp.layim.common.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterParam {
    private String account;
    private String nickname;
    private String password;
    private String repassword;
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    private String vercode;

    public String getCheckMessage(String realCode){
        if(realCode != vercode){
            return "vercode:验证码不正确";
        }
        if(RegexUtil.isValid(account,"[a-zA-Z]{1}[a-zA-Z0-9_]{1,15}")){
            return "account:由字母数字下划线组成且开头必须是字母，不能超过16位";
        }
        if(!(password.length()>=6 &&password.length()<=20)){
            return "password:密码长度6-20位";
        }
        if(repassword!=password){
            return "repassword:两次密码输入不一致";
        }
        return null;
    }


}
