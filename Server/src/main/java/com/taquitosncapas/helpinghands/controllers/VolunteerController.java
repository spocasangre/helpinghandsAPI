package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.auth.RefreshTokenRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditVolunteerRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetVolunteerProfileResponse;
import com.taquitosncapas.helpinghands.models.dtos.project.ModifyProjectRequest;
import com.taquitosncapas.helpinghands.models.entities.Category;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.models.entities.Volunteer;

import com.taquitosncapas.helpinghands.services.definition.UserService;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    @Autowired
    UserService userService;

    @Autowired
    TokenManager tokenManager;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/get-my-profile")
    public ResponseEntity<GetVolunteerProfileResponse> getMyVolunteerProfile(@RequestHeader(value = "Authorization") String autho) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User foundUser = userService.findOneByEmail(email);

        if(foundUser.getId_role()!=1){
            return new ResponseEntity<>(new GetVolunteerProfileResponse(null),HttpStatus.UNAUTHORIZED);
        }

        Optional<Volunteer> volunteer = userService.findVolunteerById(Long.valueOf(foundUser.getId()));

        if(!volunteer.isPresent()) {
            return new ResponseEntity<>(new GetVolunteerProfileResponse( null),HttpStatus.NOT_FOUND);
        }
        volunteer.get().setPass("null");
        return new ResponseEntity<>(new GetVolunteerProfileResponse(volunteer),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PutMapping("/editprofile")
    public ResponseEntity<?> editOneVolunteerProfileById(@RequestHeader (value = "Authorization") String autho,
                                                         @RequestBody EditVolunteerRequest editVolunteerRequest) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User foundUser = userService.findOneByEmail(email);

        //System.out.println(foundUser.toString());
        if (foundUser.getId_role() != 1){
            return ResponseEntity.status(401).body("This action can only be performed by a Volunteer");
        }

        Optional<Volunteer> exist = userService.findVolunteerById(foundUser.getId());

        userService.updateVolunteer(editVolunteerRequest, foundUser);
        return ResponseEntity.status(OK).body("Volunteer profile Updated Successfully!!");
    }
}
