package com.example.security.jwt.security.filter;

import com.example.security.jwt.security.constants.SecurityConstants;
import com.example.security.jwt.security.entity.JwtUser;
import com.example.security.jwt.security.entity.LoginUser;
import com.example.security.jwt.security.utils.JwtTokenUtils;
import com.example.security.jwt.system.enums.UserStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 如果用户名和密码正确，那么过滤器将创建一个JWT Token 并在HTTP Response 的header中返回它，格式：token: "Bearer +具体token值"
 */
public class JWTUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;

    public JWTUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置URL，以确定是否需要身份验证
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 从输入流中获取到登录的信息
            LoginUser loginUser = objectMapper.readValue(request.getInputStream(), LoginUser.class);
            rememberMe.set(loginUser.getRememberMe());
            // 这部分和attemptAuthentication方法中的源码是一样的，
            // 只不过由于这个方法源码的是把用户名和密码这些参数的名字是死的，所以我们重写了一下
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginUser.getUsername(), loginUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果验证成功，就生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        if (!jwtUser.isActivated()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            buildResponseBody(response, "账号未激活");
            return;
        }

        if (jwtUser.getStatus() == UserStatusEnum.LOCKED.getCode()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            buildResponseBody(response, "账号已锁定，请联系管理员或稍后重试");
            return;
        }

        // todo 更新登陆时间，重置登陆失败次数为0等操作；

        List<String> roles = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 创建 Token
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), roles, rememberMe.get());
        // Http Response Header 中返回 Token
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);
        buildResponseBody(response, "登陆成功");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        if (authenticationException instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            buildResponseBody(response, "账号密码错误");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            buildResponseBody(response, "账号不存在");
        }
    }

    /**
     * 返回内容
     *
     * @param response response
     * @param msg      请设置为自己的输出对象
     */
    private void buildResponseBody(HttpServletResponse response, String msg) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(msg);
            printWriter.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
