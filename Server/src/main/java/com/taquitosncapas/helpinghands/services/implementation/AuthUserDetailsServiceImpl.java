package com.taquitosncapas.helpinghands.services.implementation;

import com.taquitosncapas.helpinghands.models.entities.User;
import com.taquitosncapas.helpinghands.services.definition.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	AuthService authService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User userFound = authService.findOneByIdentifer(email);
			
			if(userFound != null) {
				return new org.springframework.security.core.userdetails.User(
							userFound.getEmail(),
							userFound.getPass(),
							new ArrayList<>()
						);
			}else {
				throw new UsernameNotFoundException("User not found: " + email);
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found: " + email);
		}
	}

}
