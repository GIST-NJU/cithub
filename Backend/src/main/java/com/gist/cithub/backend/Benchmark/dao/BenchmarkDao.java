package com.gist.cithub.backend.Benchmark.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkEntity;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface BenchmarkDao extends BaseMapper<BenchmarkEntity> {

    @Select("SELECT b.benchmarkset, " +
            "SUM(CASE WHEN b.modeltype = 'constrained' THEN 1 ELSE 0 END) AS consModelCount, " +
            "SUM(CASE WHEN b.modeltype = 'unconstrained' THEN 1 ELSE 0 END) AS unconsModelCount, " +
            "COUNT(DISTINCT b.reference) AS referenceCount, " +
            "(SELECT MAX(referenceyear) FROM benchmark b2 WHERE b2.benchmarkset = b.benchmarkset) AS maxReferenceYear " +
            "FROM benchmark b " +
            "GROUP BY b.benchmarkset")
    List<Map<String, Object>> getBenchmarkStats();
}