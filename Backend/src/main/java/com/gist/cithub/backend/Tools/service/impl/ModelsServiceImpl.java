package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.Query;
import com.gist.cithub.backend.Tools.dao.ModelsDao;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.ModelsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("modelsService")
public class ModelsServiceImpl extends ServiceImpl<ModelsDao, ModelsEntity> implements ModelsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ModelsEntity> page = this.page(
                new Query<ModelsEntity>().getPage(params),
                new QueryWrapper<ModelsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ModelsEntity> listModelsByProjectId(Integer projectId) {
        QueryWrapper<ModelsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("ProjectID", projectId);
        return list(wrapper);
    }

}