package com.example.security.jwt.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 匿名访问注解
 * 存在某些接口不需要权限认证就能访问，如果每次在antMatchers里进行添加：
 * 一不方便，二麻烦，三不利封装，四需要开发人员知道去配置
 * 五配置过多匿名接口自定义configure代码冗长，不方便维护
 * 使用在方法上打上@AnonymousAccess即可
 *
 * @author PD
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}