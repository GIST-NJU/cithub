package com.gist.cithub.backend.Tools.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EvaluationDao extends BaseMapper<EvaluationEntity> {
}
