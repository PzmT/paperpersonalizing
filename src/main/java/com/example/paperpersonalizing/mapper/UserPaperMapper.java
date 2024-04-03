package com.example.paperpersonalizing.mapper;


import com.example.paperpersonalizing.entity.PaperPo;
import com.example.paperpersonalizing.entity.papertreeComposite.PaperTreeEntry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用来操作"user_paper"与"user_paper_mapping"表
 */
@Mapper
public interface UserPaperMapper {

    @Select("select * from user_paper " +
            "INNER JOIN user_paper_mapping ON user_paper.paper_id=user_paper_mapping.paper_id" +
            "WHERE user_paper_mapping.user_id=#{userId}")
    List<PaperPo> getUserPaperByUserId(@Param("userId") int userId);

}
