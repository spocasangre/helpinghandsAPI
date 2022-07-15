package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.EditApplicationRequest;
import com.taquitosncapas.helpinghands.models.dtos.project.*;
import com.taquitosncapas.helpinghands.models.entities.*;
import com.taquitosncapas.helpinghands.services.definition.*;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TokenManager tokenManager;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ApprovalService approvalService;

	@Autowired
	ApplicationService applicationService;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping("")
	public ResponseEntity<?> create(@RequestHeader (value = "Authorization") String autho,
									@RequestBody CreateProjectRequest createProjectRequest) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Organization");
		}else{
			Optional<Category> category = categoryService.findCategoryById(createProjectRequest.getCategory());
			Category thisCategory = category.get();

			projectService.createProject(createProjectRequest, foundUser, thisCategory);

			return ResponseEntity.status(201).body("Project sent to be approved!");
		}
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/active/{active}")
	public ResponseEntity<?> projectsByActive(@RequestHeader(value = "Authorization") String autho, @PathVariable("active") Integer active, Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		if(active == null){
			return ResponseEntity.status(401).body("Must include a route parameter, It could be 0 (for pending) or 1 (for active)");
		}else if(active < 0 || active > 1){
			return ResponseEntity.status(401).body("The route parameter, must be 0 (for pending) or 1 (for active), pther values are not accepted");
		}

		Page<Project> projectList = projectService.projectPreviewGetAll(active, pageable);
		//se necesita agregar el string de photo al pageable

		return ResponseEntity.ok(new HandlerPage(projectList.getTotalElements(), projectList.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/category/{category}")
	public ResponseEntity<?> previewProjectsByCategoryAndIsActive(@RequestHeader(value = "Authorization") String autho,
																  @PathVariable("category") Long category, Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Page<Project> projectList = projectService.projectsPreviewByCategoryAndIsActiveAndNotFinished(category, pageable);
		return ResponseEntity.ok(new HandlerPage(projectList.getTotalElements(), projectList.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@RequestHeader(value = "Authorization") String autho,
								  @PathVariable(value="id") Long projectId) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if(foundUser.getId_role() > 3){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer OR an Org OR a Manager");
		}

		Optional<Project> project = projectService.findProjectById(projectId);

		if(!project.isPresent()) {
			return ResponseEntity.status(404).body("Project Not Found!!");
		}
		return ResponseEntity.ok(project.get());
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PutMapping("/editproject")
	public ResponseEntity<?> editOneProjectById(@RequestHeader (value = "Authorization") String autho,
												@RequestBody ModifyProjectRequest modifyProjectRequest) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		//System.out.println(foundUser.toString());
		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Manager");
		}

		if(modifyProjectRequest.getProjectId() == null){
			return ResponseEntity.status(404).body("No project id has been provided in the json body");

		}

		Optional<Project> project = projectService.findProjectById(modifyProjectRequest.getProjectId());

		if(!project.isPresent()){
			return ResponseEntity.status(404).body("This project id does not exist");
		}

		if(project.get().getOwner().getId() != foundUser.getId()){
			return ResponseEntity.status(401).body("This projects does not belong to the org that is obtained from token");
		}

		if(modifyProjectRequest.getCategory() != null){
			Optional<Category> category = categoryService.findCategoryById(modifyProjectRequest.getCategory());

			if(!category.isPresent()){
				return ResponseEntity.status(404).body("This category id does not exist");
			}
		}

		projectService.updateProject(modifyProjectRequest, project.get());
		return ResponseEntity.status(OK).body("Project Updated Successfully!!");
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/GetExtraInfo/{projectId}")
	public ResponseEntity<?> GetAdditionalInfo(@RequestHeader(value = "Authorization") String autho,
											   @PathVariable(value="projectId") Long projectId) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (!(foundUser.getId_role() >= 1 && foundUser.getId_role() <= 3)){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer OR an Org OR a Manager");
		}
		Optional<Project> project = projectService.findProjectById(projectId);

		if(!project.isPresent()) {
			return ResponseEntity.status(404).body("Project Not Found!!");
		}

		Optional<Organization> org = userService.findOrganizationById(project.get().getOwner().getId());
		ProjectAdditionalInfoResponse projectAdditionalInfoResponse = new ProjectAdditionalInfoResponse();
		projectAdditionalInfoResponse.setOrgId(org.get().getId());
		projectAdditionalInfoResponse.setOrgName(org.get().getName_org());
		projectAdditionalInfoResponse.setCategoryName(project.get().getCategory().getName());
		return ResponseEntity.ok(projectAdditionalInfoResponse);

	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/GetMyProjects/{active}")
	public ResponseEntity<?> GetMyOrgProjects(@RequestHeader(value = "Authorization") String autho,
											  @PathVariable(value="active") Integer active, Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by an Org");
		}
		if(active == null){
			return ResponseEntity.status(401).body("Must include a route parameter, It could be 0 (for pending) or 1 (for active)");
		}else if(active < 0 || active > 1){
			return ResponseEntity.status(401).body("The route parameter, must be 0 (for pending) or 1 (for active), pther values are not accepted");
		}

		Page<Project> projects = projectService.myOrgProjectsByIsActive(foundUser.getId(), active, pageable);

		if (projects.isEmpty()){
			if(active == 1){
				return ResponseEntity.status(404).body("Projects that are active not Found!!");
			}else{
				return ResponseEntity.status(404).body("Projects that are pending not Found!!");
			}
		}

		return ResponseEntity.ok(new HandlerPage(projects.getTotalElements(), projects.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/GetMyProjects")
	public ResponseEntity<?> GetMyOrgNotFinishedProjects(@RequestHeader(value = "Authorization") String autho,
													  Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by an Org");
		}

		Page<Project> projects = projectService.myOrgProjectsThatAreNotFinished(foundUser.getId(), pageable);

		if (projects.isEmpty()){
			return ResponseEntity.status(404).body("Projects not found");
		}

		return ResponseEntity.ok(new HandlerPage(projects.getTotalElements(), projects.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/GetMyFinishedProjects")
	public ResponseEntity<?> GetMyOrgFinishedProjects(@RequestHeader(value = "Authorization") String autho,
													  Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by an Org");
		}

		Page<Project> projects = projectService.myOrgProjectsThatAreFinished(foundUser.getId(), pageable);

		if (projects.isEmpty()){
			return ResponseEntity.status(404).body("Projects that are finished not found");
		}

		return ResponseEntity.ok(new HandlerPage(projects.getTotalElements(), projects.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/myActivatedProjects")
	public ResponseEntity<?> getAllActivatedProjects(@RequestHeader(value = "Authorization") String autho,
													 Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 3){
			return ResponseEntity.status(401).body("This action can only be performed by a Manager");
		}

		Page<Project> projects = approvalService.getAllProjectsByRespondantId(foundUser.getId(), pageable);
		return ResponseEntity.ok(new HandlerPage(projects.getTotalElements(), projects.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PutMapping("/makeActive")
	public ResponseEntity<?> makeProjectActive(@RequestHeader(value = "Authorization") String autho,
											   @RequestBody EditProjectRequest editProjectRequest) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 3){
			return ResponseEntity.status(401).body("This action can only be performed by a Manager");
		}

		Optional<Project> project = projectService.findProjectById(editProjectRequest.getProjectId());

		if(project==null) {
			return ResponseEntity.status(404).body("Project Not Found!!");
		}

		Optional<Approval> exist = approvalService.findOneApprovalByProjectId(project.get().getId());
		if(exist.isPresent()){
			return ResponseEntity.status(404).body("Project is already active");
		}

/*		if(project.get().getActive() == 1){
			return ResponseEntity.status(404).body("Project is already active");
		}*/

		projectService.makeProjectActive(project.get().getId(), foundUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("This project is now active");
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PutMapping("/makeFinished")
	public ResponseEntity<?> makeProjectFinished(@RequestHeader(value = "Authorization") String autho,
												 @RequestBody EditProjectRequest editProjectRequest) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Manager");
		}

		Optional<Project> project = projectService.findProjectById(editProjectRequest.getProjectId());

		if(project.get().getOwner().getId() != foundUser.getId()){
			return ResponseEntity.status(401).body("This project does not belong to the org that is asking to set it as finished");
		}

		if(project==null) {
			return ResponseEntity.status(404).body("Project Not Found!!");
		}

		projectService.makeProjectFinished(project.get().getId());
		return ResponseEntity.status(HttpStatus.CREATED).body("This project is now marked as finished");
	}

	@PostMapping("/apply")
	public ResponseEntity<?> applyToProjectByProjectId(@RequestHeader (value = "Authorization") String autho,
													   @RequestBody EditProjectRequest editProjectRequest) throws Exception{
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Project> exist = projectService.findProjectById(editProjectRequest.getProjectId());

		if(!exist.isPresent()){
			return ResponseEntity.status(404).body("This project does not exist");
		}

		if(exist.get().getActive() == 0){
			return ResponseEntity.status(404).body("This project has already been set as finished");
		}

		if(exist.get().getIsFinished() == 1){
			return ResponseEntity.status(404).body("This project has already been set as finished, you cannot apply");
		}

		Optional<Application> appliExist = applicationService.findOneByProjectIdAndUserId(exist.get().getId(), foundUser.getId());

		if(appliExist.isPresent()){
			return ResponseEntity.status(401).body("You have already applied to this project");
		}

		applicationService.apply(exist.get(), foundUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("You have applied to this project");
	}

	@GetMapping("/myapplied/")
	public ResponseEntity<?> getAllMyAppliedProjects(@RequestHeader (value = "Authorization") String autho, Pageable pageable) throws Exception{
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Page<Application> myProjects = applicationService.getAllByUserId(foundUser.getId(), pageable);
		return ResponseEntity.status(OK).body(new HandlerPage(myProjects.getTotalElements(), myProjects.toList()));
	}

	@GetMapping("/myappliedabandoned/")
	public ResponseEntity<?> getAllMyAppliedAbandonedProjects(@RequestHeader (value = "Authorization") String autho, Pageable pageable) throws Exception{
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Page<Application> myProjects = applicationService.getAllAbandonedByUserId(foundUser.getId(), pageable);
		return ResponseEntity.status(OK).body(new HandlerPage(myProjects.getTotalElements(), myProjects.toList()));
	}

	@GetMapping("/appliedinfo/{id}")
	public ResponseEntity<?> getProjectInfoFromAppliedId(@RequestHeader (value = "Authorization") String autho,
														 @PathVariable (value = "id") Long appId) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Application> app = applicationService.findOneById(appId);

		if (!app.isPresent()){
			return ResponseEntity.status(404).body("This application id does not exist");
		}

		return ResponseEntity.status(OK).body(app.get().getProject());
	}

	@PutMapping("/respondapplication")
	public ResponseEntity<?> respondApplicationByid(@RequestHeader (value = "Authorization") String autho ,
													@RequestBody EditApplicationRequest editApplicationRequest) throws Exception{
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Org");
		}
		Optional<Application> exist = applicationService.findOneById(editApplicationRequest.getAppId());

		if(!exist.isPresent()){
			return ResponseEntity.status(404).body("Application not found");
		}

		if(exist.get().getProject().getOwner().getId() != foundUser.getId()){
			return ResponseEntity.status(401).body("This application is not linked to any of your Org projects");
		}

		if(exist.get().getResponse() == 1){
			return ResponseEntity.status(401).body("This application has already been approved");
		}

		applicationService.respondByAppId(editApplicationRequest.getAppId());

		return ResponseEntity.status(OK).body("Application has been approved");
	}

	@PutMapping("/makeapplicationabandoned")
	public ResponseEntity<?> makeAppAbandoned(@RequestHeader (value = "Authorization") String autho,
											  @RequestBody EditApplicationRequest editApplicationRequest) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Application> exist = applicationService.findOneById(editApplicationRequest.getAppId());

		if(!exist.isPresent()){
			return ResponseEntity.status(404).body("This application id does not exist");
		}

		if(exist.get().getUser().getId() != foundUser.getId()) {
			return ResponseEntity.status(404).body("This application does not belong to this volunteer");
		}

		applicationService.abandoneByAppId(exist.get().getId());
		return ResponseEntity.status(OK).body("This application has now been set as abandoned");
	}

	@GetMapping("/notresponded")
	public ResponseEntity<?> getAllbyOrg(@RequestHeader (value = "Authorization") String autho, Pageable pageable) throws Exception{

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Organization");
		}

		Page<Application> orgProjects = applicationService.getAllbyOrgId(foundUser.getId(), pageable);
		return ResponseEntity.status(OK).body(new HandlerPage(orgProjects.getTotalElements(), orgProjects.toList()));
	}

/*	@GetMapping("/users/{id}")
	public ResponseEntity<?> getAllbyOrg(@RequestHeader (value = "Authorization") String autho, @PathVariable (value = "id") Long appId),Pageable pageable) throws Exception{

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Organization");
		}

		Page<Application> orgProjects = applicationService.getAllbyOrgId(foundUser.getId(), pageable);
		return ResponseEntity.status(OK).body(orgProjects.toList());
	}*/



/*	@PostMapping("/apply")
	public ResponseEntity<?> getProjectByorgId(@RequestHeader (value = "Authorization") String autho,
													   @RequestBody EditProjectRequest editProjectRequest) throws Exception{
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Project> exist = projectService.findProjectById(editProjectRequest.getProjectId());



		if(!exist.isPresent()){
			return ResponseEntity.status(404).body("This project does not exist");
		}

		applicationService.apply(exist.get(), foundUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("You have applied to this project");
	}*/

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value="id") Long projectId) {
		Optional<Project> project = projectService.findProjectById(projectId);

		if(!project.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		projectService.deleteProjectById(projectId);
		return ResponseEntity.status(OK).body("Project Deleted Successfully!!");
	}
}
