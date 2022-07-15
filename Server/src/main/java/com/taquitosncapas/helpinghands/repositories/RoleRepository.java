package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
