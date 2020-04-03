package com.example.security.jwt.security.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author PD
 */
@Component
public class SecurityConstants {
    /**
     * 登录的地址
     */
    public static String AUTH_LOGIN_URL = "/auth/login";
    /**
     * 角色的key
     **/
    public static String ROLE_CLAIMS = "roles";
    /**
     * 权限的key
     **/
    public static String PERMISSION_CLAIMS = "permissions";
    /**
     * rememberMe 为 false 的时候过期时间是1个小时
     */
    public static long EXPIRATION = 60 * 60;
    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    public static long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7;
    /**
     * JWT签名密钥硬编码到应用程序代码中，应该存放在环境变量或.properties文件中。
     */
    public static String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";
    /**
     * token header
     */
    public static String TOKEN_HEADER = "Authorization";
    /**
     * token prefix
     */
    public static String TOKEN_PREFIX = "Bearer ";
    /**
     * token type
     */
    public static String TOKEN_TYPE = "JWT";

    @Value("${spring.security.auth.login.url}")
    public void setAuthLoginUrl(String authLoginUrl) {
        SecurityConstants.AUTH_LOGIN_URL = authLoginUrl;
    }

    @Value("${spring.security.auth.role.claims}")
    public void setRoleClaims(String roleClaims) {
        SecurityConstants.ROLE_CLAIMS = roleClaims;
    }

    @Value("${spring.security.auth.permission.claims}")
    public void setPermissionClaims(String permissionClaims) {
        SecurityConstants.PERMISSION_CLAIMS = permissionClaims;
    }

    @Value("${spring.security.auth.expiration}")
    public void setExpiration(long expiration) {
        SecurityConstants.EXPIRATION = expiration;
    }

    @Value("${spring.security.auth.expiration.remember}")
    public void setExpirationRemember(long expirationRemember) {
        SecurityConstants.EXPIRATION_REMEMBER = expirationRemember;
    }

    @Value("${spring.security.auth.jwt.secret.key}")
    public void setJwtSecretKey(String jwtSecretKey) {
        SecurityConstants.JWT_SECRET_KEY = jwtSecretKey;
    }

    @Value("${spring.security.auth.token.header}")
    public void setTokenHeader(String tokenHeader) {
        SecurityConstants.TOKEN_HEADER = tokenHeader;
    }

    @Value("${spring.security.auth.token.prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        SecurityConstants.TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${spring.security.auth.token.type}")
    public void setTokenType(String tokenType) {
        SecurityConstants.TOKEN_TYPE = tokenType;
    }
}
