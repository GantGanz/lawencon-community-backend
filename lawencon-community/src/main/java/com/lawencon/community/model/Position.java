package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_position", uniqueConstraints = {
		@UniqueConstraint(name = "t_position_bk", columnNames = { "position_code" }),
		@UniqueConstraint(name = "t_position_ck", columnNames = { "position_code", "position_name" }) })
@Data
@EqualsAndHashCode(callSuper = false)
public class Position extends BaseEntity {

	private static final long serialVersionUID = -3682702930206011139L;

	@Column(name = "position_name", nullable = false, length = 50)
	@Setter
	@Getter
	private String positionName;

	@Column(name = "position_code", nullable = false, length = 5)
	@Setter
	@Getter
	private String positionCode;
}
