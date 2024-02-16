package org.maiyon.service;

import org.maiyon.model.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findByRoleName(String roleName);
}
