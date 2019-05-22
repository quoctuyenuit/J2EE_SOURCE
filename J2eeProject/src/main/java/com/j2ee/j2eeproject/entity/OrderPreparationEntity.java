package com.j2ee.j2eeproject.entity;

public class OrderPreparationEntity {
	private Integer productId;
	private Integer quantity;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public OrderPreparationEntity(Integer productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}
	public OrderPreparationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
