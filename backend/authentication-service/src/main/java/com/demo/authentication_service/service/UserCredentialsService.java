package com.demo.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.authentication_service.dao.UserCredentialsDao;
import com.demo.authentication_service.entity.UserCredentialsEntity;

import java.util.Optional;

@Service
public class UserCredentialsService {
	@Autowired 
	JwtService jwtService;
	
	@Autowired
	UserCredentialsDao authDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserCredentialsEntity register(UserCredentialsEntity user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return authDao.saveAndFlush(user);
	}
	
	public String generateToken(String email) {
		return jwtService.generateToken(email);
	}
	
	public boolean verifyToken(String token) {
		jwtService.validateToken(token);
		return true;
	}

	public UserCredentialsEntity updateProfile(Long userId,UserCredentialsEntity userCredentialsEntity) {
		Optional<UserCredentialsEntity>us=authDao.findById(userId);
		if(us.isPresent()){
			UserCredentialsEntity u=us.get();
			u.setAddress(userCredentialsEntity.getAddress());
			u.setName(userCredentialsEntity.getName());
			u.setAge(userCredentialsEntity.getAge());
			u.setEmail(userCredentialsEntity.getEmail());
			u.setContact(userCredentialsEntity.getContact());
			authDao.saveAndFlush(u);
		}
		return us.get();
	}

	public void deleteUser(Long userId) {
		authDao.deleteById(userId);
	}
}
