package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.Tools.entity.ProjectsEntity;

import java.util.List;
import java.util.Map;

/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
public interface ProjectsService extends IService<ProjectsEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<ProjectsEntity> listProjectByUserID(Integer userID);

}

