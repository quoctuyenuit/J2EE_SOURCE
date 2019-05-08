package com.j2ee.j2eeproject.entity;

import java.sql.Date;

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
	 
	 @Column(name = "day_of_birth")
	 private String day_of_birth;
	 
	 @NotEmpty
	 @Column(name = "password", nullable = false)
	 private String password;

	 @Column(name = "address")
	 private String address;

	 @Column(name = "created")
	 private Date created;

	
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


	public String getDay_of_birth() {
		return day_of_birth;
	}


	public void setDay_of_birth(String day_of_birth) {
		this.day_of_birth = day_of_birth;
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


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public User(@NotEmpty Integer type_id, @NotEmpty String name, @NotEmpty @Email String email, String day_of_birth,
			@NotEmpty String password, String address, Date created) {
		super();
		this.type_id = type_id;
		this.name = name;
		this.email = email;
		this.day_of_birth = day_of_birth;
		this.password = password;
		this.address = address;
		this.created = created;
	}

	public User() {
		super();
		this.type_id = 1;
	}
}
