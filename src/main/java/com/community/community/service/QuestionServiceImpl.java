package com.community.community.service;

import com.community.community.dto.PaginationDTO;
import com.community.community.dto.QuestionDTO;
import com.community.community.mapper.QuestionMapper;
import com.community.community.mapper.UserMapper;

import com.community.community.model.Question;
import com.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Override
	public PaginationDTO list(Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.count();

		if (page<1) page = 1;
		if(page>getTotalPage(size)) page = getTotalPage(size);

		int offset = (page-1)*size;

		List<QuestionDTO> questionDTOlist = new ArrayList<>();
		List<Question> questions = questionMapper.questionList(offset, size);

		for (Question question : questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO(question);
			questionDTO.setUser(user);
			questionDTOlist.add(questionDTO);
		}

		paginationDTO.setQuestions(questionDTOlist);
		paginationDTO.setPagination(totalCount, page, size);
		return paginationDTO;
	}

	@Override
	public Integer getTotalPage(Integer size) {
		return (int)Math.ceil(questionMapper.count()/(double)size);
	}
}
