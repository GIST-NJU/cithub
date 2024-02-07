package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;

import java.util.List;
import java.util.Map;

public interface EvaluationService extends IService<EvaluationEntity> {
    List<EvaluationEntity> listEvaluationByTestSuitesID(Integer testsuitesID);
}
