package com.example.paperpersonalizing.mapper;

import com.example.paperpersonalizing.entity.CategoryPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 *用来操作"category"和"users_category"表
 */

@Mapper
public interface UsersCategoryAndCategoryMapper {
    @Select("SELECT category.category,category.id,users_category.parent_category_id FROM category " +
            "INNER JOIN users_category ON users_category.category_id=category.id" +
            "WHERE category.id IN" +
            "<foreach item='item' index='index' collection='categoryIds' open='(' separator=',' close=')' >" +
            "#{item}" +
            "</foreach>")
    List<CategoryPo> getCategoryByCategoryId(@Param("categoryIds") Set<Integer> categoryId);

    @Select("SELECT id FROM category WHERE category = #{category}")
    int getCategoryIdByFormalCategoryName(@Param("category")String category);

    @Select("SELECT category FROM category ")
    List<String> getAllCategoryName();
}
