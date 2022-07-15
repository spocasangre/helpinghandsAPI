package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.CreateFavoriteRequest;
import com.taquitosncapas.helpinghands.models.dtos.project.HandlerPage;
import com.taquitosncapas.helpinghands.models.entities.Favorite;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.services.definition.AuthService;
import com.taquitosncapas.helpinghands.services.definition.FavoriteService;
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
@RequestMapping("/favorites")
public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String autho,
									@RequestBody CreateFavoriteRequest createFavoriteRequest) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		Optional<Project> project = projectService.findProjectById(createFavoriteRequest.getProjectId());

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		if(project==null){
			return ResponseEntity.status(404).body("Project not found");
		}

		Optional<Favorite> existente = favoriteService.findByUserAndProjectId(foundUser.getId(), project.get().getId());

		if(existente.isPresent()){
			return ResponseEntity.status(401).body("This project has already been added as favorite");
		}

		favoriteService.create(foundUser, project.get());
		return ResponseEntity.status(HttpStatus.CREATED).body("Project added to favorites");
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/")
	public ResponseEntity<?> read(@RequestHeader(value = "Authorization") String autho, Pageable pageable) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);


		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		System.out.println(foundUser.getId());
		Page<Favorite> favorites = favoriteService.findAllByUserId(foundUser.getId(), pageable);

		return ResponseEntity.ok(new HandlerPage(favorites.getTotalElements(), favorites.toList()));
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/project/{id}")
	public ResponseEntity<?> getProjectbyFavorite(@RequestHeader(value = "Authorization") String autho,@PathVariable(value="id") Long favoriteId) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Project> favorites = favoriteService.getProjectByFavoriteId(favoriteId);

		return ResponseEntity.ok(favorites);
	}
	/*@PutMapping("/{id}")
		public ResponseEntity<?> update(@RequestBody Favorite favoriteDetails, @PathVariable(value="id") Long favoriteId ) {
		Optional<Favorite> favorite = favoriteService.findById(favoriteId);

		if(!favorite.isPresent()) {
			return ResponseEntity.status(404).body("Favorite Not Found!!");
		}

		favorite.get();

		return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.save(favorite.get()));
	  }*/

	@CrossOrigin(origins = "*", maxAge = 3600)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String autho,
									@PathVariable(value="id") Long favoriteId) throws Exception {

		Optional<Favorite> favorite = favoriteService.findById(favoriteId);

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);


		if (foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		if(!favorite.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		if(favorite.get().getUser().getId() != foundUser.getId()){
			return ResponseEntity.status(401).body("This favorite is not owned by this Volunteer");
		}

		favoriteService.deleteById(favoriteId);
		return ResponseEntity.status(OK).body("Favorite Deleted Successfully!!");
	}
}
