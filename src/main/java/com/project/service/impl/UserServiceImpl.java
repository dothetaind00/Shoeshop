package com.project.service.impl;

import com.project.entity.Role;
import com.project.entity.User;
import com.project.repository.RoleRepository;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User findUserByUserNameAndIsEnable(String userName, Boolean isEnable) {

        return userRepository.findUserByUserNameAndIsEnable(userName, isEnable).orElse(null);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null){
            if (userRepository.existsUserByUserName(user.getUserName())){
                return null;
            }
            user.setPassword(passwordEncoder.encode(user.getPsw()));
            user.setIsEnable(true);
            //add role for user
            Role role = roleRepository.findRoleByRole("USER").orElseThrow(() -> new NullPointerException("Cannot find Role"));
            user.addRole(role);
            return userRepository.save(user);
        }

        User u = userRepository.findById(user.getId()).orElse(null);
        if (u != null){
            u.setFullName(user.getFullName());
            u.setEmail(user.getEmail());
            u.setAddress(user.getAddress());
            u.setPhone(user.getPhone());
            u.setToken(user.getToken());
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public Boolean existUserByUserName(String username) {
        return userRepository.existsUserByUserName(username);
    }
}
