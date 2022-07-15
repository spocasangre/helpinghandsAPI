package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByEmail(String email);

    User findFirstById(long id);

    @Query("SELECT u FROM User u WHERE u.id_role = ?1")
    Page<User> findByIdRole(Long idRole, Pageable pageable);
}
