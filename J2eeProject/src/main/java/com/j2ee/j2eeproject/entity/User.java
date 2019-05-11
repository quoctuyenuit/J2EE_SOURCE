package com.j2ee.j2eeproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "User")
public class User {
	 @Id
	 @Column(name = "id", nullable = false)
	 private String id;

	 @NotNull
	 @Column(name = "type_id", nullable = false)
	 private Integer type_id;

	 @NotEmpty
	 @Column(name = "name", nullable = false)
	 private String name;

	 @NotEmpty
	 @Email
	 @Column(name = "email", nullable = false)
	 private String email;
	 
	 @NotEmpty
	 @Column(name = "password", nullable = false)
	 private String password;

	 @Column(name = "address")
	 private String address;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Integer getType_id() {
		return type_id;
	}


	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public User(@NotNull Integer type_id, @NotEmpty String name, @NotEmpty @Email String email,
			@NotEmpty String password, String address) {
		super();
		this.type_id = type_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
	}


	public User(String id, @NotNull Integer type_id) {
		super();
		this.id = id;
		this.type_id = type_id;
	}
	
	
	public User() {}
}
