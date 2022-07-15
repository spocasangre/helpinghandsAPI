package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Favorite;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Page<Favorite> getFavoriteByUser(User user, Pageable pageable);

    @Query("SELECT f FROM Favorite f" +
            " WHERE f.user.id = ?1 AND f.project.isFinished NOT IN (1)")
    Page<Favorite> getFavoriteByUserId(Long id, Pageable pageable);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = ?1 AND f.project.id = ?2")
    Optional<Favorite> getFavoriteByUserAndAndProject(Long userId, Long projectId);

    @Query("SELECT f.project FROM Favorite f WHERE f.id = ?1 AND f.project.isFinished NOT IN (1)")
    Optional<Project> getProjectByFavoriteId(Long id);
}
