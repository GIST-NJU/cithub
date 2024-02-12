package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.Tools.dao.ConversionDao;
import com.gist.cithub.backend.Tools.dao.EvaluationDao;
import com.gist.cithub.backend.Tools.entity.ConversionEntity;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import com.gist.cithub.backend.Tools.service.ConversionService;
import com.gist.cithub.backend.Tools.service.EvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ConversionService")
public class ConversionServiceImpl extends ServiceImpl<ConversionDao, ConversionEntity> implements ConversionService {


    @Override
    public List<ConversionEntity> listConversionByTestSuitesID(Integer testsuitesID) {
        QueryWrapper<ConversionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("TestSuitesID", testsuitesID);
        return list(wrapper);
    }
}
