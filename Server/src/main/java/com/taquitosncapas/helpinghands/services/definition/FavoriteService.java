package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.entities.Favorite;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FavoriteService {

	public Iterable<Favorite> findAll();
	public Page<Favorite> findAllByUser(User user, Pageable pageable);
	public Page<Favorite> findAllByUserId(Long userId, Pageable pageable);
	public Optional<Favorite> findByUserAndProjectId(Long userId, Long projectId);
	public Optional<Favorite> findById(Long id);
	public void create(User user, Project project);
	public void deleteById(Long id);
	public Optional<Project> getProjectByFavoriteId(Long favoriteId);
}
