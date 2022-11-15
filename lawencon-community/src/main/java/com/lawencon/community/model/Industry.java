package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_industry", uniqueConstraints = {
		@UniqueConstraint(name = "t_industry_bk", columnNames = { "industry_code" }),
		@UniqueConstraint(name = "t_industry_ck", columnNames = { "industry_code", "industry_name" }) })
@Data
@EqualsAndHashCode(callSuper = false)
public class Industry extends BaseEntity {

	private static final long serialVersionUID = 5070956208128726910L;

	@Column(name = "industry_name", nullable = false, length = 50)
	private String industryName;

	@Column(name = "industry_code", nullable = false, length = 5)
	private String industryCode;
}
