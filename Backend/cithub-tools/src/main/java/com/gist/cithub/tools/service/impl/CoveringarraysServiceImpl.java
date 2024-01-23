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


import com.gist.cithub.tools.dao.CoveringarraysDao;
import com.gist.cithub.tools.entity.CoveringarraysEntity;
import com.gist.cithub.tools.service.CoveringarraysService;


@Service("coveringarraysService")
public class CoveringarraysServiceImpl extends ServiceImpl<CoveringarraysDao, CoveringarraysEntity> implements CoveringarraysService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CoveringarraysEntity> page = this.page(
                new Query<CoveringarraysEntity>().getPage(params),
                new QueryWrapper<CoveringarraysEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CoveringarraysEntity> listCAsByModelID(Integer Modelid) {
        QueryWrapper<CoveringarraysEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("ModelID", Modelid);
        return list(wrapper);
    }

}