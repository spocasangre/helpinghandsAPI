package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.entities.Favorite;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.repositories.FavoriteRepository;
import com.taquitosncapas.helpinghands.services.definition.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Favorite> findAll() {
		return favoriteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Favorite> findAllByUser(User user, Pageable pageable) {

		return favoriteRepository.getFavoriteByUser(user, pageable);
	}

	@Override
	public Page<Favorite> findAllByUserId(Long userId, Pageable pageable) {
		return favoriteRepository.getFavoriteByUserId(userId, pageable);
	}

	@Override
	public Optional<Favorite> findByUserAndProjectId(Long userId, Long projectId) {
		return favoriteRepository.getFavoriteByUserAndAndProject(userId, projectId);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Favorite> findById(Long id) {
		return favoriteRepository.findById(id);
	}

	@Override
	public void create(User user, Project project) {
		Favorite favorite = new Favorite();
		favorite.setUser(user);
		favorite.setProject(project);
		favoriteRepository.save(favorite);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		favoriteRepository.deleteById(id);
	}

	@Override
	public Optional<Project> getProjectByFavoriteId(Long favoriteId) {
		return favoriteRepository.getProjectByFavoriteId(favoriteId);
	}

}
