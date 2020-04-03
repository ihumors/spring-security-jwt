package com.example.security.jwt.security.utils;

import com.example.security.jwt.security.constants.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PD
 */
public class JwtTokenUtils {


    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    /**
     * 创建Token
     * @param username
     * @param roles
     * @param isRememberMe
     * @return
     */
    public static String createToken(String username, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? SecurityConstants.EXPIRATION_REMEMBER : SecurityConstants.EXPIRATION;

        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("token_type", SecurityConstants.TOKEN_TYPE)
                // 加密设置
                .signWith(secretKey, SignatureAlgorithm.HS256)
                // 访问主张-角色
                .claim(SecurityConstants.ROLE_CLAIMS, String.join(",", roles))
                // 访问主张-权限
                .claim(SecurityConstants.PERMISSION_CLAIMS, new ArrayList<>())
                // 签发人
                .setIssuer("PD")
                // 设置签发时间
                .setIssuedAt(new Date())
                // 用户ID，面向用户
                .setSubject(username)
                // 设置到期时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                // 压缩，可选GZIP
                .compressWith(CompressionCodecs.DEFLATE);
        return SecurityConstants.TOKEN_PREFIX + jwtBuilder.compact();
    }

    public static boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String getUsernameByToken(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取用户所有角色
     * @param token
     * @return
     */
    public static List<SimpleGrantedAuthority> getUserRolesByToken(String token) {
        String role = (String) getTokenBody(token)
                .get(SecurityConstants.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
