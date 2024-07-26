package com.example.paperpersonalizing.mapper;

import com.example.paperpersonalizing.entity.UserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用来操作"user_info"表
 */
@Mapper
public interface UserInfoMapper {
    @Select("SELECT user_id FROM user_info WHERE user_name=#{userName}")
    int getUserIdByUserName(@Param("userName")String userName);
    @Select("SELECT user_name FROM user_info WHERE user_id=#{userId}")
    String getUserNameByUserId(@Param("userId")Integer userId);
    @Select("SELECT * FROM user_info")
    List<UserPo> getAllUsers();

}
