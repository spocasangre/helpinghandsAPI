package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.entities.Category;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.services.definition.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenManager tokenManager;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestHeader (value = "Authorization") String autho,
									@RequestBody Category createCategoryRequest) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}
		categoryService.saveCategory(createCategoryRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Category created succesfully");
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable(value="id") Long categoryId) {
		Optional<Category> category = categoryService.findCategoryById(categoryId);

		if(!category.isPresent()) {
			return ResponseEntity.status(404).body("Category Not Found!!");
		}

		return ResponseEntity.ok(category);
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Category categoryDetails,@RequestHeader(value = "Authorization", required = true) String autho, @PathVariable(value="id") Long categoryId ) throws Exception {

		String token[] = autho.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}else {
			Optional<Category> category = categoryService.findCategoryById(categoryId);
			if(category.isPresent()) {
				return ResponseEntity.status(404).body("Category Not Found!!");
			}
			category.get().setName(categoryDetails.getName());
			category.get().setDescription(categoryDetails.getDescription());
			return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(category.get()));
		}
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@RequestHeader(value = "Authorization", required = true) String header,@PathVariable(value="id") Long categoryId) throws Exception {

		String token[] = header.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}

		if (user.getId_role() != 4){
			return ResponseEntity.status(401).body("This action can only be performed by a Master");
		}else {
			Optional<Category> category = categoryService.findCategoryById(categoryId);
			if(category.isPresent()) {
				return ResponseEntity.status(404).body("Category Not Found!!");
			}
			categoryService.deleteCategoryById(categoryId);
			return ResponseEntity.status(OK).body("Category Deleted Successfully!!");
		}

	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/")
	public ResponseEntity<?> readAll(@RequestHeader(value = "Authorization", required = true) String autho) throws Exception {

		String token[] = autho.split("\\s+");

		String email = tokenManager.getEmailFromToken(token[1]);

		User user = userService.findOneByEmail(email);

		if (user == null){
			return ResponseEntity.status(401).body("This action can only be performed by a registered user");
		}

		List<Category> categories = StreamSupport
				.stream(categoryService.findAllCategories().spliterator(),false)
				.collect(Collectors.toList());
		if(categories.isEmpty()) {
			return ResponseEntity.status(404).body("Category Not Found!!");
		}
		return ResponseEntity.ok(categories);
	}
}
