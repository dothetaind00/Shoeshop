package com.project.service;

import com.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findByIsEnable(Boolean isEnable, Pageable pageable);

    User findById(Integer id);

    User findUserByUserNameAndIsEnable(String userName, Boolean isEnable);

    Boolean existUserByTokenAndIsEnable(String token, Boolean isEnable);

    Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable);

    Boolean existUserByUserName(String username);

    Optional<User> findUserByUserName(String username);

    User save(User user);

    User postUser(User user);

    void delete(Integer id);

    void enableUser(Boolean isEnable, Integer id);
}
