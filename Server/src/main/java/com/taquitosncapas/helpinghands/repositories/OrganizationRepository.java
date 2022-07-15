package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> { }
