package com.example.security.jwt.security.controller;

import com.example.security.jwt.security.annotation.AnonymousAccess;
import com.example.security.jwt.system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @AnonymousAccess
    public ResponseEntity registerUser(@RequestBody Map<String, String> registerUser) {
        userService.saveUser(registerUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken() {

        return ResponseEntity.ok().build();
    }
    /**
     * 首次登陆更改密码
     *
     * @param resetUser
     * @return
     */
    @PutMapping("/password")
    public ResponseEntity password(@RequestBody Map<String, String> resetUser) {
        return ResponseEntity.ok().build();
    }

    /**
     * 注销登陆状态
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        return ResponseEntity.ok().build();
    }
}
