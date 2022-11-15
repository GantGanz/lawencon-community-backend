package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_attachment_activity")
public class AttachmentActivity extends BaseEntity {

	private static final long serialVersionUID = -8199311893889229151L;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	@OneToOne
	@JoinColumn(name = "activity_id", nullable = false)
	private Activity activity;
}
