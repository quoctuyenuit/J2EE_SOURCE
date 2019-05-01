package com.j2ee.j2eeproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TakenOrder")
public class TakenOrder {
	@NotEmpty
	@Column(name = "productId")
	private Integer productId;
	
	@NotEmpty
	@Column(name = "orderId")
	private Integer orderId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public TakenOrder(@NotEmpty Integer productId, @NotEmpty Integer orderId) {
		super();
		this.productId = productId;
		this.orderId = orderId;
	}

	public TakenOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
}
