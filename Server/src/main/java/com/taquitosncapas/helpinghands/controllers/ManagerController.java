package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.auth.RefreshTokenRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditManagerRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditOrgRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetManagerProfileResponse;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetOrganizationProfileResponse;
import com.taquitosncapas.helpinghands.models.entities.Manager;
import com.taquitosncapas.helpinghands.models.entities.Organization;
import com.taquitosncapas.helpinghands.models.entities.User;
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
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/get-my-profile")
    public ResponseEntity<GetManagerProfileResponse> getMyManagerProfile(@RequestHeader(value = "Authorization") String autho) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);

        User foundUser = userService.findOneByEmail(email);

        if(foundUser.getId_role()!=3){
            return new ResponseEntity<>(new GetManagerProfileResponse("you cannot do this action",null),HttpStatus.UNAUTHORIZED);
        }

        Optional<Manager> manager = userService.findManagerById(Long.valueOf(foundUser.getId()));

        if(!manager.isPresent()) {
            return new ResponseEntity<>(new GetManagerProfileResponse("User not found", null),HttpStatus.NOT_FOUND);
        }
        manager.get().setPass("null");
        return new ResponseEntity<>(new GetManagerProfileResponse("User found",manager),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/getorgprofile/{id}")
    public ResponseEntity<?> getOrgProfileById(@RequestHeader(value = "Authorization") String autho,
                                               @PathVariable (value = "id") Long orgId) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User foundUser = userService.findOneByEmail(email);

        if(foundUser.getId_role()!=3){
            return ResponseEntity.status(401).body("This action can only be performed by a Manager");
        }

        Optional<Organization> organization = userService.findOrganizationById(orgId);

        if(!organization.isPresent()) {
            return new ResponseEntity<>(new GetOrganizationProfileResponse( "Org not found",null),HttpStatus.NOT_FOUND);
        }
        organization.get().setPass("null");
        return new ResponseEntity<>(new GetOrganizationProfileResponse("Org found", organization),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PutMapping("/editprofile")
    public ResponseEntity<?> editOneManagerProfileById(@RequestHeader (value = "Authorization") String autho,
                                                       @RequestBody EditManagerRequest editManagerRequest) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User foundUser = userService.findOneByEmail(email);

        //System.out.println(foundUser.toString());
        if (foundUser.getId_role() != 3){
            return ResponseEntity.status(401).body("This action can only be performed by a Manager");
        }

        Optional<Manager> exist = userService.findManagerById(foundUser.getId());

        userService.updateManager(editManagerRequest, foundUser);
        return ResponseEntity.status(OK).body("Manager profile Updated Successfully!!");
    }
}
