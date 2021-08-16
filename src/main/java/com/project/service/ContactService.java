package com.project.service;

import com.project.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Page<Contact> findAllContact(Pageable pageable);

    Page<Contact> findAllByName(String name, Pageable pageable);

    Optional<Contact> findById(Integer id);

    Contact findByPhone(String phone);

    Boolean existByEmailAndPhone(String email, String phone);

    Long totalRecord();

    List<Contact> findAll();

    Page<Contact> findAllPaging(Pageable pageable);

    Contact save(Contact contact);

    void delete(Contact contact);
}
