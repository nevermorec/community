package com.community.community.dto;

import com.community.community.model.Comment;
import com.community.community.model.User;
import lombok.Data;

@Data
public class CommentDTO extends Comment {
	private User user;
}
