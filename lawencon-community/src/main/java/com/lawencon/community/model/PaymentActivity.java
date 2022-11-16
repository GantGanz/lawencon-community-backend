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
@Table(name = "t_payment_activity")
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentActivity extends BaseEntity {

	private static final long serialVersionUID = -6258941125653807864L;

	@Column(name = "nominal", nullable = false)
	private BigDecimal nominal;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne
	@JoinColumn(name = "activity_id", nullable = false)
	private Activity activity;
}
