package com.community.community.service;

import com.community.community.dto.PaginationDTO;
import org.h2.mvstore.Page;

public interface QuestionService {
	public PaginationDTO list(Integer page, Integer size);

	public Integer getTotalPage(Integer size);
}
