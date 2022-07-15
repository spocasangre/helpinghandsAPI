package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.dtos.auth.ManagerRegisterRequest;
import com.taquitosncapas.helpinghands.models.dtos.auth.MasterRegisterRequest;
import com.taquitosncapas.helpinghands.models.dtos.auth.OrganizationRegisterRequest;
import com.taquitosncapas.helpinghands.models.dtos.auth.VolunteerRegisterRequest;
import com.taquitosncapas.helpinghands.models.entities.User;

public interface AuthService {

	void registerVolunteer(VolunteerRegisterRequest volunteer) throws Exception;

	void registerOrganization(OrganizationRegisterRequest organization) throws Exception;

	void registerManager(ManagerRegisterRequest manager) throws Exception;

	void registerMaster(MasterRegisterRequest master) throws Exception;

	Boolean comparePassword(User user, String passToCompare) throws Exception;

	void insertToken(User user, String token) throws Exception;

    Boolean isTokenValid(User user, String token) throws Exception;

	User findOneByIdentifer(String identifier) throws Exception;


}
