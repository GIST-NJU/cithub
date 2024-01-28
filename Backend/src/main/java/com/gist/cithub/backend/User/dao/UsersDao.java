package com.gist.cithub.backend.User.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gist.cithub.backend.User.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 16:09:03
 */
@Mapper
public interface UsersDao extends BaseMapper<UsersEntity> {
	
}
