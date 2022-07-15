package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.entities.Approval;
import com.taquitosncapas.helpinghands.models.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApprovalService {
    Optional<Approval> findOneApprovalByProjectId(Long projectId);

    Page<Project> getAllProjectsByRespondantId(Long respondantId, Pageable pageable);
}
