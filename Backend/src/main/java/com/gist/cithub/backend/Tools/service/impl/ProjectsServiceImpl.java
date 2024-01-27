package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.Query;
import com.gist.cithub.backend.Tools.dao.ProjectsDao;
import com.gist.cithub.backend.Tools.entity.ProjectsEntity;
import com.gist.cithub.backend.Tools.service.ProjectsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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