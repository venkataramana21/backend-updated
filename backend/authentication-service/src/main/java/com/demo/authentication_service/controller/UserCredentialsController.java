package com.demo.authentication_service.controller;

import com.demo.authentication_service.dao.UserCredentialsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.demo.authentication_service.entity.UserCredentialsEntity;
import com.demo.authentication_service.service.JwtService;
import com.demo.authentication_service.service.UserCredentialsService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")

public class UserCredentialsController {
	@Autowired
	JwtService jwtService;
	@Autowired
	PasswordEncoder passwordEncoder;
	String token="";

	@Autowired
	private UserCredentialsService userCredService;

	@Autowired
	private UserCredentialsDao userCredentialsDao;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public UserCredentialsEntity register(@RequestBody UserCredentialsEntity user) {
		return userCredService.register(user);
	}
	
	@GetMapping("/validate/token")
	public boolean validateToken(@RequestParam String token) {
		return userCredService.verifyToken(token);
	}
	
	@PostMapping("/validate/user")
	public ResponseEntity<Map<String, Object>> getToken(@RequestBody UserCredentialsEntity user) {
		System.out.println("user : " + user);

		UserCredentialsEntity olduser=userCredentialsDao.findByEmail(user.getEmail()).get();

		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		System.out.println("authenticated?? : " + authenticate.isAuthenticated());
		if(authenticate.isAuthenticated()) {
			 token= userCredService.generateToken(user.getEmail());
			Map<String, Object> response = new HashMap<>();
			response.put("id", olduser.getUserId());        // Assuming `getUserId()` is a method in UserCredentialsEntity
			response.put("token", token);                // Add the generated token
			response.put("username", olduser.getName());
			System.out.println("USer resposne"+response);
			return ResponseEntity.ok(response);
		}

		return null;
	}

	@GetMapping("/findByEmail")
	public UserCredentialsEntity getUserByEmail(@RequestParam String email){
		return userCredentialsDao.findByEmail(email).get();
	}

	@PutMapping("/update/{userId}")
	public UserCredentialsEntity updateProfile(@PathVariable Long userId,@RequestBody UserCredentialsEntity userCredentialsEntity){
		return userCredService.updateProfile(userId,userCredentialsEntity);
	}
	@PutMapping("/updatePassword")
	public void updatePassword(@RequestParam long userId, @RequestParam String newPassword){
		UserCredentialsEntity u=userCredentialsDao.findById(userId).get();
		u.setPassword(passwordEncoder.encode(newPassword));
		userCredentialsDao.saveAndFlush(u);
	}
	@DeleteMapping("/delete/{userId}")
	public void deleteUser(@PathVariable Long userId){
		userCredService.deleteUser(userId);
	}
}
