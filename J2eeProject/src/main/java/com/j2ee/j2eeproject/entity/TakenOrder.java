package com.j2ee.j2eeproject.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TakenOrder")
public class TakenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @NotEmpty
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "created")
    private Date created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public TakenOrder(@NotEmpty String userId, Date created) {
		super();
		this.userId = userId;
		this.created = created;
	}

	public TakenOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
}
