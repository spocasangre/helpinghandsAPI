package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.dtos.project.CreateProjectRequest;
import com.taquitosncapas.helpinghands.models.dtos.project.ModifyProjectRequest;
import com.taquitosncapas.helpinghands.models.entities.Category;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProjectService {

	public Iterable<Project> findAllProjects();
	public Page<Project> findAllProjects(Pageable pageable);
	public Optional<Project> findProjectById(Long id);
	public void deleteProjectById(Long id);
	public void createProject(CreateProjectRequest createProjectRequest, User ownerUser, Category category);
	public void makeProjectActive(Long projectId, User respondant);
	void updateProject(ModifyProjectRequest modifyProjectRequest, Project project);
	void makeProjectFinished(Long projectId);
	public Page<Project> projectPreviewGetAll(Integer active, Pageable pageable);
	public Page<Project> projectsPreviewByCategoryAndIsActiveAndNotFinished(Long category, Pageable pageable);
	Page<Project> myOrgProjectsByIsActive(Long orgId, Integer active, Pageable pageable);
	Page<Project> myOrgProjectsThatAreFinished(Long orgId, Pageable pageable);
	Page<Project> myOrgProjectsThatAreNotFinished(Long orgId, Pageable pageable);
}
