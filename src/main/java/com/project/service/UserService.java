package com.project.service;

import com.project.entity.User;

public interface UserService {
    User findUserByUserNameAndIsEnable(String userName, Boolean isEnable);

    User save(User user);

    Boolean existUserByUserName(String username);
}
