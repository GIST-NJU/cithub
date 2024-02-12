package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.Tools.dao.PrioritisationDao;
import com.gist.cithub.backend.Tools.dao.ReductionDao;
import com.gist.cithub.backend.Tools.entity.PrioritisationEntity;
import com.gist.cithub.backend.Tools.entity.ReductionEntity;
import com.gist.cithub.backend.Tools.service.PrioritisationService;
import com.gist.cithub.backend.Tools.service.ReductionService;
import com.gist.cithub.backend.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ReductionService")
public class ReductionServiceImpl extends ServiceImpl<ReductionDao, ReductionEntity> implements ReductionService {




    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<ReductionEntity> listReductionByTestSuitesID(Integer testsuitesID) {
        QueryWrapper<ReductionEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("TestSuitesID", testsuitesID);
        return list(wrapper);
    }
}
