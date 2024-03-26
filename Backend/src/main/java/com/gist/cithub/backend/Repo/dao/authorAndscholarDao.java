package com.gist.cithub.backend.Repo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gist.cithub.backend.Repo.entity.authorAndscholarEntity;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
//public interface authorAndscholarDao extends BaseMapper<authorAndscholarEntity> {
public interface authorAndscholarDao extends MPJBaseMapper<authorAndscholarEntity> {
}
