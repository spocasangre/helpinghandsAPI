package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.entities.Role;
import com.taquitosncapas.helpinghands.repositories.RoleRepository;
import com.taquitosncapas.helpinghands.services.definition.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Iterable<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> findRoleById(Long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save (role);
	}

	@Override
	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}
}
