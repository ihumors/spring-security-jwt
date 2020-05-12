package com.example.security.jwt.system.service.impl;

import com.example.security.jwt.security.entity.JwtUser;
import com.example.security.jwt.system.entity.User;
import com.example.security.jwt.system.enums.UserStatusEnum;
import com.example.security.jwt.system.exception.UserNameAlreadyExistException;
import com.example.security.jwt.system.repository.UserRepository;
import com.example.security.jwt.system.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author PD
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(Map<String, String> registerUser) {
        Optional<User> optionalUser = userRepository.findByUsername(registerUser.get("username"));
        if (optionalUser.isPresent()) {
            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
        }
        User user = new User();
        user.setUsername(registerUser.get("username"));
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRoles("DEV,PM");
        user.setStatus(UserStatusEnum.NORMAL);
        user.setActivated(false);
        userRepository.save(user);
    }

    @Override
    public User findUserByUserName(String name) {
        return userRepository.findByUsername(name)
                .orElse(null);
    }

    @Override
    public void deleteUserByUserName(String name) {
        userRepository.deleteByUsername(name);
    }

    @Override
    public Page<User> getAllUser(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize));
    }

    @Override
    public JwtUser getCurrentUser() {
        return new JwtUser(findUserByUserName(getCurrentUserName()));
    }

    /**
     * 由于在JWTAuthorizationFilter这个类注入UserDetailsServiceImpl一致失败，
     * 导致无法正确查找到用户，所以存入Authentication的Principal为从 token 中取出的当前用户的姓名
     */
    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
