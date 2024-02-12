package com.gist.cithub.backend.Tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import com.gist.cithub.backend.Tools.entity.PrioritisationEntity;

import java.util.List;

public interface PrioritisationService extends IService<PrioritisationEntity> {
    List<PrioritisationEntity> listPrioritisationByTestSuitesID(Integer testsuitesID);
}
