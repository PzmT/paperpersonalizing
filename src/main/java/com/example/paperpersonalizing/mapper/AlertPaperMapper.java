package com.example.paperpersonalizing.mapper;

import com.example.paperpersonalizing.entity.PaperPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用来操作"paper_to_alert"表
 * */
@Mapper
public interface AlertPaperMapper {
    @Select("select * from paper_to_alert INNER JOIN paper_alerted_to_user" +
            "ON paper_to_alert.paper_id=paper_alerted_to_user.paper_id" +
            "WHERE paper_alerted_to_user.user_id=#{userId}")
    List<PaperPo> getAlertPaperByUserId(@Param("userId") int userId);
}
