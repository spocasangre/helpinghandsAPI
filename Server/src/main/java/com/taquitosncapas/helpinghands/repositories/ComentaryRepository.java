package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Comentary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComentaryRepository extends JpaRepository<Comentary, Long> {

    @Query("SELECT c FROM Comentary c WHERE c.project.id = ?1")
    public Page<Comentary> getAllByProjectId(Long id, Pageable pageable);

    @Query("SELECT c FROM Comentary c WHERE c.user.id = ?1 AND c.project.id = ?2")
    Optional<Comentary> findComentaryByUserAndProject(Long userId, Long projectId);
}
