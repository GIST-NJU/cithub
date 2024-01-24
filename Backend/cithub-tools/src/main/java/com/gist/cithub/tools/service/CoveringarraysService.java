package com.gist.cithub.tools.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.tools.entity.CoveringarraysEntity;
import com.gist.cithub.tools.entity.ModelsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
public interface CoveringarraysService extends IService<CoveringarraysEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<CoveringarraysEntity> listCAsByModelID(Integer Modelid);

}

