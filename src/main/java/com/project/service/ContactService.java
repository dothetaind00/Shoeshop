package com.project.service;

import com.project.entity.Contact;

import java.util.List;

public interface ContactService {
    Contact findByEmailOrPhone(String email, String phone);

    Boolean existByEmailAndPhone(String email, String phone);

    List<Contact> findAll();

    Contact save(Contact contact);

    void delete(Integer id);
}
