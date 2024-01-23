package com.gist.cithub.tools.service.impl;

import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.Query;
import com.gist.cithub.tools.entity.ModelsEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gist.cithub.tools.dao.ProjectsDao;
import com.gist.cithub.tools.entity.ProjectsEntity;
import com.gist.cithub.tools.service.ProjectsService;


@Service("projectsService")
public class ProjectsServiceImpl extends ServiceImpl<ProjectsDao, ProjectsEntity> implements ProjectsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProjectsEntity> page = this.page(
                new Query<ProjectsEntity>().getPage(params),
                new QueryWrapper<ProjectsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProjectsEntity> listProjectByUserID(Integer userID) {
        QueryWrapper<ProjectsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("userID", userID);
        return list(wrapper);
    }

}