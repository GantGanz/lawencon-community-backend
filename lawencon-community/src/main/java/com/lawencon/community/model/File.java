package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_file")
public class File extends BaseEntity{

	private static final long serialVersionUID = 8251053564074380636L;
	
	@Column(name="file_codes", nullable = false)
	private String fileCodes; 
	@Column(name="extensions", nullable = false, length = 5)
	private String extensions;
	
	public String getFileCodes() {
		return fileCodes;
	}
	public void setFileCodes(String fileCodes) {
		this.fileCodes = fileCodes;
	}
	public String getExtensions() {
		return extensions;
	}
	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}
	
}
