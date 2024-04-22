package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.ModelsService;
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

    @Autowired
    private ModelsService modelsService;

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
        return R.ok().put("TestSuites", "failed");


    }

    @RequestMapping(value = "/NewTestSuites", method = RequestMethod.POST)
    public R NewTestSuites(@RequestBody Map<String, Object> info) {

        QueryWrapper<ModelsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ModelID", info.get("modelid"));
        ModelsEntity modelsEntity = modelsService.getOne(queryWrapper);
        Object strength = info.get("strength");

//        if (strength != null) {
//            if (strength instanceof Integer) {
//                modelsEntity.setStrength((Integer) strength);
//            } else if (strength instanceof String) {
//                modelsEntity.setStrength(Integer.parseInt((String) strength));
//            }
//            modelsService.updateById(modelsEntity);
//        }


        TestSuitesEntity testSuitesEntity = new TestSuitesEntity();

        Object testsuitesname = info.get("testsuitesname");
        if (testsuitesname != null) {
            testSuitesEntity.setTestsuitesname((String) testsuitesname);
        }

        Object testsuitesdescriptions = info.get("testsuitesdescriptions");
        if (testsuitesdescriptions != null) {
            testSuitesEntity.setTestsuitesdescriptions((String) testsuitesdescriptions);
        }

        Object modelid = info.get("modelid");
        if (modelid != null) {
            testSuitesEntity.setModelid(Integer.parseInt(modelid.toString()));
        }

        Object testsuitescontents = info.get("testsuitescontents");
        if (testsuitescontents != null) {
            testSuitesEntity.setTestsuitescontents((String) testsuitescontents);
        }

        Object time = info.get("time");
        if (time != null) {
            testSuitesEntity.setTime((Integer) time);
        }

        Object size = info.get("size");
        if (size != null) {
            testSuitesEntity.setSize((Integer) size);
        }

        Object algorithm = info.get("algorithm");
        if (algorithm != null) {
            testSuitesEntity.setAlgorithm((String) algorithm );
        }

        //        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        Object createdtime = info.get("createdtime");
        if (createdtime != null) {
            LocalDateTime createTime = LocalDateTime.parse((String) createdtime , formatter);
            testSuitesEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));

        }

        Object lastupdatedtime = info.get("lastupdatedtime");
        if (lastupdatedtime != null) {
            LocalDateTime lastUpdateTime = LocalDateTime.parse((String) lastupdatedtime, formatter);
            testSuitesEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        }

        // Check if TestSuitesEntity exists for the given modelid
        QueryWrapper<TestSuitesEntity> queryWrapperTestSuite = new QueryWrapper<>();
        queryWrapperTestSuite.eq("ModelID", info.get("modelid"));
        TestSuitesEntity existingTestSuites = testSuitesService.getOne(queryWrapperTestSuite);

        if (existingTestSuites != null) {
            // If exists, update the existing TestSuitesEntity
            testSuitesEntity.setTestsuitesid(existingTestSuites.getTestsuitesid());
            testSuitesService.updateById(testSuitesEntity);
        } else {
            // If doesn't exist, save a new TestSuitesEntity
            Boolean flag = testSuitesService.save(testSuitesEntity);
            if (!flag) {
                return R.ok().put("NewStatus", "failed!");
            }
        }

        return R.ok().put("NewStatus", "success!");
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
