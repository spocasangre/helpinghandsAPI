package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Approval;
import com.taquitosncapas.helpinghands.models.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    @Query("SELECT a FROM Approval a WHERE a.project.id = ?1")
    Optional<Approval> getOneByProjectId(Long id);

    @Query("SELECT a.project FROM Approval a WHERE a.user.id = ?1")
    Page<Project> getAllProjectsByRespondantId(Long respondantId, Pageable pageable);
}
