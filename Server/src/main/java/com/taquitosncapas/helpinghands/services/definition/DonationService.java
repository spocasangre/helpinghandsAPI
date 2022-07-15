package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.dtos.CreateDonationRequest;
import com.taquitosncapas.helpinghands.models.entities.Donation;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DonationService {

	public Iterable<Donation> findAll();
	public Page<Donation> findAllByOrgId(Long orgId, Pageable pageable);
	public Page<Donation> findAllByVolunteerId(Long volId, Pageable pageable);
	public Page<Donation> findAllByProjectId(Long orgId, Pageable pageable);
	public Optional<Donation> findById(Long id);
	public void create(CreateDonationRequest createDonationRequest, Project project, User user);
	public void deleteById(Long id);
	public Integer getTotalDonationsByOrgId(Long id);
	public Integer getTotalDonations();
}
