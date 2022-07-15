package com.taquitosncapas.helpinghands.repositories;

import com.taquitosncapas.helpinghands.models.entities.Token;
import com.taquitosncapas.helpinghands.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
	List<Token> findByUserAndActive(User user, Boolean active);
}
