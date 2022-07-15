package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.active = ?1")
    public Page<Project> projectByActive(Integer state, Pageable pageable);

    //Previews para ser observados por Volunteers
    @Query("SELECT p FROM Project p WHERE p.active = ?1 AND p.isFinished = 0")
    public Page<Project> projectPreviewGetAll(Integer state, Pageable pageable);

    //Previews para ser observados por Volunteers
    @Query("SELECT p FROM Project p WHERE p.active = 1 AND p.isFinished = 0 AND p.category.id = ?1")
    public Page<Project> projectPreviewByCategory(Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.owner.id = ?1 AND p.active = ?2 AND p.isFinished = 0")
    public Page<Project> myOrgProjectsByActive(Long orgId, Integer active, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.owner.id = ?1  AND p.isFinished = 0")
    public Page<Project> myOrgProjectsNotFinished(Long orgId, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.owner.id = ?1 AND p.isFinished = 1")
    Page<Project> myOrgProjectThatAreFinished(Long orgId, Pageable pageable);

    public Page<Project> getProjectByActive(Integer active, Pageable pageable);

}