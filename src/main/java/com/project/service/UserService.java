package com.project.service;

import com.project.entity.User;
import com.project.exception.CustomNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findByIsEnable(Boolean isEnable, Pageable pageable);

    Page<User> findByUserName(String username, Pageable pageable);

    User findById(Integer id);

    User findUserByUserNameAndIsEnable(String userName, Boolean isEnable) throws CustomNotFoundException;

    Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable);

    Optional<User> findUserByUserName(String username);

    User findByEmail(String email) throws CustomNotFoundException;

    User save(User user);

    User postUser(User user);

    void delete(Integer id);

    void enableUser(Boolean isEnable, Integer id);

    void updateToken(String token, String email);
}
