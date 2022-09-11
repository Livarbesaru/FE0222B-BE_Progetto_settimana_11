package com.dblibrary.model.auth;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserDTO {
	@NotNull
	private String userName;
	
	@NotNull
	private String password;
	
	@NotNull
	private String email;
	private boolean isActive;
	private Set<String> roles = new HashSet<>();

	public UserDTO() {
		
	}
	public UserDTO(String userName,String password,String email,boolean isActive) {
		this.email=email;
		this.userName=userName;
		this.password=password;
		this.isActive=isActive;
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}
}
