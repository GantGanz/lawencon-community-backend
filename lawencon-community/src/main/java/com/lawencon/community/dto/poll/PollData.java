package com.lawencon.community.dto.poll;

import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.dto.polloption.PollOptionData;

import lombok.Data;

@Data
public class PollData {

	private String id;
	private String pollTitle;
	private LocalDateTime endAt;
	private String postId;
	private Boolean isActive;
	private Long countVote;
	private Boolean isVoted;
	private List<PollOptionData> pollOptionDatas;
	
}
