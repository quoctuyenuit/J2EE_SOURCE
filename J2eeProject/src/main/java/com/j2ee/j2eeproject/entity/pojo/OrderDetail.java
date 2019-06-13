package com.j2ee.j2eeproject.entity.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail {

	@EmbeddedId
	private PrimaryKey pk;
	
	public PrimaryKey getPk() {
		return pk;
	}

	public void setPk(PrimaryKey pk) {
		this.pk = pk;
	}

	@Embeddable
	public static class PrimaryKey implements Serializable {
		private static final long serialVersionUID = 1L;
		@Column(name = "product_id")
		private Integer productId;
		
		@Column(name = "order_id")
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

		public PrimaryKey(Integer productId, Integer orderId) {
			super();
			this.productId = productId;
			this.orderId = orderId;
		}

		public PrimaryKey() {
			super();
		}
	}
}
