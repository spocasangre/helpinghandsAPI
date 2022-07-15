package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.dtos.profile.EditManagerRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditOrgRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditVolunteerRequest;
import com.taquitosncapas.helpinghands.models.entities.*;
import com.taquitosncapas.helpinghands.repositories.*;
import com.taquitosncapas.helpinghands.services.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private MasterRepository masterRepository;

    @Override
    public Optional<Volunteer> findVolunteerById(Long id) throws Exception {
        Optional<Volunteer> foundUser = volunteerRepository.findById(id);
        return foundUser;
    }

    @Override
    public Optional<Organization> findOrganizationById(Long id) throws Exception {
        Optional<Organization> foundUser = organizationRepository.findById(id);
        return foundUser;
    }

    @Override
    public Optional<Manager> findManagerById(Long id) throws Exception {
        Optional<Manager> foundUser = managerRepository.findById(id);
        return foundUser;
    }

    @Override
    public Optional<Master> findMasterById(Long id) throws Exception {
        Optional<Master> foundUser = masterRepository.findById(id);
        return foundUser;
    }

    @Override
    public Page<User> findByRole(Long idRole, Pageable pageable) throws Exception {
        Page<User> foundUsers = userRepository.findByIdRole(idRole,pageable);
        return foundUsers;
    }

    @Override
    public void updateVolunteer(EditVolunteerRequest editVolunteerRequest, User user) throws Exception{
        Optional<Volunteer> exist = findVolunteerById(user.getId());

        if(editVolunteerRequest.getName() != null){
            exist.get().setName(editVolunteerRequest.getName());
        }
        if(editVolunteerRequest.getLastname() != null){
            exist.get().setLastname(editVolunteerRequest.getLastname());
        }
        if(editVolunteerRequest.getTelephone_number() != null){
            exist.get().setTelephone_number(editVolunteerRequest.getTelephone_number());
        }
        if(editVolunteerRequest.getGender() != null){
            exist.get().setGender(editVolunteerRequest.getGender());
        }
        if(editVolunteerRequest.getBirth_date() != null){
            exist.get().setBirth_date(editVolunteerRequest.getBirth_date());
        }
        if(editVolunteerRequest.getCollege() != null){
            exist.get().setCollege(editVolunteerRequest.getCollege());
        }
        if(editVolunteerRequest.getCareer() != null){
            exist.get().setCareer(editVolunteerRequest.getCareer());
        }
        volunteerRepository.save(exist.get());
    }

    @Override
    public void updateOrg(EditOrgRequest editOrgRequest, User user) throws Exception {
        Optional<Organization> exist = findOrganizationById(user.getId());

        if(editOrgRequest.getName_org() != null){
            exist.get().setName_org(editOrgRequest.getName_org());
        }
        if(editOrgRequest.getName() != null){
            exist.get().setName(editOrgRequest.getName());
        }
        if(editOrgRequest.getLastname() != null){
            exist.get().setLastname(editOrgRequest.getLastname());
        }
        if(editOrgRequest.getBirth_date() != null){
            exist.get().setBirth_date(editOrgRequest.getBirth_date());
        }
        if(editOrgRequest.getGender() != null){
            exist.get().setGender(editOrgRequest.getGender());
        }
        if(editOrgRequest.getRegister_number() != null){
            exist.get().setRegister_number(editOrgRequest.getRegister_number());
        }
        if(editOrgRequest.getTelephone_number() != null){
            exist.get().setTelephone_number(editOrgRequest.getTelephone_number());
        }
        if(editOrgRequest.getAddress() != null){
            exist.get().setAddress(editOrgRequest.getAddress());
        }
        if(editOrgRequest.getPurpose() != null){
            exist.get().setPurpose(editOrgRequest.getPurpose());
        }
        if(editOrgRequest.getWebsite() != null){
            exist.get().setWebsite(editOrgRequest.getWebsite());
        }

        organizationRepository.save(exist.get());
    }

    @Override
    public void updateManager(EditManagerRequest editManagerRequest, User user) throws Exception {
        Optional<Manager> exist = findManagerById(user.getId());

        if(editManagerRequest.getName() != null){
            exist.get().setName(editManagerRequest.getName());
        }
        if(editManagerRequest.getLastname() != null){
            exist.get().setLastname(editManagerRequest.getLastname());
        }
        if(editManagerRequest.getTelephone_number() != null){
            exist.get().setTelephone_number(editManagerRequest.getTelephone_number());
        }
        if(editManagerRequest.getGender() != null){
            exist.get().setGender(editManagerRequest.getGender());
        }
        if(editManagerRequest.getBirth_date() != null){
            exist.get().setBirth_date(editManagerRequest.getBirth_date());
        }

        managerRepository.save(exist.get());
    }

    @Override
    public User findOneByEmail(String email) throws Exception {
        User foundUser = userRepository.findOneByEmail(email);
        return foundUser;
    }

    @Override
    public User findOneById(Long id) throws Exception {
        return userRepository.findFirstById(id);
    }

    @Override
    public void deleteManagerById(Long id) {
        userRepository.deleteById(id);
    }

}
