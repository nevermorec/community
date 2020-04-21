package com.community.community.dto;

import com.community.community.model.Question;
import com.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
	private Integer id;
	private String title;
	private String description;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer creator;
	private String tag;
	private Integer commentCount;
	private Integer viewCount;
	private Integer likeCount;
	private User user;

	public QuestionDTO(Question question) {
		this.id = question.getId();
		this.title = question.getTitle();
		this.description = question.getDescription();
		this.gmtCreate = question.getGmtCreate();
		this.gmtModified = question.getGmtModified();
		this.creator = question.getCreator();
		this.tag = question.getTag();
		this.commentCount = question.getCommentCount();
		this.viewCount = question.getViewCount();
		this.likeCount = question.getLikeCount();
		this.user = null;
	}

	public QuestionDTO() {
	}
}
