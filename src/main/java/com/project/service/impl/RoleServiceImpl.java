package com.project.service.impl;

import com.project.entity.Role;
import com.project.repository.RoleRepository;
import com.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleByRole(String role) {
        return roleRepository.findRoleByRole(role).orElseThrow(() -> new NullPointerException("Cannot find role"));
    }
}
