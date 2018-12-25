package com.fyp.utils.jwt;

public class JwtTokenResult {

    public JwtTokenResult(String token){
     this.token = token;
    }

    private String token;
    private Long expires = 7200L;

    public String getToken(){
        return token;
    }

    public Long getExpires(){
        return expires;
    }

    public Boolean IsSuccess(){
        return token != null;
    }
}
