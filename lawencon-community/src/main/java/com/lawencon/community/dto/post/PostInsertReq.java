package com.lawencon.community.dto.post;

import java.util.List;

import com.lawencon.community.dto.attachmentpost.AttachmentPostInsertReq;
import com.lawencon.community.dto.poll.PollInsertReq;

import lombok.Data;

@Data
public class PostInsertReq {

	private String postTitle;
	private String postContent;
	private String postTypeId;
	private List<AttachmentPostInsertReq> attachmentPostInsertReqs;
	private PollInsertReq pollInsertReq; 
}
