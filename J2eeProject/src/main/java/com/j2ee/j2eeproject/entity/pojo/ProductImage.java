package com.j2ee.j2eeproject.entity.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "product_image")
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@NotEmpty
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "product_id")
	private Integer productId;

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

	public Integer getProduct_id() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public ProductImage(@NotEmpty String name, Integer productId) {
		super();
		this.name = name;
		this.productId = productId;
	}

	public ProductImage() {
		super();
	}
}
