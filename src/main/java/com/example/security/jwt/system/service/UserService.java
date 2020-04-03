package com.example.security.jwt.system.service;

import com.example.security.jwt.security.entity.JwtUser;
import com.example.security.jwt.system.entity.User;
import org.springframework.data.domain.Page;

import java.util.Map;


public interface UserService {

    void saveUser(Map<String, String> registerUser);

    User findUserByUserName(String name);

    void deleteUserByUserName(String name);

    Page<User> getAllUser(int pageNum, int pageSize);

    JwtUser getCurrentUser();
}
