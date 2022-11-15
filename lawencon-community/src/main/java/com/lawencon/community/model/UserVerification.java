package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_user_verification")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVerification extends BaseEntity {

	private static final long serialVersionUID = 4106206381476361596L;

	@Column(name = "verification_code", nullable = false, length = 5)
	private String verificationCode;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
