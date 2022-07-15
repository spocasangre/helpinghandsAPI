package com.taquitosncapas.helpinghands.controllers;

import com.taquitosncapas.helpinghands.models.dtos.auth.*;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetManagersInfoResponse;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetMasterProfileResponse;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetOrganizationProfileResponse;
import com.taquitosncapas.helpinghands.models.dtos.profile.GetVolunteerProfileResponse;
import com.taquitosncapas.helpinghands.models.dtos.project.HandlerPage;
import com.taquitosncapas.helpinghands.models.entities.Master;
import com.taquitosncapas.helpinghands.models.entities.Organization;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.models.entities.Volunteer;
import com.taquitosncapas.helpinghands.services.definition.AuthService;
import com.taquitosncapas.helpinghands.services.definition.UserService;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/master")
public class MasterController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    private TokenManager tokenManager;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/get-my-profile")
    public ResponseEntity<GetMasterProfileResponse> getMyMasterProfile(@RequestHeader(value = "Authorization") String autho) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);

        User foundUser = userService.findOneByEmail(email);

        if(foundUser.getId_role()!=4){
            return new ResponseEntity<>(new GetMasterProfileResponse("You cannot do this action",null),HttpStatus.UNAUTHORIZED);
        }

        Optional<Master> master = userService.findMasterById(Long.valueOf(foundUser.getId()));

        if(!master.isPresent()) {
            return new ResponseEntity<>(new GetMasterProfileResponse("User not found", null),HttpStatus.NOT_FOUND);
        }
        master.get().setPass("null");
        return new ResponseEntity<>(new GetMasterProfileResponse("User Logged",master),HttpStatus.OK);
    }


    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/get-managers")
    public ResponseEntity<?> getAllManagers(@RequestHeader (value = "Authorization") String autho, Pageable pageable) throws Exception {
        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User user = userService.findOneByEmail(email);
        if (user.getId_role() != 4){
            return new ResponseEntity<>(new GetManagersInfoResponse("This action can only be performed by a Master",null),HttpStatus.UNAUTHORIZED);
        }else {
            Page<User> managers = userService.findByRole(3L,pageable);
            List<User> managersList = managers.toList();
            managersList.forEach((manager) -> {
                manager.setPass("hidden");
            });
            return new ResponseEntity<>(new HandlerPage(managers.getTotalElements(), managersList),HttpStatus.OK);
        }
    }



    @CrossOrigin(origins = "*", maxAge = 3600)
    @DeleteMapping("/delete-manager/{id}")
    public ResponseEntity<?> deleteManager(@RequestHeader (value = "Authorization") String autho
            , @PathVariable(value="id") Long managerId) throws Exception {

        String token = autho.split(" ")[1];
        String email = tokenManager.getEmailFromToken(token);
        User user = userService.findOneByEmail(email);
        if (user.getId_role() != 4){
            return ResponseEntity.status(401).body("This action can only be performed by a Master");
        }else {
            User foundUser = userService.findOneById(managerId);

            if(foundUser == null) {
                return ResponseEntity.status(404).body("Manager Not Found!!");
            }
            if(foundUser.getId_role() != 3){
                return ResponseEntity.status(404).body("This user is not a manager, cannot be deleted");
            }else{
                userService.deleteManagerById(managerId);
                return ResponseEntity.status(200).body("Manager Deleted Successfully!!");
            }
        }
    }

}
