package com.project.service.impl;

import com.google.api.gax.rpc.NotFoundException;
import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> findAllPaging(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NullPointerException("Cannot find user"));
    }

    @Transactional
    @Override
    public User findUserByUserNameAndIsEnable(String userName, Boolean isEnable) {
        return userRepository.findUserByUserNameAndIsEnable(userName, isEnable).orElse(null);
    }

    @Override
    public Boolean existUserByTokenAndIsEnable(String token, Boolean isEnable) {
        return userRepository.existsUserByTokenAndIsEnable(token, isEnable);
    }

    @Override
    public Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable) {
        return userRepository.findUserByTokenAndIsEnable(token,isEnable);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null){
            if (userRepository.existsUserByUserName(user.getUserName())){
                return null;
            }

            String token = UUID.randomUUID().toString();
            user.setToken(token);

            user.setPassword(passwordEncoder.encode(user.getPsw()));
            user.setIsEnable(false);

            return userRepository.save(user);
        }

        User u = userRepository.findUserByUserNameAndIsEnable(user.getUserName(), true).orElse(null);
        if (u != null){
            u.setFullName(user.getFullName());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setAddress(user.getAddress());
            return userRepository.save(u);
        }
        return null;
    }

    @Override
    public User postUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean existUserByUserName(String username) {
        return userRepository.existsUserByUserName(username);
    }

    @Override
    public Optional<User> findUserByUserName(String username) {
        return userRepository.findUserByUserName(username);
    }


    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
