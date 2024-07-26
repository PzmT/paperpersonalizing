package com.example.paperpersonalizing.mapper;

import com.example.paperpersonalizing.entity.PaperPo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PaperMapper {

    @Select(" SELECT paper_id FROM paper WHERE doi =#{doi}")
    PaperPo getPaperIdByDoi(@Param("doi") String doi);

    @Insert(" INSERT INTO paper(doi) VALUES #{doi}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void setPaperByDoi(@Param("doi") String doi);
    @Select("SELECT category_id FROM paper WHERE paper_id=#{paperId}")
    int getPaperCategoryIdByPaperId(@Param("paperId") int paperId);
}
