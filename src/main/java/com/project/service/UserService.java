package com.project.service;

import com.project.entity.User;

import java.util.Optional;

public interface UserService {
    User findUserByUserNameAndIsEnable(String userName, Boolean isEnable);

    Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable);

    User save(User user);

    Boolean existUserByUserName(String username);

    Boolean existUserByTokenAndIsEnable(String token, Boolean isEnable);

    void delete(Integer id);
}
