package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Application;
import com.taquitosncapas.helpinghands.models.entities.Approval;
import com.taquitosncapas.helpinghands.models.entities.Project;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    //Para verificar que la aplicacion no se repita
    @Query("SELECT a FROM Application a WHERE a.project.id = ?1 AND a.user.id = ?2")
    Optional<Application> findOneByProjectIdAndUserId(Long projectId, Long userId);

    //Obtener todos los application a los cuales ha aplicado un usuario y la respuesta sea si, no esté abandonado y proyecto no haya terminado
    @Query("SELECT a FROM Application a WHERE a.user.id = ?1 AND a.response = 1 AND a.isAbandoned <> 1 AND a.project.isFinished <> 1")
    Page<Application> getAllByUserId(Long userId, Pageable pageable);

    //Obtener todos los applications abandonados a los cuales ha aplicado un usuario y la respuesta sea si y proyecto no haya terminado
    @Query("SELECT a FROM Application a WHERE a.user.id = ?1 AND a.response = 1 AND a.isAbandoned = 1 AND a.project.isFinished <> 1")
    Page<Application> getAllAbandonedByUserId(Long userId, Pageable pageable);

    //Se cambiara a obtener los proyecto en base a id de la aplicación.

    @Query("SELECT a FROM Application a WHERE a.project.owner.id = ?1 AND a.response = 0 AND a.isAbandoned <> 1 AND a.project.isFinished <> 1")
    Page<Application> getAllApplicationsNotResponded(Long orgId, Pageable pageable);

    @Query("SELECT a.user FROM Application a WHERE a.project.id = ?1 AND a.response = 1 AND a.isAbandoned <> 1 AND a.project.isFinished <> 1")
    Page<User> getUserFromProjectId(Long projectId, Pageable pageable);

}
