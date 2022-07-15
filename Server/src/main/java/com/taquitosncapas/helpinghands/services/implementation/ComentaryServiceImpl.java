package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.dtos.CreateComentaryRequest;
import com.taquitosncapas.helpinghands.models.entities.Comentary;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.repositories.ComentaryRepository;
import com.taquitosncapas.helpinghands.services.definition.ComentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ComentaryServiceImpl implements ComentaryService {

	@Autowired
	private ComentaryRepository comentaryRepository;



	@Override
	public Page<Comentary> findAll(Long projectId, Pageable pageable) {
		return comentaryRepository.getAllByProjectId(projectId, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Comentary> findById(Long id) {
		return comentaryRepository.findById(id);
	}

	@Override
	public Optional<Comentary> findByUserIdAndProjectId(Long userId, Long projectId) {
		return comentaryRepository.findComentaryByUserAndProject(userId, projectId);
	}

	@Override
	public void create(CreateComentaryRequest createComentaryRequest, Project project, User user) {
		Comentary comentary = new Comentary();
		comentary.setContent(createComentaryRequest.getContent());
		comentary.setCreate_at(createComentaryRequest.getCreate_at());
		comentary.setProject(project);
		comentary.setUser(user);
		comentaryRepository.save(comentary);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		comentaryRepository.deleteById(id);
	}

}
