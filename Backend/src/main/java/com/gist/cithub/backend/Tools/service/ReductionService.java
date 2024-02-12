package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.Tools.entity.ReductionEntity;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
public interface ReductionService extends IService<ReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<ReductionEntity> listReductionByTestSuitesID(Integer Modelid);

}

