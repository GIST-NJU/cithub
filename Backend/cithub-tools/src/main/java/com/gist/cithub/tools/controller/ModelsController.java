package com.gist.cithub.tools.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.gist.cithub.common.utils.PageUtils;
import com.gist.cithub.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gist.cithub.tools.entity.ModelsEntity;
import com.gist.cithub.tools.service.ModelsService;


/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@RestController
@RequestMapping("tools/models")
public class ModelsController {
    @Autowired
    private ModelsService modelsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = modelsService.queryPage(params);

        return R.ok().put("page", page);
    }



    @RequestMapping("/listModelsByProjectID")
    public R listModelsByProjectID(@RequestBody Map<String,Object> info) {
        Integer projectid = Integer.parseInt((String)info.get("projectid"));
        List<ModelsEntity> modelsList = modelsService.listModelsByProjectId(projectid);
//        System.out.println(modelsList);
        return R.ok().put("models", modelsList);
    }

    @RequestMapping("/updateModel")
    public R updatePaper(@RequestBody ModelsEntity modelsEntity) {
//        System.out.println(listEntity.toString());
        Boolean updateFlag = modelsService.updateById(modelsEntity);
        if (updateFlag) return R.ok().put("UpdateStatus", "success!").put("UpdateResult", modelsEntity);
        else return R.ok().put("UpdateStatus", "failed!");

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{modelid}")
    public R info(@PathVariable("modelid") Integer modelid) {
        ModelsEntity models = modelsService.getById(modelid);

        return R.ok().put("models", models);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ModelsEntity models) {
        modelsService.save(models);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ModelsEntity models) {
        modelsService.updateById(models);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] modelids) {
        modelsService.removeByIds(Arrays.asList(modelids));

        return R.ok();
    }

}
