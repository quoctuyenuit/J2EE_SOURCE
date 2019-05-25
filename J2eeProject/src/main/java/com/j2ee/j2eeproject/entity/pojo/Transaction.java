package com.j2ee.j2eeproject.entity.pojo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty
    @Column(name = "status", nullable = false)
    private Integer status;
    
    @NotEmpty
    @Column(name = "amount")
    private Float amount;
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "created")
    private Date Created;
    
    @NotEmpty
    @Column(name = "paymentId", nullable = false)
    private Integer paymentId;
    
    @NotEmpty
    @Column(name = "orderId", nullable = false)
    private Integer orderId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return Created;
	}

	public void setCreated(Date created) {
		Created = created;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
    
    public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Transaction(@NotEmpty Integer status, @NotEmpty Float amount, String message, Date created,
			@NotEmpty Integer paymentId, @NotEmpty Integer orderId) {
		super();
		this.status = status;
		this.amount = amount;
		this.message = message;
		Created = created;
		this.paymentId = paymentId;
		this.orderId = orderId;
	}
}
