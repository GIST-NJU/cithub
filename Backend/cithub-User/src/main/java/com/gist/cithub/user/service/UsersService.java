package com.gist.cithub.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.user.entity.UsersEntity;

import java.util.Map;

/**
 * 
 *
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 16:09:03
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);
    Boolean registerUser(Map<String,Object> userinfo);
}

