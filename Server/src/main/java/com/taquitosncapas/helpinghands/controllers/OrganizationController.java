package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.auth.RefreshTokenRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditOrgRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditVolunteerRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetOrganizationProfileResponse;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetVolunteerProfileResponse;
import com.taquitosncapas.helpinghands.models.entities.Organization;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.models.entities.Volunteer;
import com.taquitosncapas.helpinghands.services.definition.UserService;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/get-my-profile")
    public ResponseEntity<GetOrganizationProfileResponse> getMyOrganizationProfile(@RequestHeader(value = "Authorization") String autho) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);

        User foundUser = userService.findOneByEmail(email);

        if(foundUser.getId_role()!=2){
            return new ResponseEntity<>(new GetOrganizationProfileResponse("you cannot do this action",null),HttpStatus.UNAUTHORIZED);
        }

        Optional<Organization> organization = userService.findOrganizationById(Long.valueOf(foundUser.getId()));

        if(!organization.isPresent()) {
            return new ResponseEntity<>(new GetOrganizationProfileResponse("User not found", null),HttpStatus.NOT_FOUND);
        }
        organization.get().setPass("null");
        return new ResponseEntity<>(new GetOrganizationProfileResponse("User found",organization),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getvolunteerprofile/{id}")
    public ResponseEntity<?> getMyVolunteerProfile(@RequestHeader(value = "Authorization") String autho,
                                                                             @PathVariable (value = "id") Long volunteerId) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User foundUser = userService.findOneByEmail(email);

        if(foundUser.getId_role()!=2){
            return ResponseEntity.status(401).body("This action can only be performed by a Org");
        }

        Optional<Volunteer> volunteer = userService.findVolunteerById(volunteerId);

        if(!volunteer.isPresent()) {
            return new ResponseEntity<>(new GetVolunteerProfileResponse( null),HttpStatus.NOT_FOUND);
        }
        volunteer.get().setPass("null");
        return new ResponseEntity<>(new GetVolunteerProfileResponse(volunteer),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PutMapping("/editprofile")
    public ResponseEntity<?> editOneOrgProfileById(@RequestHeader (value = "Authorization") String autho,
                                                   @RequestBody EditOrgRequest editOrgRequest) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User foundUser = userService.findOneByEmail(email);

        //System.out.println(foundUser.toString());
        if (foundUser.getId_role() != 2){
            return ResponseEntity.status(401).body("This action can only be performed by a Org");
        }

        Optional<Organization> exist = userService.findOrganizationById(foundUser.getId());

        userService.updateOrg(editOrgRequest, foundUser);
        return ResponseEntity.status(OK).body("Org profile Updated Successfully!!");
    }
}
