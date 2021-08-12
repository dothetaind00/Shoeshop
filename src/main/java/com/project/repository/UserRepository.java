package com.project.repository;

import com.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByIsEnable(Boolean isEnable, Pageable pageable);

    Page<User> findByUserNameLike(String username, Pageable pageable);

    Optional<User> findUserByUserNameAndIsEnable(String userName, Boolean isEnable);

    Optional<User> findUserByTokenAndIsEnable(String token, Boolean isEnable);

    Optional<User> findUserByUserName(String username);

    Optional<User> findUserByEmail(String email);

    @Query("UPDATE User u set u.isEnable = :isEnable where u.id= :id")
    @Modifying
    void enableUser(@Param("isEnable") Boolean isEnable, @Param("id") Integer id);

    @Query("UPDATE User u set u.lastLogined = :lastLogined where u.userName= :username")
    @Modifying
    void setTimeLogin(@Param("lastLogined") Timestamp lastLogined, @Param("username") String username);

    @Query("UPDATE User u set u.token = :token where u.email= :email")
    @Modifying
    void updateToken(@Param("token") String token, @Param("email") String email);
}
