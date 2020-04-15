package com.community.community.mapper;

import antlr.Token;
import com.community.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

	@Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
	void insertUser(User user);

	@Select("select * from user where token = #{token}")
	User findByToken(@Param("token") String token);

	@Select("select * from user where id = #{id}")
//	@Results(id="userMap", value= {
//			@Result(column = "ACCOUNT_ID", property = "accountId"),
//			@Result(column = "GMT_CREATE", property = "gmtCreate"),
//			@Result(column = "GMT_MODIFIED", property = "gmtModified"),
//			@Result(column = "AVATAR_URL", property = "avatarUrl")
//	})
	User findById(Integer id);
}
