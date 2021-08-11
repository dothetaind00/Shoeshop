package com.project.repository;

import com.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUserNameAndIsEnable(String userName, Boolean isEnable);

    Boolean existsUserByUserName(String username);

    Boolean existsUserByTokenAndIsEnable(String token, Boolean isEnable);

    Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable);
}
