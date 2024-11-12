package com.demo.authentication_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class UserCredentialsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String name;
	private String email;
	private String password;
	private String contact;
	private int age;
	private String address;

}
