package com.gist.cithub.backend.Tools.controller;

import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Tools.entity.CoveringarraysEntity;
import com.gist.cithub.backend.Tools.service.CoveringarraysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@RestController
@RequestMapping("tools/coveringarrays")
public class CoveringarraysController {
    @Autowired
    private CoveringarraysService coveringarraysService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = coveringarraysService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listCAsByModelID")
    public R listCAsByModelID(@RequestBody Map<String,Object> info) {
//        System.out.println("listCAsByModelID");
        Integer modelid = (Integer) info.get("modelid");
        List<CoveringarraysEntity> CAlist = coveringarraysService.listCAsByModelID(modelid);
        return R.ok().put("CAs", CAlist);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{coveringarrayid}")
    public R info(@PathVariable("coveringarrayid") Integer coveringarrayid) {
        CoveringarraysEntity coveringarrays = coveringarraysService.getById(coveringarrayid);

        return R.ok().put("coveringarrays", coveringarrays);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CoveringarraysEntity coveringarrays) {
        coveringarraysService.save(coveringarrays);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CoveringarraysEntity coveringarrays) {
        coveringarraysService.updateById(coveringarrays);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] coveringarrayids) {
        coveringarraysService.removeByIds(Arrays.asList(coveringarrayids));

        return R.ok();
    }

}
