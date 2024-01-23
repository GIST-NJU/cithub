package com.gist.cithub.tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.tools.entity.ModelsEntity;
import com.gist.cithub.tools.entity.ProjectsEntity;

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

