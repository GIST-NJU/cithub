package com.gist.cithub.backend.Repo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-09-08 21:44:17
 */
@Mapper
public interface ListDao extends BaseMapper<ListEntity> {
//public interface ListDao extends MPJBaseMapper<ListEntity> {

    List<ListEntity> searchByInstitution(@Param("institution") String institution);

    List<ListEntity> searchByCountry(@Param("country") String country);

    List<ListEntity> searchByTag(@Param("tag") String tag);


//    根据标题数组查询Papers，用于BenchmarkHome页面点击References按钮
    @Select("<script>" +
            "SELECT * " +
            "FROM repository_list " +
            "WHERE title IN " +
            "<foreach collection='titles' item='title' open='(' separator=',' close=')'>" +
            "#{title}" +
            "</foreach>" +
            "ORDER BY year DESC" +
            "</script>")
    List<ListEntity> getByTitleList(@Param("titles") List<String> titles);
}
