package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.Tools.dao.EvaluationDao;
import com.gist.cithub.backend.Tools.dao.PrioritisationDao;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import com.gist.cithub.backend.Tools.entity.PrioritisationEntity;
import com.gist.cithub.backend.Tools.service.EvaluationService;
import com.gist.cithub.backend.Tools.service.PrioritisationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PrioritisationService")
public class PrioritisationServiceImpl extends ServiceImpl<PrioritisationDao, PrioritisationEntity> implements PrioritisationService {


    @Override
    public List<PrioritisationEntity> listPrioritisationByTestSuitesID(Integer testsuitesID) {
        QueryWrapper<PrioritisationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("TestSuitesID", testsuitesID);
        return list(wrapper);
    }
}
