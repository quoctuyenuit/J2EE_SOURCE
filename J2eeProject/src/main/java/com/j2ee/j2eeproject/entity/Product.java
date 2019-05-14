package com.j2ee.j2eeproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@NotEmpty
	@Column(name = "name", nullable = false)
	private String name;

	@NotEmpty
	@Column(name = "price", nullable = false)
	private Float price;

	@Column(name = "discount")
	private Float discount;

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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Product(@NotEmpty String name, @NotEmpty Float price, Float discount) {
		super();
		this.name = name;
		this.price = price;
		this.discount = discount;
	}

	public Product() {
		super();
	}
}
