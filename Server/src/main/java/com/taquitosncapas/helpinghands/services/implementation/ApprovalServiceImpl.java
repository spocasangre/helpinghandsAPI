package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.entities.Approval;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.repositories.ApprovalRepository;
import com.taquitosncapas.helpinghands.services.definition.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;

    @Override
    public Optional<Approval> findOneApprovalByProjectId(Long projectId) {
        return approvalRepository.getOneByProjectId(projectId);
    }

    @Override
    public Page<Project> getAllProjectsByRespondantId(Long respondantId, Pageable pageable) {
        return approvalRepository.getAllProjectsByRespondantId(respondantId, pageable);
    }
}
