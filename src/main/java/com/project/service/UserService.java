package com.project.service;

import com.project.entity.User;

import java.util.Optional;

public interface UserService {
    User findUserByUserNameAndIsEnable(String userName, Boolean isEnable);

    Boolean existUserByTokenAndIsEnable(String token, Boolean isEnable);

    Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable);

    User save(User user);

    User activeUser(User user);

    Boolean existUserByUserName(String username);

    Optional<User> findUserByUserName(String username);

    void delete(Integer id);
}
