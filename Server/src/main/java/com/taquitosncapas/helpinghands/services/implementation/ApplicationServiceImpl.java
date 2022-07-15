package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.entities.Application;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.repositories.ApplicationRepository;
import com.taquitosncapas.helpinghands.services.definition.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public void apply(Project project, User user) {
        Application application = new Application();

        application.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        application.setProject(project);
        application.setUser(user);
        application.setResponse(0);
        application.setIsAbandoned(0);

        applicationRepository.save(application);
    }

    @Override
    public void respondByAppId(Long appliId) {

        Optional<Application> myApp = findOneById(appliId);
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(date);
        myApp.get().setResponse(1);
        myApp.get().setRespondedAt(date);
        applicationRepository.save(myApp.get());
    }

    @Override
    public void abandoneByAppId(Long appId) {
        Optional<Application> myapp = findOneById(appId);
        myapp.get().setIsAbandoned(1);
        myapp.get().setAbandonedAt(Timestamp.valueOf(LocalDateTime.now()));
        applicationRepository.save(myapp.get());
    }

    @Override
    public Optional<Application> findOneByProjectIdAndUserId(Long projectId, Long userId) {
        return applicationRepository.findOneByProjectIdAndUserId(projectId, userId);
    }

    @Override
    public Page<Application> getAllByUserId(Long userId, Pageable pageable) {
        return applicationRepository.getAllByUserId(userId,pageable);
    }

    @Override
    public Page<Application> getAllAbandonedByUserId(Long userId, Pageable pageable) {
        return applicationRepository.getAllAbandonedByUserId(userId, pageable);
    }

    @Override
    public Optional<Application> findOneById(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }

    @Override
    public Page<Application> getAllbyOrgId(Long orgId, Pageable pageable) {
        return applicationRepository.getAllApplicationsNotResponded(orgId,pageable);
    }

    @Override
    public Page<User> getUserFromProjectId(Long projectId, Pageable pageable) {
        return applicationRepository.getUserFromProjectId(projectId,pageable);
    }
}
