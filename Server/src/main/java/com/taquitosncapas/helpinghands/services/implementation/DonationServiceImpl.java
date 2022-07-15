package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.dtos.CreateDonationRequest;
import com.taquitosncapas.helpinghands.models.entities.Donation;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.repositories.DonationRepository;
import com.taquitosncapas.helpinghands.services.definition.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

	@Autowired
	private DonationRepository donationRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Donation> findAll() {
		return donationRepository.findAll();
	}

	@Override
	public Page<Donation> findAllByOrgId(Long orgId, Pageable pageable) {
		return donationRepository.getDonationByOrgId(orgId, pageable);
	}

	@Override
	public Page<Donation> findAllByVolunteerId(Long volId, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Donation> findAllByProjectId(Long orgId, Pageable pageable) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Donation> findById(Long id) {
		return donationRepository.findById(id);
	}

	@Override
	public void create(CreateDonationRequest createDonationRequest, Project project, User user) {
		/*SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(formatter.format(date));*/

		Donation donation = new Donation();
		donation.setCreateAt(createDonationRequest.getCreateAt());
		donation.setValue(createDonationRequest.getValue());
		donation.setProject(project);
		donation.setUser(user);
		donationRepository.save(donation);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		donationRepository.deleteById(id);
	}

	@Override
	public Integer getTotalDonationsByOrgId(Long id) {
		return donationRepository.getTotalDonationByOrgId(id);
	}

	@Override
	public Integer getTotalDonations() {
		return Math.toIntExact(donationRepository.count());
	}


}
