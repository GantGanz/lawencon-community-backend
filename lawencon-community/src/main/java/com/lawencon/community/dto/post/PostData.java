package com.lawencon.community.dto.post;

import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.dto.attachmentpost.AttachmentPostData;
import com.lawencon.community.dto.comment.CommentData;
import com.lawencon.community.dto.poll.PollData;

import lombok.Data;

@Data
public class PostData {

	private String id;
	private Integer version;
	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String postTypeCode;
	private Long countLike;
	private Boolean isLiked;
	private Boolean isBookmarked;
	private String userId;
	private String creatorName;
	private String createdBy;
	private String positionName;
	private String company;
	private String fileId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<AttachmentPostData> attachmentPostDatas;
	private PollData pollData;
	private List<CommentData> commentDatas;
	private Long countComment;
}
