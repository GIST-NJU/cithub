package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.Query;
import com.gist.cithub.backend.Tools.dao.TestSuitesDao;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.Tools.service.TestSuitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("TestSuitesService")
public class TestSuitesServiceImpl extends ServiceImpl<TestSuitesDao, TestSuitesEntity> implements TestSuitesService {

    @Autowired
    private  TestSuitesDao testSuitesDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TestSuitesEntity> page = this.page(
                new Query<TestSuitesEntity>().getPage(params),
                new QueryWrapper<TestSuitesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TestSuitesEntity> listTestSuitesByModelID(Integer Modelid) {
        QueryWrapper<TestSuitesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("ModelID", Modelid);
        return list(wrapper);
    }

    @Override
    public List<Map<String, Object>> getCountByModelIds(List<Integer> modelIds) {
        return testSuitesDao.countTestSuitesByModelIds(modelIds);
    }




}