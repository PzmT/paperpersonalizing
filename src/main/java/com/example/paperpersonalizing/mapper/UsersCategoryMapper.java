package com.example.paperpersonalizing.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UsersCategoryMapper {
    @Select("SELECT category_id FROM users_category WHERE user_id=#{userId}")
    List<Integer> getUserCategoryIdByUserId(@Param("userId") int userId);

}
