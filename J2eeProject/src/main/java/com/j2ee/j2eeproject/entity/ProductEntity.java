package com.j2ee.j2eeproject.entity;

import java.util.List;

//Entity to show in page
//It will be parsed from product pojo
public class ProductEntity {
	private Integer id;
	private String name;
	private Integer price;
	private Float discount;
	private String description;
	private Integer catalogId;
	private List<String> listImages;
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
	public List<String> getListImages() {
		return listImages;
	}
	public void setListImages(List<String> listImages) {
		this.listImages = listImages;
	}
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	public ProductEntity(Integer id, String name, Integer price, Float discount, Integer catalogId, String description) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.catalogId = catalogId;
		this.description = description;
	}
	public ProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
}
