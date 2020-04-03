# spring-security-jwt-example

**项目参考：** https://gitee.com/SnailClimb/spring-security-jwt-guide 。

## 介绍

[Spring Security](https://spring.io/projects/spring-security ) 是 Spring 全家桶中非常强大的一个用来做身份验证以及权限控制的框架，我们可以轻松地扩展它来满足我们当前系统安全性这方面的需求。

但是 Spring Security 相比于其他一些技术比如 JPA 来说更难上手，很多人初学的时候很难通过看视频或者文档发就很快能独立写一个 Demo 出来，于是后面可能就放弃了学习这个东西。

刚来公司的时候的入职培训实战项目以及现在正在做的项目都用到了 Spring Security 这个强大的安全验证框架，可以看出这个框架在身份验证以及权限验证领域可以说应该是比较不错的选择。由于之前经历项目的这部分模块都不是自己做的，所以对于 Spring Security 并不是太熟悉。于是自/己抽时间对这部分知识学习了一下，并实现了一个简单的 Demo 。这个 Demo 主要用到了 **Spring Security** 和 **Spring Boot** 这两门技术，并且所有的依赖采用的都是最新的稳定版本。初次之外，这个项目还用到了 JPA 这门技术。项目代码结构如下(chrome 插件：Octoree)，整体还是比较清晰的，由于自己的能力以及时间有限，所以一定还有很多可以优化的地方，有兴趣的朋友可以一起完善，期待你的 PR。


## 下载配置

1. git clone git@github.com:ihumors/spring-security-jwt.git
2. 打开项目并且等待 Maven 下载好相关依赖。建议使用 Intellij IDEA 打开，并确保你的 Intellij IDEA 下载了 lombok 插件。
3. 修改 `application.properties` 将数据库连接信息改成你自己的。
4. 运行项目（相关数据表会被自动创建，不了解的看一下 JPA）

## 示例

### 1.注册一个账号

localhost:9333/auth/register
#####注册参数示例：
```json
{
	"username":"root",
	"password":"12345"
}
```

### 2.登录

localhost:3214/api/auth/login
#####注册参数示例：

```json
{"username": "root", "password": "123456","rememberMe":true}
```
登陆成功后，会在response header 里面添加Authorization

### 3.匿名访问接口：不需要登陆
localhost:9333/api/anyAll

在对应的RestController下的接口上加上注解

@AnonymousAccess
### 4.不带 Token 访问需要进行身份验证的资源
localhost:9333/api/users
```json
{
    "timestamp": "2020-04-03T03:52:58.877+0000",
    "status": 401,
    "error": "Unauthorized",
    "message": "Full authentication is required to access this resource",
    "path": "/api/users"
}
```
### 5.带 Token 访问需要进行身份验证的资源
localhost:9333/api/users
在header里面加添加Authorization并给定值
```json
{
    "content": [
        {
            "id": 1,
            "username": "root",
            "password": "$2a$10$3W/u86MY0sPuDnMLdpkaKOyKFqC13kfSyqS6kqulb29PehkDP71dK",
            "status": "NORMAL",
            "roles": [
                {
                    "authority": "ROLE_DEV"
                },
                {
                    "authority": "ROLE_PM"
                }
            ],
            "activated": true
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 1,
    "last": true,
    "totalElements": 1,
    "number": 0,
    "size": 10,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```
## 参考

- https://dev.to/keysh/spring-security-with-jwt-3j76
