package com.infervision.model;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq  token 构成使用 用户id， 用户名，时间戳构成
 * @Date 2019/3/9 1:26
 * @Version 1.0
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
