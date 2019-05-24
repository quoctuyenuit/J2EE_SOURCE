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
	private Integer price;

	@Column(name = "discount")
	private Float discount;
	
	@Column(name = "image_sample")
	private String imageSample;

	@Column(name = "description")
	private String description;
	
	public String getImageSample() {
		return imageSample;
	}

	public void setImageSample(String imageSample) {
		this.imageSample = imageSample;
	}

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Product(@NotEmpty String name, @NotEmpty Integer price, Float discount, String imageSample,
			String description) {
		super();
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.imageSample = imageSample;
		this.description = description;
	}

	public Product() {
		super();
	}
}
