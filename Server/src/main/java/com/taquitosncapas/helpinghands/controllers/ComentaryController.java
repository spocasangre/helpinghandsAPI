package com.taquitosncapas.helpinghands.controllers;


import com.taquitosncapas.helpinghands.models.dtos.ComentaryInfoResponse;
import com.taquitosncapas.helpinghands.models.dtos.CreateComentaryRequest;
import com.taquitosncapas.helpinghands.models.dtos.project.HandlerPage;
import com.taquitosncapas.helpinghands.models.entities.Comentary;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.services.definition.ComentaryService;
import com.taquitosncapas.helpinghands.services.definition.ProjectService;
import com.taquitosncapas.helpinghands.services.definition.UserService;
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
@RequestMapping("/comentaries")
public class ComentaryController {

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ComentaryService comentaryService;

	@Autowired
	private ProjectService projectService;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping("/create")
	  public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String autho,
									  @RequestBody CreateComentaryRequest createComentaryRequest) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Project> project = projectService.findProjectById(createComentaryRequest.getId_project());

		if(!project.isPresent()) {
			return ResponseEntity.status(404).body("Project Not Found!!");
		}
		comentaryService.create(createComentaryRequest, project.get(), foundUser);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Comentary created succesfully");
	  }

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/{projectId}")
	public ResponseEntity<?> readAll(@RequestHeader(value = "Authorization") String autho,
									 @PathVariable (value = "projectId") Long projectId, Pageable pageable) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Page<Comentary> comentaries = comentaryService.findAll(projectId, pageable);

		return ResponseEntity.ok(new HandlerPage(comentaries.getTotalElements(), comentaries.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/info/{id}")
	public ResponseEntity<?> readAll(@RequestHeader(value = "Authorization") String autho,
									 @PathVariable (value = "id") Long comentaryId) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Comentary> comentaries = comentaryService.findById(comentaryId);
		if(!comentaries.isPresent()) {
			return ResponseEntity.status(404).body("Comentary Not Found!!");
		}
		ComentaryInfoResponse comentaryInfoResponse=new ComentaryInfoResponse();
		comentaryInfoResponse.setContent(comentaries.get().getContent());
		comentaryInfoResponse.setAuthor(comentaries.get().getUser().getName()+" "+comentaries.get().getUser().getLastname());
		comentaryInfoResponse.setCreate_at(comentaries.get().getCreate_at());
		return ResponseEntity.ok(comentaryInfoResponse);
	}
	
	/*@GetMapping("/{id}")
	  public ResponseEntity<?> read(@PathVariable(value="id") Long comentaryId) {
		Optional<Comentary> comentary = comentaryService.findById(comentaryId);
		
		if(!comentary.isPresent()) {
			return ResponseEntity.status(404).body("Comentary Not Found!!");
		}
		
		return ResponseEntity.ok(comentary);
	  }*/
	
	/*@PutMapping("/{id}")
		public ResponseEntity<?> update(@RequestBody Comentary comentaryDetails, @PathVariable(value="id") Long comentaryId ) {
		Optional<Comentary> comentary = comentaryService.findById(comentaryId);
		
		if(!comentary.isPresent()) {
			return ResponseEntity.status(404).body("Comentary Not Found!!");
		}

		comentary.get().setContent(comentaryDetails.getContent());
		comentary.get().setDate(comentaryDetails.getDate());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(comentaryService.save(comentary.get()));
	  }*/

	@CrossOrigin(origins = "*", maxAge = 3600)
	@DeleteMapping("/{id}")
	  public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String autho,
									  @PathVariable(value="id") Long comentaryId) throws Exception {

		Optional<Comentary> comentary = comentaryService.findById(comentaryId);

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		//System.out.println(foundUser.toString());
		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		if(foundUser.getId() != comentary.get().getUser().getId()){
			return ResponseEntity.status(401).body("Cannot delete a comment that isnt yours");
		}

		if(!comentary.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		comentaryService.deleteById(comentaryId);
		return ResponseEntity.status(OK).body("Comentary Deleted Successfully!!");
	  }
	

}
