package com.j2ee.j2eeproject.entity.pojo;

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

	 @Column(name = "name")
	 private String name;
	 
	 @Column(name = "first_name")
	 private String firstName;
	 
	 @Column(name = "last_name")
	 private String lastName;

	 @NotEmpty
	 @Email
	 @Column(name = "email", nullable = false)
	 private String email;
	 
	 @Column(name = "phone")
	 private String phone;
	 
	 @NotEmpty
	 @Column(name = "password", nullable = false)
	 private String password;

	 @Column(name = "address")
	 private String address;
	 
	 @Column(name = "wards")
	 private String wards;
	 
	 @Column(name = "district")
	 private String district;
	 
	 @Column(name = "province")
	 private String province;
	
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWards() {
		return wards;
	}

	public void setWards(String wards) {
		this.wards = wards;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User(@NotNull Integer type_id, @NotEmpty String name, String firstName, String lastName, String phone, 
			@NotEmpty @Email String email, @NotEmpty String password, String address, String wards, String district,
			String province) {
		super();
		this.type_id = type_id;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
		this.wards = wards;
		this.district = district;
		this.province = province;
	}

	public User(String id, @NotNull Integer type_id) {
		super();
		this.id = id;
		this.type_id = type_id;
	}
	
	
	public User() {}
}
