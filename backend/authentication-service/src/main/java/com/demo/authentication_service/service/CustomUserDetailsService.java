package com.demo.authentication_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.authentication_service.dao.UserCredentialsDao;
import com.demo.authentication_service.entity.UserCredentialsEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired 
	UserCredentialsDao userCredentialsDao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserCredentialsEntity> user = userCredentialsDao.findByEmail(email);
		System.out.println("user 2: " + user);
		return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("Username/password not valid!"));
	}
}
