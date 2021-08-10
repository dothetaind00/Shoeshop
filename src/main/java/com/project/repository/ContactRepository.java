package com.project.repository;

import com.project.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findContactByEmailLikeOrPhoneLike(String email, String phone);

    Boolean existsByEmailAndPhone(String email, String phone);
}
