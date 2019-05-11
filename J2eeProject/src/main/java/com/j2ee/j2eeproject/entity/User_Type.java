package com.j2ee.j2eeproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "User_Type")
public class User_Type {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@NotEmpty
	@UniqueElements
	@Column(name = "name", nullable = false)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User_Type() {
		super();
	}

	public User_Type(@NotEmpty @UniqueElements String name) {
		super();
		this.name = name;
	}
}
