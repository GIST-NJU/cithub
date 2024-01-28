package com.gist.cithub.backend.Tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.Query;
import com.gist.cithub.backend.Tools.dao.CoveringarraysDao;
import com.gist.cithub.backend.Tools.entity.CoveringarraysEntity;
import com.gist.cithub.backend.Tools.service.CoveringarraysService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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