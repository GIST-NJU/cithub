package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
public interface ModelsService extends IService<ModelsEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<ModelsEntity> listModelsByProjectId(Integer projectId);

}


