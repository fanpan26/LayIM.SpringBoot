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
        if(!realCode.equals(vercode)){
            return "验证码不正确";
        }
        if(account==null || account.length()==0||account.length()>16){
            return "账号长度1-16位";
        }
        if(!(password.length()>=6 &&password.length()<=20)){
            return "密码长度6-20位";
        }
        if(!repassword.equals(password)){
            return "两次密码输入不一致";
        }
        return null;
    }


}
