package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.dtos.project.CreateProjectRequest;
import com.taquitosncapas.helpinghands.models.dtos.project.ModifyProjectRequest;
import com.taquitosncapas.helpinghands.models.entities.Approval;
import com.taquitosncapas.helpinghands.models.entities.Category;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.repositories.ApprovalRepository;
import com.taquitosncapas.helpinghands.repositories.ProjectRepository;
import com.taquitosncapas.helpinghands.services.definition.ApprovalService;
import com.taquitosncapas.helpinghands.services.definition.CategoryService;
import com.taquitosncapas.helpinghands.services.definition.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ApprovalRepository approvalRepository;

	@Autowired
	private CategoryService categoryService;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Project> findAllProjects(Pageable pageable) {
		return projectRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Project> findProjectById(Long id) {
		return projectRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteProjectById(Long id) {
		projectRepository.deleteById(id);
	}

	@Override
	public void createProject(CreateProjectRequest projectRequest, User ownerUser, Category category) {

		Project project = new Project(projectRequest,ownerUser,category);

		projectRepository.save(project);
	}

	@Override
	public void makeProjectActive(Long projectId, User respondant) {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());

		Optional<Project> myProject = findProjectById(projectId);
		Approval approvalOfProject = new Approval();

		myProject.get().setActive(1);
		myProject.get().setIsPending(0);
		myProject.get().setResponse_date(date);
		projectRepository.save(myProject.get());
		approvalOfProject.setProject(myProject.get());
		approvalOfProject.setUser(respondant);
		approvalRepository.save(approvalOfProject);
	}

	@Override
	public void updateProject(ModifyProjectRequest modifyProjectRequest, Project project) {
		Optional<Project> myProject = findProjectById(project.getId());

		if(modifyProjectRequest.getTitle() != null){
			myProject.get().setTitle(modifyProjectRequest.getTitle());
		}
		if(modifyProjectRequest.getDescription() != null){
			myProject.get().setDescription(modifyProjectRequest.getDescription());
		}
		if(modifyProjectRequest.getPlace() != null){
			myProject.get().setPlace(modifyProjectRequest.getPlace());
		}
		if(modifyProjectRequest.getDate() != null){
			myProject.get().setDate(modifyProjectRequest.getDate());
		}
		if(modifyProjectRequest.getDuration() != null){
			myProject.get().setDuration(modifyProjectRequest.getDuration());
		}
		if(modifyProjectRequest.getCategory() != null){
			Optional<Category> myCategory = categoryService.findCategoryById(modifyProjectRequest.getCategory());
			myProject.get().setCategory(myCategory.get());
		}
		projectRepository.save(myProject.get());
	}

	@Override
	public void makeProjectFinished(Long projectId) {
		Optional<Project> myProject = findProjectById(projectId);
		myProject.get().setIsFinished(1);
		projectRepository.save(myProject.get());
	}

	@Override
	public Page<Project> projectPreviewGetAll(Integer active, Pageable pageable) {
		return projectRepository.projectPreviewGetAll(active, pageable);
	}

	@Override
	public Page<Project> projectsPreviewByCategoryAndIsActiveAndNotFinished(Long category, Pageable pageable) {
		return projectRepository.projectPreviewByCategory(category, pageable);
	}

	@Override
	public Page<Project> myOrgProjectsByIsActive(Long orgId, Integer active, Pageable pageable) {
		return projectRepository.myOrgProjectsByActive(orgId, active, pageable);
	}

	@Override
	public Page<Project> myOrgProjectsThatAreFinished(Long orgId, Pageable pageable) {
		return projectRepository.myOrgProjectThatAreFinished(orgId, pageable);
	}

	@Override
	public Page<Project> myOrgProjectsThatAreNotFinished(Long orgId, Pageable pageable) {
		return projectRepository.myOrgProjectsNotFinished(orgId, pageable);
	}

}
