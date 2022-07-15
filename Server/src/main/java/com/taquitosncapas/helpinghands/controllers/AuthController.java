package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.auth.*;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetVolunteerProfileResponse;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.models.entities.Volunteer;
import com.taquitosncapas.helpinghands.services.definition.AuthService;
import com.taquitosncapas.helpinghands.services.definition.UserService;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenManager tokenManager;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/signup-volunteer")
    public ResponseEntity<MessageRegisterResponse> signup_volunteer(@Valid @RequestBody VolunteerRegisterRequest volunteer, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("There are conflicts"),
                        HttpStatus.BAD_REQUEST
                );
            }

            User foundUser = userService.findOneByEmail(volunteer.getEmail());

            if (foundUser != null) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("User already exists"),
                        HttpStatus.BAD_REQUEST
                );
            }

            authService.registerVolunteer(volunteer);
            return new ResponseEntity<>(new MessageRegisterResponse("Registration Successfully"), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new MessageRegisterResponse("Internal Error"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/signup-organization")
    public ResponseEntity<MessageRegisterResponse> signup_organization(@Valid @RequestBody OrganizationRegisterRequest organization, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("There are conflicts"),
                        HttpStatus.BAD_REQUEST
                );
            }

            User foundUser = userService.findOneByEmail(organization.getEmail());

            if (foundUser != null) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("User already exists"),
                        HttpStatus.BAD_REQUEST
                );
            }

            authService.registerOrganization(organization);
            return new ResponseEntity<>(new MessageRegisterResponse("Registration Successfully"), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new MessageRegisterResponse("Internal Error"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/signup-manager")
    public ResponseEntity<MessageRegisterResponse> signup_manager(@Valid @RequestBody ManagerRegisterRequest manager,
                                                                  @RequestHeader (value = "Authorization") String autho,
                                                                  BindingResult result){
        try{
            if(result.hasErrors()) {
                String errors = result.getAllErrors().toString();

                return new ResponseEntity<>(
                        new MessageRegisterResponse("There are conflicts: " + errors),
                        HttpStatus.BAD_REQUEST
                );
            }

            String token = autho.split(" ")[1];
            String email = tokenManager.getEmailFromToken(token);
            User masterUser = userService.findOneByEmail(email);

            if (masterUser.getId_role() != 4) {
                return new ResponseEntity<>(new MessageRegisterResponse("This action can only be performed by a Master"), HttpStatus.UNAUTHORIZED);
            }

            User foundUser = userService.findOneByEmail(manager.getEmail());

            if(foundUser != null) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("Manager already exists"),
                        HttpStatus.BAD_REQUEST
                );
            }

            authService.registerManager(manager);

            return new ResponseEntity<>(new MessageRegisterResponse("Master Registration Successfully"), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(
                    new MessageRegisterResponse("Internal Error"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/signup-master")
    public ResponseEntity<MessageRegisterResponse> signup_master(@Valid @RequestBody MasterRegisterRequest master, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("There are conflicts"),
                        HttpStatus.BAD_REQUEST
                );
            }

            User foundUser = userService.findOneByEmail(master.getEmail());

            if (foundUser != null) {
                return new ResponseEntity<>(
                        new MessageRegisterResponse("User already exists"),
                        HttpStatus.BAD_REQUEST
                );
            }

            authService.registerMaster(master);
            return new ResponseEntity<>(new MessageRegisterResponse("Registration Successfully"), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new MessageRegisterResponse("Internal Error"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/signin")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest loginRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                String errors = result.getAllErrors().toString();
                return new ResponseEntity<>(
                        new UserLoginResponse("There are conflicts: ", errors),
                        HttpStatus.BAD_REQUEST
                );
            }

            User foundUser = userService.findOneByEmail(loginRequest.getEmail());

            if (foundUser == null) {
                return new ResponseEntity<>(
                        new UserLoginResponse("User not found", null),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (!authService.comparePassword(foundUser, loginRequest.getPassword())) {
                System.out.println(foundUser);
                return new ResponseEntity<>(
                        new UserLoginResponse("Password is not correct", null),
                        HttpStatus.UNAUTHORIZED
                );
            }

            final String token = tokenManager.generateJwtToken(foundUser);

            authService.insertToken(foundUser, token);

            return new ResponseEntity<>(
                    new UserLoginResponse("User Logged", token),
                    HttpStatus.CREATED
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new UserLoginResponse("Internal Error", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/refreshtoken")
    public ResponseEntity<RefreshTokenResponse> refresh_token(@RequestHeader(value = "Authorization") String autho) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);

        User foundUser = userService.findOneByEmail(email);

        if (foundUser != null) {
            return new ResponseEntity<>(
                    new RefreshTokenResponse(foundUser.getId_role().toString()),
                    HttpStatus.OK
            );
        }

        String role = foundUser.getId_role().toString();
        return new ResponseEntity<>(
                new RefreshTokenResponse(role),
                HttpStatus.OK
        );
    }
}



