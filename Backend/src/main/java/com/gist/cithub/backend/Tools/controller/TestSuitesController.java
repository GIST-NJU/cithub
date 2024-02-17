package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.Tools.service.TestSuitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@RestController
@RequestMapping("tools/testSuites")
public class TestSuitesController {
    @Autowired
    private TestSuitesService testSuitesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = testSuitesService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listTestSuitesByModelID")
    public R listTestSuitesByModelID(@RequestBody Map<String, Object> info) {
        Object modelid = info.get("modelid");
        if (modelid instanceof String) {
            List<TestSuitesEntity> TestSuites = testSuitesService.listTestSuitesByModelID((Integer.parseInt((String) info.get("modelid"))));
            return R.ok().put("TestSuites", TestSuites);
        } else if (modelid instanceof Integer) {
            List<TestSuitesEntity> TestSuites = testSuitesService.listTestSuitesByModelID((Integer) info.get("modelid"));
            return R.ok().put("TestSuites", TestSuites);
        }
        return R.ok().put("TestSuites","failed");


    }

    @RequestMapping(value = "/NewTestSuites", method = RequestMethod.POST)
    public R NewTestSuites(@RequestBody Map<String, Object> info) {
//        System.out.println(info);
        TestSuitesEntity testSuitesEntity = new TestSuitesEntity();
        testSuitesEntity.setTestsuitesname((String) info.get("testsuitesname"));
        testSuitesEntity.setTestsuitesdescriptions((String) info.get("testsuitesdescriptions"));
        testSuitesEntity.setModelid(Integer.parseInt((String) info.get("modelid")));
        testSuitesEntity.setTestsuitescontents((String) info.get("testsuitescontents"));
        testSuitesEntity.setTime((Integer) info.get("time"));
        testSuitesEntity.setSize((Integer) info.get("size"));
        testSuitesEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        testSuitesEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        testSuitesEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        Boolean flag = testSuitesService.save(testSuitesEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }

    @RequestMapping(value = "/reduction/NewTestSuites", method = RequestMethod.POST)
    public R ReductionNewTestSuites(@RequestBody Map<String, Object> info) {
//        System.out.println(info);
        TestSuitesEntity testSuitesEntity = new TestSuitesEntity();
        testSuitesEntity.setTestsuitesname((String) info.get("testsuitesname"));
        testSuitesEntity.setTestsuitesdescriptions((String) info.get("testsuitesdescriptions"));
        testSuitesEntity.setModelid(Integer.parseInt((String) info.get("modelid")));
        testSuitesEntity.setTestsuitescontents((String) info.get("testsuitescontents"));
        testSuitesEntity.setTime((Integer) info.get("time"));
        testSuitesEntity.setSize((Integer) info.get("size"));
        testSuitesEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        testSuitesEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        testSuitesEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        Boolean flag = testSuitesService.save(testSuitesEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }


    @RequestMapping(value = "/DeleteByTestSuitesID", method = RequestMethod.POST)
    public R DeleteByTestSuitesID(@RequestBody Map<String, Object> info) {
        System.out.println(info);
        QueryWrapper<TestSuitesEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TestSuitesID", info.get("testsuitesid"));
        Boolean deleteFlag = testSuitesService.remove(queryWrapper);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{coveringarrayid}")
    public R info(@PathVariable("coveringarrayid") Integer coveringarrayid) {
        TestSuitesEntity coveringarrays = testSuitesService.getById(coveringarrayid);

        return R.ok().put("coveringarrays", coveringarrays);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TestSuitesEntity coveringarrays) {
        testSuitesService.save(coveringarrays);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TestSuitesEntity coveringarrays) {
        testSuitesService.updateById(coveringarrays);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] coveringarrayids) {
        testSuitesService.removeByIds(Arrays.asList(coveringarrayids));

        return R.ok();
    }

}
