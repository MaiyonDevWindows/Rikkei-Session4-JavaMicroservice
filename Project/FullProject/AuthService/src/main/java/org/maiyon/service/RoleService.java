package org.maiyon.service;

import org.maiyon.model.entity.Role;
import org.maiyon.model.enums.RoleName;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role findByRoleName(RoleName roleName);
}
