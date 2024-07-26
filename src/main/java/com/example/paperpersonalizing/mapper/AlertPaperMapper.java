package com.example.paperpersonalizing.mapper;

import com.example.paperpersonalizing.entity.PaperPo;
import org.apache.ibatis.annotations.*;

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
    @Insert("INSERT INTO alerting (paper_id,user_id,category_id) values (#{paperId},#{userId},#{categoryId})")
    int insertAlertingPaperByUserIdPaperId(@Param("paperId") int paperId,@Param("userId")int userId,@Param("categoryId")int categoryId);
    @Insert("INSERT INTO paper_alerted_to_user (alerted_paper_id,user_id,category_id) values (#{paperId},#{userId},#{categoryId}) ")
    int insertAlertedPaperByUserIdPaperIdCategoryId(@Param("paperId") int paperId,@Param("userId")int userId,@Param(("categoryId"))int categoryId);
    @Insert("INSERT INTO user_not_paper (paper_id,user_id) values (#{paperId},#{userId}) ")
    int insertNotPaperByUserIdPaperId(@Param("paperId") int paperId,@Param("userId")int userId);
    @Delete(("DELETE FROM alerting WHERE user_id=#{userId}"))
    int deleteAlertingPaperByUserId(@Param("userId") int userId);
}
