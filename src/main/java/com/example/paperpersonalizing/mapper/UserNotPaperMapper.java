package com.example.paperpersonalizing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用来操作"user_not_paper"表
 * */
@Mapper
public interface UserNotPaperMapper {
    @Select("SELECT paper_id FROM user_not_paper WHERE user_id=#{userId")
    int getUserNotPaperByUserId(@Param("userId") int userId);
}
