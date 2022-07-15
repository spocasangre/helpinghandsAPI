package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.dtos.CreateComentaryRequest;
import com.taquitosncapas.helpinghands.models.entities.Comentary;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ComentaryService {
	public Page<Comentary> findAll(Long projectId, Pageable pageable);
	public Optional<Comentary> findById(Long id);
	public Optional<Comentary> findByUserIdAndProjectId(Long userId, Long projectId);
	public void create(CreateComentaryRequest createComentaryRequest, Project project, User user);
	public void deleteById(Long id);
}
