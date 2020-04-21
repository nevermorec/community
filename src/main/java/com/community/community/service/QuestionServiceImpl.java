package com.community.community.service;

import com.community.community.dto.PaginationDTO;
import com.community.community.dto.QuestionDTO;
import com.community.community.exception.CustomizeErrorCode;
import com.community.community.exception.CustomizeException;
import com.community.community.mapper.QuestionMapper;
import com.community.community.mapper.UserMapper;

import com.community.community.model.Question;
import com.community.community.model.QuestionExample;
import com.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
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
		Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

		if (page<1) page = 1;
		if(page>getTotalPage(size)) page = getTotalPage(size);

		int offset = (page-1)*size;

		List<QuestionDTO> questionDTOlist = new ArrayList<>();
		List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));

		for (Question question : questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
//			QuestionDTO questionDTO = new QuestionDTO(question);
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOlist.add(questionDTO);
		}

		paginationDTO.setQuestions(questionDTOlist);
		paginationDTO.setPagination(totalCount, page, size);
		return paginationDTO;
	}

	@Override
	public PaginationDTO listByUser(Integer userId, Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();
		QuestionExample example = new QuestionExample();
		example.createCriteria().andCreatorEqualTo(userId);
		Integer totalCount = (int)questionMapper.countByExample(example);

		if (page<1) page = 1;
		if (page>getTotalPage(size)) page = getTotalPage(size);

		int offset = (page-1)*size;

		List<QuestionDTO> questionDTOlist = new ArrayList<>();
		List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));


		for (Question question : questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO(question);
			questionDTO.setUser(user);
			questionDTOlist.add(questionDTO);
		}

		paginationDTO.setQuestions(questionDTOlist);
		paginationDTO.setPagination(totalCount, page, size);
		return paginationDTO;
	}

	@Override
	public QuestionDTO getById(Integer id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if (question==null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		QuestionDTO questionDTO = new QuestionDTO(question);
		questionDTO.setUser(user);
		return questionDTO;
	}

	@Override
	public void updateOrCreateQuestion(Question question) {
		if (question.getId()!=null) {
			// 更新问题
			int i = questionMapper.updateByPrimaryKeySelective(question);
			if (i!=1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		} else {
			// 增加问题
			questionMapper.insertSelective(question);
		}
	}

	@Override
	public Integer getTotalPage(Integer size) {
		return (int)Math.ceil(questionMapper.countByExample(new QuestionExample())/(double)size);
	}

	@Override
	public void increaseViewCount(Integer id) {
//		Question question = questionMapper.selectByPrimaryKey(id);
//		question.setViewCount(question.getViewCount()+1);
//		questionMapper.updateByPrimaryKeySelective(question);
		Question question = new Question();
		question.setId(id);
		question.setViewCount(1);
		questionMapper.increaseViewCount(question);
	}
}
