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
@Table(name = "t_post_type", uniqueConstraints = {
		@UniqueConstraint(name = "t_post_type_bk", columnNames = { "post_type_code" }),
		@UniqueConstraint(name = "t_post_type_ck", columnNames = { "post_type_code", "post_type_name" }) })
@Data
@EqualsAndHashCode(callSuper = false)
public class PostType extends BaseEntity {

	private static final long serialVersionUID = -4945398026799662087L;

	@Column(name = "post_type_name", nullable = false, length = 20)
	@Setter
	@Getter
	private String postTypeName;
	@Column(name = "post_type_code", nullable = false, length = 5)
	@Setter
	@Getter
	private String postTypeCode;

}
