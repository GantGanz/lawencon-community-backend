package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_file")
@Data
@EqualsAndHashCode(callSuper = false)
public class File extends BaseEntity {

	private static final long serialVersionUID = 5637214128769408408L;

	@Column(name = "file_codes", nullable = false)
	private String fileCodes;

	@Column(name = "extensions", nullable = false, length = 5)
	private String extensions;

}
