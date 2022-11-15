package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_file")
public class File extends BaseEntity {

	private static final long serialVersionUID = 8251053564074380636L;

	@Column(name = "file_codes", nullable = false)
	@Setter
	@Getter
	private String fileCodes;

	@Column(name = "extensions", nullable = false, length = 5)
	@Setter
	@Getter
	private String extensions;

}
