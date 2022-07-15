package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE d.project.owner.id = ?1")
    Page<Donation> getDonationByOrgId(Long orgId, Pageable pageable);

    @Query("SELECT sum(d.value) from Donation AS d where d.project.owner.id = ?1")
    Integer getTotalDonationByOrgId(Long orgId);

}
