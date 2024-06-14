package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
public interface TestSuitesService extends IService<TestSuitesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TestSuitesEntity> listTestSuitesByModelID(Integer Modelid);

    List<Map<String, Object>> getCountByModelIds(List<Integer> modelIds);

}

