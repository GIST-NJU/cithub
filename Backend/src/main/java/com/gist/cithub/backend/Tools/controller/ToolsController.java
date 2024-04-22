package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.ProjectsEntity;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.Tools.service.TestSuitesService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("tools/")
public class ToolsController {

    @Autowired
    private ModelsService modelsService;

    @Autowired
    private TestSuitesService testSuitesService;

    @RequestMapping("/listByID")
    public R listBy(@RequestBody Map<String, Object> info) {
        System.out.println(info);
        Object id = info.get("searchkeywords");
        String column = (String) info.get("column");
        if (id instanceof Integer) {
            id = (Integer) id;
        } else if (id instanceof String) {
            // 如果是字符串，尝试将其转换为整数
            id = Integer.parseInt((String) id);
        } else {
            // 处理其他类型的情况，例如记录日志或抛出异常
            System.err.println("Unsupported type for strength: " + id.getClass());
            return R.ok().put("msg", "error");
        }


        switch (column) {
            //        一种是list By UserID 根据USERID获取对应用户所有的Model
            case "userid":
                QueryWrapper<ModelsEntity> queryWrapperModel = new QueryWrapper<>();
                queryWrapperModel.eq("userid", id);
                return R.ok().put("res", modelsService.list(queryWrapperModel));
            case "modelid":
                //        一种是list By ModelID 根据ModelID 获取对应测试模型的信息 和 该测试模型所拥有的测试用例集的信息
                QueryWrapper<TestSuitesEntity> queryWrapperTestSuite = new QueryWrapper<>();
                queryWrapperTestSuite.eq("modelid", id);
                QueryWrapper<ModelsEntity> queryWrapperModelInfo = new QueryWrapper<>();
                queryWrapperModelInfo.eq("modelid", id);
                return R.ok().put("TestSutiesRES", testSuitesService.list(queryWrapperTestSuite)).put("ModelRES", modelsService.getOne(queryWrapperModelInfo));
        }

        return R.ok().put("res", "listFail");

    }


    /*新建模型*/
    @RequestMapping(value = "/NewModel", method = RequestMethod.POST)
    public R NewModel(@RequestBody Map<String, Object> info) {
        System.out.println("info是！");
        System.out.println(info);
        ModelsEntity modelsEntity = new ModelsEntity();
        modelsEntity.setModelname((String) info.get("modelname"));
        modelsEntity.setModeldescriptions((String) info.get("modeldescriptions"));
        modelsEntity.setModeltype((String) info.get("modeltype"));

        Object modelid = info.get("modelid");
        if (modelid instanceof String) {
            modelsEntity.setModelid(Integer.parseInt((String) modelid));
        } else if (modelid instanceof Integer) {
            modelsEntity.setModelid((Integer) modelid);
        }

        Object userid = info.get("userid");
        if (userid instanceof String) {
            modelsEntity.setUserid(Integer.parseInt((String) userid));
        } else if (userid instanceof Integer) {
            modelsEntity.setUserid((Integer) userid);
        }


//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        modelsEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        modelsEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));


        modelsEntity.setParamsvalues((String) info.get("ParametersAndValues"));
        modelsEntity.setCons((String) info.get("Cons"));

        modelsEntity.setApikey((String) info.get("apikey"));
        modelsEntity.setSemantics((String) info.get("semantics"));
        modelsEntity.setSemanticstype((String) info.get("semanticsType"));
        modelsEntity.setLlmmodel((String) info.get("LLMmodel"));
        modelsEntity.setBaseurl((String) info.get("baseUrl"));

        Boolean flag = modelsService.save(modelsEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }

    //    Save
    @RequestMapping(value = "/Save", method = RequestMethod.POST)
    public R Save(@RequestBody Map<String, Object> info) {
        System.out.println(info);

        String column = (String) info.get("column");
        switch (column) {
            // Save Model
            case "model":
                Object id = info.get("modelid");
                // 有modelid 则 存储 Model

                if (id instanceof Integer) {
                    id = (Integer) id;
                } else if (id instanceof String) {
                    // 如果是字符串，尝试将其转换为整数
                    id = Integer.parseInt((String) id);
                } else {
                    // 处理其他类型的情况，例如记录日志或抛出异常
                    System.err.println("Unsupported type for strength: " + id.getClass());
                    return R.ok().put("msg", "error");
                }

                QueryWrapper<ModelsEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ModelID", info.get("modelid"));
                ModelsEntity modelsEntity = modelsService.getOne(queryWrapper);
                modelsEntity.setParamsvalues((String) info.get("ParametersAndValues"));
                modelsEntity.setCons((String) info.get("Cons"));

                modelsEntity.setModelname((String) info.get("modelname"));
                modelsEntity.setModeldescriptions((String) info.get("modeldescriptions"));

                //        将时间戳转为Date类型
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

                modelsEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));

                boolean updateResult = modelsService.updateById(modelsEntity);

                if (updateResult) {
                    return R.ok().put("SaveModelStatus", "success");
                } else {
                    return R.ok().put("SaveModelStatus", "fail");

                }

                // Save TestSuite
            case "testsuite":

                TestSuitesEntity testSuitesEntity = new TestSuitesEntity();
                if (info.containsKey("testsuitesname")) {
                    testSuitesEntity.setTestsuitesname((String) info.get("testsuitesname"));
                }
                if (info.containsKey("testsuitesdescriptions")) {
                    testSuitesEntity.setTestsuitesdescriptions((String) info.get("testsuitesdescriptions"));
                }

                testSuitesEntity.setTestsuitescontents((String) info.get("testsuitescontents"));
                testSuitesEntity.setTime((Integer) info.get("time"));
                testSuitesEntity.setSize((Integer) info.get("size"));
                testSuitesEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
                lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);


                Object strength = info.get("strength");
                if (strength instanceof String) {
                    testSuitesEntity.setStrength(Integer.parseInt((String) strength));
                } else if (strength instanceof Integer) {
                    testSuitesEntity.setStrength((Integer) strength);
                }

                Object modelid = info.get("modelid");
                if (modelid instanceof String) {
                    testSuitesEntity.setModelid(Integer.parseInt((String) modelid));
                } else if (strength instanceof Integer) {
                    testSuitesEntity.setModelid((Integer) modelid);
                }


                Object testsuiteid = info.get("testsuiteid");
                if (testsuiteid instanceof String) {
                    testSuitesEntity.setTestsuitesid(Integer.parseInt((String) testsuiteid));
                } else if (testsuiteid instanceof Integer) {
                    testSuitesEntity.setTestsuitesid((Integer) testsuiteid);
                }


                testSuitesEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
                testSuitesEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
                Boolean flag = testSuitesService.updateById(testSuitesEntity);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");

        }
        return R.ok().put("res", "saveFail");

    }
}
