package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.CreateDonationRequest;
import com.taquitosncapas.helpinghands.models.dtos.InfoDonationResponse;
import com.taquitosncapas.helpinghands.models.dtos.TotalDonationResponse;
import com.taquitosncapas.helpinghands.models.dtos.project.HandlerPage;
import com.taquitosncapas.helpinghands.models.dtos.project.ProjectAdditionalInfoResponse;
import com.taquitosncapas.helpinghands.models.entities.Donation;
import com.taquitosncapas.helpinghands.models.entities.Organization;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.services.definition.DonationService;
import com.taquitosncapas.helpinghands.services.definition.ProjectService;
import com.taquitosncapas.helpinghands.services.definition.UserService;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/donations")
public class DonationController {
	@Autowired
	private DonationService donationService;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@PostMapping("/create")
	  public ResponseEntity<?> create(@RequestHeader(value = "Authorization") String autho,
									  @RequestBody CreateDonationRequest createDonationRequest) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if(foundUser.getId_role() != 1){
			return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
		}

		Optional<Project> foundProject = projectService.findProjectById(createDonationRequest.getProjectId());

		if(foundProject==null){
			return ResponseEntity.status(404).body("Project not found");
		}
		donationService.create(createDonationRequest, foundProject.get(), foundUser);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Donation registered succesfully");
	  }

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/")
	  public ResponseEntity<?> getDonationsByOrgId(@RequestHeader(value = "Authorization") String autho,
												   Pageable pageable) throws Exception {

		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);

		if(foundUser.getId_role() != 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Org");
		}

		Page<Donation> donations = donationService.findAllByOrgId(foundUser.getId(), pageable);

		return ResponseEntity.ok(new HandlerPage(donations.getTotalElements(), donations.toList()));
	  }
	
	/*@PutMapping("/{id}")
		public ResponseEntity<?> update(@RequestBody Donation donationDetails, @PathVariable(value="id") Long donationId ) {
		Optional<Donation> donation = donationService.findById(donationId);
		
		if(!donation.isPresent()) {
			return ResponseEntity.status(404).body("Donation Not Found!!");
		}

		donation.get().setValue(donationDetails.getValue());
		donation.get().setDate(donationDetails.getDate());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(donationService.save(donation.get()));
	  }*/

	@CrossOrigin(origins = "*", maxAge = 3600)
	@DeleteMapping("/{id}")
	  public ResponseEntity<?> delete(@PathVariable(value="id") Long categoryId) {
		Optional<Donation> category = donationService.findById(categoryId);
		
		if(!category.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		donationService.deleteById(categoryId);
		return ResponseEntity.status(OK).body("Donation Deleted Successfully!!");
	  }

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping
	  public List<Donation> readAll() {
		
		List<Donation> donations = StreamSupport
				.stream(donationService.findAll().spliterator(),false)
				.collect(Collectors.toList());

		return donations;
	  }

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/info/{id}")
	public ResponseEntity<?> getInfoDonationsById(@RequestHeader(value = "Authorization") String autho,
												  @PathVariable(value="id") Long donationId) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role()!= 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Organization");
		}
		Optional<Donation> donation  = donationService.findById(donationId);

		if(!donation.isPresent()) {
			return ResponseEntity.status(404).body("Donation Not Found!!");
		}

		InfoDonationResponse infoDonationResponse = new InfoDonationResponse();

		infoDonationResponse.setCreateAt(donation.get().getCreateAt());
		infoDonationResponse.setDonator(donation.get().getUser().getName()+" "+donation.get().getUser().getLastname());
		infoDonationResponse.setProject_name(donation.get().getProject().getTitle());
		infoDonationResponse.setValue(donation.get().getValue());

		return ResponseEntity.ok(infoDonationResponse);
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@GetMapping("/total/")
	public ResponseEntity<?> getTotalDonationsByOrgId(@RequestHeader(value = "Authorization") String autho) throws Exception {
		String token = autho.split(" ")[1];
		String email = tokenManager.getEmailFromToken(token);
		User foundUser = userService.findOneByEmail(email);
		if (foundUser.getId_role()!= 2){
			return ResponseEntity.status(401).body("This action can only be performed by a Organization");
		}

		Integer total  = donationService.getTotalDonationsByOrgId(foundUser.getId());

		Integer total_value = donationService.getTotalDonations();

		TotalDonationResponse totalDonationResponse = new TotalDonationResponse();

		totalDonationResponse.setTotal(total);
		totalDonationResponse.setTotal_value(total_value);


		return ResponseEntity.ok(totalDonationResponse);
	}
}
