package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.Tools.entity.ConversionEntity;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;

import java.util.List;

public interface ConversionService extends IService<ConversionEntity> {
    List<ConversionEntity> listConversionByTestSuitesID(Integer testsuitesID);
}
