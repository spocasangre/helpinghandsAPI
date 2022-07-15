package com.taquitosncapas.helpinghands.services.definition;

import com.taquitosncapas.helpinghands.models.entities.Application;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationService {
    void apply(Project project, User user);

    void respondByAppId(Long appliId);

    void abandoneByAppId(Long appId);

    Optional<Application> findOneByProjectIdAndUserId(Long projectId, Long userId);

    Page<Application> getAllByUserId(Long userId, Pageable pageable);

    Page<Application> getAllAbandonedByUserId(Long userId, Pageable pageable);

    Optional<Application> findOneById(Long applicationId);

    Page<Application> getAllbyOrgId(Long orgId, Pageable pageable);

    Page<User> getUserFromProjectId(Long projectId, Pageable pageable);
}
