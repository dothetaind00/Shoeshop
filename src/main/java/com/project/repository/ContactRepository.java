package com.project.repository;

import com.project.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("SELECT c FROM Contact c")
    Page<Contact> findAllContact(Pageable pageable);

    Page<Contact> findAllByNameLike(String name, Pageable pageable);

    Optional<Contact> findContactByPhoneLike(String phone);

    Boolean existsByEmailAndPhone(String email, String phone);
}
