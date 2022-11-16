package com.lawencon.community.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_payment_premium")
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentPremium extends BaseEntity {

	private static final long serialVersionUID = -6258941125653807864L;

	@Column(name = "nominal", nullable = false)
	private BigDecimal nominal;

	@Column(name = "is_approved", nullable = false)
	private Boolean isApproved;
	
	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
}
