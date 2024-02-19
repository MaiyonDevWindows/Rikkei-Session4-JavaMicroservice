package org.maiyon.service.Impl;

import org.maiyon.model.entity.Role;
import org.maiyon.model.enums.RoleName;
import org.maiyon.repository.RoleRepository;
import org.maiyon.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(
                ()->new RuntimeException("Can not found Role by role name.")
        );
    }
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
