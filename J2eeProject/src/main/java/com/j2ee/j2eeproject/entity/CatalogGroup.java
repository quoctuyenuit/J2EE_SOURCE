package com.j2ee.j2eeproject.entity;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class CatalogGroup {
	@NotEmpty
	@Column(name = "catalogId")
	private Integer catalogId;
	
	@NotEmpty
	@Column(name = "productId")
	private Integer productId;

	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
