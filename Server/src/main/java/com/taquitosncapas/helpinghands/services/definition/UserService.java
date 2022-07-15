package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.dtos.profile.EditManagerRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditOrgRequest;
import com.taquitosncapas.helpinghands.models.dtos.profile.EditVolunteerRequest;
import com.taquitosncapas.helpinghands.models.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
	public Optional<Volunteer> findVolunteerById(Long id) throws Exception;

	public Optional<Organization> findOrganizationById(Long id) throws Exception;

	public Optional<Manager> findManagerById(Long id) throws Exception;

	public Optional<Master> findMasterById(Long id) throws Exception;

	public Page<User> findByRole(Long idRole, Pageable pageable) throws Exception;

	void updateVolunteer(EditVolunteerRequest editVolunteerRequest, User user) throws Exception;

	void updateOrg(EditOrgRequest editOrgRequest, User user) throws Exception;

	void updateManager(EditManagerRequest editManagerRequest, User user) throws Exception;

	User findOneByEmail(String email) throws Exception;

	User findOneById(Long id) throws Exception;

    void deleteManagerById(Long id);

}
