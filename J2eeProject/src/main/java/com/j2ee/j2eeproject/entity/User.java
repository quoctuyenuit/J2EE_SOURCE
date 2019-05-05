package com.j2ee.j2eeproject.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class User {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", nullable = false)
	 private Integer id;
	 
	 @NotEmpty 
	 @Column(name = "type_id", nullable = false)
	 private Integer type_id;

	 @NotEmpty
	 @Column(name = "name", nullable = false)
	 private String name;

	 @Email
	 @Column(name = "email")
	 private String email;
	 
	 @NotEmpty
	 @Column(name = "password", nullable = false)
	 private String password;
	 
	 @Column(name = "address")
	 private String address;
	 
	 @Column(name = "created")
	 private Date created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User(@NotEmpty Integer type_id, @NotEmpty String name, @Email String email,
			@NotEmpty String password, String address, Date created) {
		super();
		this.type_id = type_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.created = created;
	}

	public User() {
		super();
	}
}
