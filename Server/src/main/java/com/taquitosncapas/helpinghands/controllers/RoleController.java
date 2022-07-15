package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.entities.Role;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.services.definition.RoleService;
import com.taquitosncapas.helpinghands.services.definition.UserService;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenManager tokenManager;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Role role, @RequestHeader(value = "Authorization", required = true) String autho) throws Exception {

		String token[] = autho.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}else {
			if (role.getName() == user.getName()){
				return ResponseEntity.status(404).body("Role already exists");
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(role));
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable(value="id") Long roleId) {
		Optional<Role> role = roleService.findRoleById(roleId);

		if(!role.isPresent()) {
			return ResponseEntity.status(404).body("Role Not Found!!");
		}

		return ResponseEntity.ok(role);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Role roleDetails,@RequestHeader(value = "Authorization", required = true) String autho, @PathVariable(value="id") Long roleId ) throws Exception {

		String token[] = autho.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}else {
			Optional<Role> role = roleService.findRoleById(roleId);
			if(role.isPresent()) {
				return ResponseEntity.status(404).body("Role Not Found!!");
			}
			role.get().setName(roleDetails.getName());
			role.get().setContent(roleDetails.getContent());
			return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(role.get()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@RequestHeader(value = "Authorization", required = true) String header,@PathVariable(value="id") Long roleId) throws Exception {

		String token[] = header.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}else {
			Optional<Role> role = roleService.findRoleById(roleId);
			if(role.isPresent()) {
				return ResponseEntity.status(404).body("Role Not Found!!");
			}
			roleService.deleteRoleById(roleId);
			return ResponseEntity.status(OK).body("Role Deleted Successfully!!");
		}

	}

	@GetMapping("/")
	public ResponseEntity<?> readAll(@RequestHeader(value = "Authorization", required = true) String autho) throws Exception {

		String token[] = autho.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user == null){
			return ResponseEntity.status(401).body("This action can only be performed by a registered user");
		}

		List<Role> roles = StreamSupport
				.stream(roleService.findAllRoles().spliterator(),false)
				.collect(Collectors.toList());
		if(roles.isEmpty()) {
			return ResponseEntity.status(404).body("Role Not Found!!");
		}
		return ResponseEntity.ok(roles);
	}
}
