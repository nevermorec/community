package com.community.community.mapper;

import com.community.community.model.Question;
import com.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
	@Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
	void insertQuestion(Question question);

	@Select("select * from question limit #{offset}, #{size}")
	List<Question> questionList(@Param(value = "offset") int offset, @Param(value = "size") int size);

	@Select("select count(1) from QUESTION")
	Integer count();
}
