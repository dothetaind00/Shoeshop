package com.project.service;

import com.project.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Contact findById(Integer id);

    Contact findByEmailOrPhone(String email, String phone);

    Boolean existByEmailAndPhone(String email, String phone);

    List<Contact> findAll();

    Contact save(Contact contact);

    void delete(Integer id);
}
