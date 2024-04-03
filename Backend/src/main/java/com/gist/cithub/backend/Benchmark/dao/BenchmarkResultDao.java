package com.gist.cithub.backend.Benchmark.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkEntity;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface BenchmarkResultDao extends BaseMapper<BenchmarkResultEntity> {


}
