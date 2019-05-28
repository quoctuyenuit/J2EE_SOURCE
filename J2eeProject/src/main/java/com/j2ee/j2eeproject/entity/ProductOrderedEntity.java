package com.j2ee.j2eeproject.entity;

public class ProductOrderedEntity {
	private Integer id;
	private String name;
	private Integer price;
	private Integer quantity;
	private String imageSample;
	
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getImageSample() {
		return imageSample;
	}
	public void setImageSample(String imageSample) {
		this.imageSample = imageSample;
	}
	
	public ProductOrderedEntity(Integer id, String name, Integer price, Integer quantity, String imageSample) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.imageSample = imageSample;
	}
	public ProductOrderedEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
