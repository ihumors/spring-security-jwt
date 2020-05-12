package com.example.security.jwt.security.service;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {



    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String userName = ((UsernamePasswordAuthenticationToken) e.getSource()).getPrincipal() + "";

        // todo 处理登陆失败次数

    }
}
