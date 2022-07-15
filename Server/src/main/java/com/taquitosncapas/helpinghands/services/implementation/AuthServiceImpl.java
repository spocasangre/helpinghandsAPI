package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.dtos.auth.ManagerRegisterRequest;
import com.taquitosncapas.helpinghands.models.dtos.auth.MasterRegisterRequest;
import com.taquitosncapas.helpinghands.models.dtos.auth.OrganizationRegisterRequest;
import com.taquitosncapas.helpinghands.models.dtos.auth.VolunteerRegisterRequest;
import com.taquitosncapas.helpinghands.models.entities.*;
import com.taquitosncapas.helpinghands.repositories.*;
import com.taquitosncapas.helpinghands.services.definition.AuthService;
import com.taquitosncapas.helpinghands.utils.DateUtil;
import com.taquitosncapas.helpinghands.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

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

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private PasswordEncoder passEncoder;

	@Autowired
	private DateUtil dateUtil;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void registerVolunteer(VolunteerRegisterRequest userInfo) throws Exception {
		Volunteer volunteer = new Volunteer();

		LocalDate birth = dateUtil.convertDate(userInfo.getBirth_date());
		String encryptedPassword = passEncoder.encode(userInfo.getPass());

		volunteer.setName(userInfo.getName());
		volunteer.setLastname(userInfo.getLastname());
		volunteer.setGender(userInfo.getGender());
		volunteer.setTelephone_number(userInfo.getTelephone_number());
		volunteer.setBirth_date(userInfo.getBirth_date());
		volunteer.setCollege(userInfo.getCollege());
		volunteer.setCareer(userInfo.getCareer());
		volunteer.setEmail(userInfo.getEmail());
		volunteer.setPass(String.valueOf(encryptedPassword));
		volunteer.setId_role(Long.valueOf(userInfo.getRole()));

		volunteerRepository.save(volunteer);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void registerOrganization(OrganizationRegisterRequest userInfo) {
		Organization organization = new Organization();

		LocalDate birth = dateUtil.convertDate(userInfo.getBirth_date());
		String encryptedPassword = passEncoder.encode(userInfo.getPass());

		organization.setName(userInfo.getName());
		organization.setLastname(userInfo.getLastname());
		organization.setGender(userInfo.getGender());
		organization.setTelephone_number(userInfo.getTelephone_number());
		organization.setBirth_date(userInfo.getBirth_date());
		organization.setEmail(userInfo.getEmail());
		organization.setPass(String.valueOf(encryptedPassword));
		organization.setName_org(userInfo.getName_org());
		organization.setRegister_number(String.valueOf(userInfo.getRegister_number()));
		organization.setAddress(userInfo.getAddress());
		organization.setPurpose(userInfo.getPurpose());
		organization.setWebsite(userInfo.getWebsite());
		organization.setId_role(Long.valueOf(userInfo.getRole()));

		organizationRepository.save(organization);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void registerManager(ManagerRegisterRequest userInfo) {
		Manager manager = new Manager();

		LocalDate birth = dateUtil.convertDate(userInfo.getBirth_date());
		String encryptedPassword = passEncoder.encode(userInfo.getPass());

		manager.setName(userInfo.getName());
		manager.setLastname(userInfo.getLastname());
		manager.setGender(userInfo.getGender());
		manager.setTelephone_number(userInfo.getTelephone_number());
		manager.setBirth_date(userInfo.getBirth_date());
		manager.setEmail(userInfo.getEmail());
		manager.setPass(String.valueOf(encryptedPassword));
		manager.setId_role(Long.valueOf(userInfo.getRole()));

		managerRepository.save(manager);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void registerMaster(MasterRegisterRequest userInfo) {
		Master master = new Master();

		LocalDate birth = dateUtil.convertDate(userInfo.getBirth_date());
		String encryptedPassword = passEncoder.encode(userInfo.getPass());

		master.setName(userInfo.getName());
		master.setLastname(userInfo.getLastname());
		master.setGender(userInfo.getGender());
		master.setTelephone_number(userInfo.getTelephone_number());
		master.setBirth_date(userInfo.getBirth_date());
		master.setEmail(userInfo.getEmail());
		master.setPass(String.valueOf(encryptedPassword));
		master.setId_role(Long.valueOf(userInfo.getRole()));

		masterRepository.save(master);
	}

	@Override
	public Boolean comparePassword(User user, String passToCompare) throws Exception {
		return passEncoder.matches(passToCompare, user.getPass());
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void insertToken(User user, String token) throws Exception {
		cleanTokens(user);
		Token newToken = new Token(token, user,true);
		tokenRepository.save(newToken);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Boolean isTokenValid(User user, String token) throws Exception {
		cleanTokens(user);
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);

		return tokens.stream()
				.filter((userToken) -> {
					return userToken.getContent().equals(token);
				})
				.findAny()
				.orElse(null) != null;
	}

	@Override
	public User findOneByIdentifer(String identifier) throws Exception {
		User foundUser = userRepository.findOneByEmail(identifier);

		return foundUser;
	}


	@Transactional(rollbackOn = Exception.class)
	public void cleanTokens(User user) {
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
		tokens.forEach((userToken) -> {
			if(!tokenManager.validateJwtToken(userToken.getContent(), user.getEmail())) {
				userToken.setActive(false);
				tokenRepository.save(userToken);
			}
		});
	}
}
