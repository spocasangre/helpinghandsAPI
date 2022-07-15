package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.entities.Role;

import java.util.Optional;

public interface RoleService {
	public Iterable<Role> findAllRoles();
	public Optional<Role> findRoleById(Long id);
	public Role saveRole(Role role);
	public void deleteRoleById(Long id);
}
