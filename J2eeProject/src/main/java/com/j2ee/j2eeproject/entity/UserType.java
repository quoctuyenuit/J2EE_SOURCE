package com.j2ee.j2eeproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "UserType")
public class UserType {
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

	public UserType() {
		super();
	}

	public UserType(@NotEmpty @UniqueElements String name) {
		super();
		this.name = name;
	}
}
