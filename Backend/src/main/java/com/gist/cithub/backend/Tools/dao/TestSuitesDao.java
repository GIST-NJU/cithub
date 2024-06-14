package com.gist.cithub.backend.Tools.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@Mapper
public interface TestSuitesDao extends BaseMapper<TestSuitesEntity> {

    @Select({
            "<script>",
            "SELECT modelid, COUNT(*) AS count",
            "FROM testsuites",
            "WHERE modelid IN",
            "<foreach item='item' index='index' collection='modelIds' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "GROUP BY modelid",
            "</script>"
    })
    List<Map<String, Object>> countTestSuitesByModelIds(@Param("modelIds") List<Integer> modelIds);
}
