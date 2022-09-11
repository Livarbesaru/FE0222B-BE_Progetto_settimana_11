package com.dblibrary.model.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter//Annotazione di Lombok per creare i getter, setter e costruttore
@Entity
@Table (name = "user_spring") //user_spring se si usa postgresql, perché user va in conflitto
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String userName;

	private String password;
	
	@Column(unique = true)
	private String email;
	private boolean isActive;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();
	

	public User() {
		
	}
	public User(String userName,String password,String email,boolean isActive) {
		this.email=email;
		this.userName=userName;
		this.password=password;
		this.isActive=isActive;
	}

}
