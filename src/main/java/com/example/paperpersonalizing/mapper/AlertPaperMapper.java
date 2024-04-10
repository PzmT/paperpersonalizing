package com.example.paperpersonalizing.mapper;

import com.example.paperpersonalizing.entity.PaperPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用来操作"paper_to_alert"表
 * */
@Mapper
public interface AlertPaperMapper {
    @Select("SELECT * FROM paper_to_alert INNER JOIN paper_alerted_to_user" +
            "ON paper_to_alert.paper_id=paper_alerted_to_user.paper_id" +
            "WHERE paper_alerted_to_user.user_id=#{userId}")
    List<PaperPo> getAlertedPaperByUserId(@Param("userId") int userId);
    @Select("SELECT * FROM paper_to_alert INNER JOIN alerting " +
            "ON paper_to_alert.paper_id=alerting.paper_id" +
            " WHERE alerting.user_id=#{userId}")
    List<PaperPo> getAlertingPaperByUserId(@Param("userId") int userId);
    @Insert("INSERT INTO alerting (paper_id,user_id) values (#{paperId},#{userId})")
    int insertAlertingPaper(@Param("paperId") int paperId,@Param("userId")int userId);
}
