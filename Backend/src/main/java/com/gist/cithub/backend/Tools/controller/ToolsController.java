package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.Tools.service.TestSuitesService;
import com.gist.cithub.backend.common.utils.R;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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

//                先将该Model 下的所有 TestSuite 置空
                // 查询特定modelid下的所有TestSuitesEntity
                QueryWrapper<TestSuitesEntity> queryWrapperTestSuites = new QueryWrapper<>();
                queryWrapperTestSuites.eq("ModelID", id);
                List<TestSuitesEntity> testSuitesList = testSuitesService.list(queryWrapperTestSuites);

// 对于每个TestSuitesEntity，仅保留name description id 其他信息都置空
                for (TestSuitesEntity testSuitesEntity : testSuitesList) {
                    UpdateWrapper<TestSuitesEntity> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("ModelID", testSuitesEntity.getModelid());
                    updateWrapper.eq("TestSuitesID", testSuitesEntity.getTestsuitesid());
                    updateWrapper.set("TestSuitesName", testSuitesEntity.getTestsuitesname());
                    updateWrapper.set("TestSuitesDescriptions", testSuitesEntity.getTestsuitesdescriptions());
                    updateWrapper.set("TestSuitesContents", null);
                    updateWrapper.set("GenerationTime", null);
                    updateWrapper.set("size", null);
                    updateWrapper.set("LastUpdatedTime", null);
                    updateWrapper.set("CreatedTime", null);
                    updateWrapper.set("GenerationTool", null);
                    updateWrapper.set("strength", null);
                    updateWrapper.set("PrioritisationTool", null);
                    updateWrapper.set("ReductionTool", null);
                    updateWrapper.set("EvaluationTool", null);
                    updateWrapper.set("sizeAfterReduction", null);
                    updateWrapper.set("PrioritisationTime", null);
                    updateWrapper.set("ReductionTime", null);
                    updateWrapper.set("EvaluationTime", null);
                    updateWrapper.set("EvaluationContents", null);
                    updateWrapper.set("DiagnosisTool", null);
                    updateWrapper.set("DiagnosisContents", null);

                    Boolean updateTestSuitesResult = testSuitesService.update(updateWrapper);

                    if (!updateTestSuitesResult) {
                        // 如果更新失败，记录日志或者抛出异常
                        System.err.println("Failed to update TestSuitesEntity with id: " + testSuitesEntity.getTestsuitesid());
                        throw new RuntimeException("Failed to update TestSuitesEntity");
                    }
                }

//                再更新Model本身的信息
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

            case "llmmodel":
                Object llmmodelid = info.get("modelid");
                if (llmmodelid instanceof Integer) {
                    llmmodelid = (Integer) llmmodelid;
                } else if (llmmodelid instanceof String) {
                    // 如果是字符串，尝试将其转换为整数
                    llmmodelid = Integer.parseInt((String) llmmodelid);
                } else {
                    // 处理其他类型的情况，例如记录日志或抛出异常
                    System.err.println("Unsupported type for strength: " + llmmodelid.getClass());
                    return R.ok().put("msg", "error");
                }

//                更新 LLM Model 本身的信息
                QueryWrapper<ModelsEntity> queryWrapperLLMmodel = new QueryWrapper<>();
                queryWrapperLLMmodel.eq("ModelID", info.get("modelid"));
                ModelsEntity llmmodelsEntity = modelsService.getOne(queryWrapperLLMmodel);
                llmmodelsEntity.setParamsvalues((String) info.get("ParametersAndValues"));
                llmmodelsEntity.setCons((String) info.get("Cons"));



                //        将时间戳转为Date类型
                 formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                 lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

                llmmodelsEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));

                updateResult = modelsService.updateById(llmmodelsEntity);

                if (updateResult) {
                    return R.ok().put("SaveModelStatus", "success");
                } else {
                    return R.ok().put("SaveModelStatus", "fail");

                }


                // Save TestSuite
            case "generation":
                UpdateWrapper<TestSuitesEntity> updateWrapper = new UpdateWrapper<>();
                if (info.containsKey("testsuitesname")) {
                    updateWrapper.set("TestSuitesName", (String) info.get("testsuitesname"));
                }
                if (info.containsKey("testsuitesdescriptions")) {
                    updateWrapper.set("TestSuitesDescriptions", (String) info.get("testsuitesdescriptions"));

                }

                updateWrapper.set("TestSuitesContents", (String) info.get("testsuitescontents"));
                updateWrapper.set("GenerationTime", (Integer) info.get("generationtime"));
                updateWrapper.set("size", (Integer) info.get("size"));
                updateWrapper.set("GenerationTool", (String) info.get("generationtool"));

//        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

                if (info.containsKey("createdtime")) {
                    LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
                    updateWrapper.set("CreatedTime", java.sql.Timestamp.valueOf(createTime));

                }

                if (info.containsKey("lastupdatedtime")) {
                    lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);
                    updateWrapper.set("LastUpdatedTime", java.sql.Timestamp.valueOf(lastUpdateTime));

                }


                if (info.containsKey("strength")) {
                    Object strength = info.get("strength");
                    if (strength instanceof String) {
                        updateWrapper.set("strength", Integer.parseInt((String) strength));

                    } else if (strength instanceof Integer) {
                        updateWrapper.set("strength", (Integer) strength);

                    }
                }


                Object modelid = info.get("modelid");
                if (modelid instanceof String) {
                    updateWrapper.eq("ModelID", Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    updateWrapper.eq("ModelID", (Integer) modelid);

                }


                Object testsuiteid = info.get("testsuiteid");
                if (testsuiteid instanceof String) {
                    updateWrapper.eq("TestSuitesID", Integer.parseInt((String) testsuiteid));

                } else if (testsuiteid instanceof Integer) {
                    updateWrapper.eq("TestSuitesID", (Integer) testsuiteid);

                }

//                每次Generation，都要把排序、约减、评估 、 故障定位 的结果置为空

                updateWrapper.set("Prioritisationtool", null);
                updateWrapper.set("PrioritisationTime", null);


                updateWrapper.set("ReductionTool", null);
                updateWrapper.set("ReductionTime", null);
                updateWrapper.set("sizeAfterReduction", null);


                updateWrapper.set("EvaluationTool", null);
                updateWrapper.set("EvaluationContents", null);
                updateWrapper.set("EvaluationTime", null);

                updateWrapper.set("DiagnosisTool", null);
                updateWrapper.set("DiagnosisContents", null);

                Boolean flag = testSuitesService.update(updateWrapper);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");


            case "prioritisation":
                UpdateWrapper<TestSuitesEntity> updateWrapperPrioritisation = new UpdateWrapper<>();

                updateWrapperPrioritisation.set("TestSuitesContents", (String) info.get("testsuitescontents"));
                updateWrapperPrioritisation.set("PrioritisationTime", (Integer) info.get("prioritisationtime"));
                updateWrapperPrioritisation.set("PrioritisationTool", (String) info.get("prioritisationtool"));

//        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");


                if (info.containsKey("lastupdatedtime")) {
                    lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);
                    updateWrapperPrioritisation.set("LastUpdatedTime", java.sql.Timestamp.valueOf(lastUpdateTime));

                }


                modelid = info.get("modelid");
                if (modelid instanceof String) {
                    updateWrapperPrioritisation.eq("ModelID", Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    updateWrapperPrioritisation.eq("ModelID", (Integer) modelid);

                }


                testsuiteid = info.get("testsuiteid");
                if (testsuiteid instanceof String) {
                    updateWrapperPrioritisation.eq("TestSuitesID", Integer.parseInt((String) testsuiteid));

                } else if (testsuiteid instanceof Integer) {
                    updateWrapperPrioritisation.eq("TestSuitesID", (Integer) testsuiteid);

                }

//                每次排序，要把 约减、评估 、故障定位 置空

                updateWrapperPrioritisation.set("ReductionTool", null);
                updateWrapperPrioritisation.set("ReductionTime", null);
                updateWrapperPrioritisation.set("sizeAfterReduction", null);


                updateWrapperPrioritisation.set("EvaluationTool", null);
                updateWrapperPrioritisation.set("EvaluationContents", null);
                updateWrapperPrioritisation.set("EvaluationTime", null);

                updateWrapperPrioritisation.set("DiagnosisTool", null);
                updateWrapperPrioritisation.set("DiagnosisContents", null);

                flag = testSuitesService.update(updateWrapperPrioritisation);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");


            case "reduction":
                UpdateWrapper<TestSuitesEntity> updateWrapperReduction = new UpdateWrapper<>();

                updateWrapperReduction.set("TestSuitesContents", (String) info.get("testsuitescontents"));
                updateWrapperReduction.set("ReductionTime", (Integer) info.get("reductiontime"));
                updateWrapperReduction.set("sizeAfterReduction", (Integer) info.get("sizeafterreduction"));
                updateWrapperReduction.set("ReductionTool", (String) info.get("reductiontool"));

//        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");


                if (info.containsKey("lastupdatedtime")) {
                    lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);
                    updateWrapperReduction.set("LastUpdatedTime", java.sql.Timestamp.valueOf(lastUpdateTime));

                }


                modelid = info.get("modelid");
                if (modelid instanceof String) {
                    updateWrapperReduction.eq("ModelID", Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    updateWrapperReduction.eq("ModelID", (Integer) modelid);

                }


                testsuiteid = info.get("testsuiteid");
                if (testsuiteid instanceof String) {
                    updateWrapperReduction.eq("TestSuitesID", Integer.parseInt((String) testsuiteid));

                } else if (testsuiteid instanceof Integer) {
                    updateWrapperReduction.eq("TestSuitesID", (Integer) testsuiteid);

                }

//                每次Reduction 要把 排序、评估的已有结果置空
                updateWrapperReduction.set("Prioritisationtool", null);
                updateWrapperReduction.set("PrioritisationTime", null);


                updateWrapperReduction.set("EvaluationTool", null);
                updateWrapperReduction.set("EvaluationContents", null);
                updateWrapperReduction.set("EvaluationTime", null);

                updateWrapperReduction.set("DiagnosisTool", null);
                updateWrapperReduction.set("DiagnosisContents", null);

                flag = testSuitesService.update(updateWrapperReduction);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");

            case "evaluation":
                UpdateWrapper<TestSuitesEntity> updateWrapperEvaluation = new UpdateWrapper<>();

                updateWrapperEvaluation.set("EvaluationContents", (String) info.get("EvaluationContents"));
                updateWrapperEvaluation.set("EvaluationTime", (Integer) info.get("evaluationtime"));
                updateWrapperEvaluation.set("EvaluationTool", (String) info.get("evaluationtool"));

//        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");


                if (info.containsKey("lastupdatedtime")) {
                    lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);
                    updateWrapperEvaluation.set("LastUpdatedTime", java.sql.Timestamp.valueOf(lastUpdateTime));

                }


                modelid = info.get("modelid");
                if (modelid instanceof String) {
                    updateWrapperEvaluation.eq("ModelID", Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    updateWrapperEvaluation.eq("ModelID", (Integer) modelid);

                }


                testsuiteid = info.get("testsuiteid");
                if (testsuiteid instanceof String) {
                    updateWrapperEvaluation.eq("TestSuitesID", Integer.parseInt((String) testsuiteid));

                } else if (testsuiteid instanceof Integer) {
                    updateWrapperEvaluation.eq("TestSuitesID", (Integer) testsuiteid);

                }

                //    评估 执行不改变 testsuitecontent 无需置空其他测试活动的字段

                flag = testSuitesService.update(updateWrapperEvaluation);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");

//                故障定位
            case "diagnosis":
                UpdateWrapper<TestSuitesEntity> updateWrapperDiagnosis = new UpdateWrapper<>();

                updateWrapperDiagnosis.set("DiagnosisContents", (String) info.get("diagnosiscontents"));

                updateWrapperDiagnosis.set("DiagnosisTool", (String) info.get("diagnosistool"));

//        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");


                if (info.containsKey("lastupdatedtime")) {
                    lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);
                    updateWrapperDiagnosis.set("LastUpdatedTime", java.sql.Timestamp.valueOf(lastUpdateTime));

                }


                modelid = info.get("modelid");
                if (modelid instanceof String) {
                    updateWrapperDiagnosis.eq("ModelID", Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    updateWrapperDiagnosis.eq("ModelID", (Integer) modelid);

                }


                testsuiteid = info.get("testsuiteid");
                if (testsuiteid instanceof String) {
                    updateWrapperDiagnosis.eq("TestSuitesID", Integer.parseInt((String) testsuiteid));

                } else if (testsuiteid instanceof Integer) {
                    updateWrapperDiagnosis.eq("TestSuitesID", (Integer) testsuiteid);

                }

//                故障定位执行不改变 testsuitecontent 无需置空其他测试活动的字段


                flag = testSuitesService.update(updateWrapperDiagnosis);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");


        }
        return R.ok().put("res", "saveFail");

    }

    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public R Delete(@RequestBody Map<String, Object> info) {
        System.out.println("删除");
        System.out.println(info);
        String column = (String) info.get("column");
        switch (column) {
            // Delete Model
            case "model":
                Object modelid = info.get("modelid");
                QueryWrapper<ModelsEntity> modelsEntityQueryWrapper = new QueryWrapper<>();

                if (modelid instanceof String) {
                    modelsEntityQueryWrapper.eq("modelid", Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    modelsEntityQueryWrapper.eq("modelid", (Integer) modelid);
                }

                Boolean flag = modelsService.remove(modelsEntityQueryWrapper);
                if (flag) return R.ok().put("DeleteStatus", "success!");
                else return R.ok().put("DeleteStatus", "failed!");

            case "testsuite":
                Object testsuitesid = info.get("testsuitesid");
                QueryWrapper<TestSuitesEntity> testSuitesEntityQueryWrapper = new QueryWrapper<>();

                if (testsuitesid instanceof String) {
                    testSuitesEntityQueryWrapper.eq("TestSuitesID", Integer.parseInt((String) testsuitesid));
                } else if (testsuitesid instanceof Integer) {
                    testSuitesEntityQueryWrapper.eq("TestSuitesID", (Integer) testsuitesid);
                }

                flag = testSuitesService.remove(testSuitesEntityQueryWrapper);
                if (flag) return R.ok().put("DeleteStatus", "success!");
                else return R.ok().put("DeleteStatus", "failed!");
        }
        return R.ok();
    }

    @RequestMapping(value = "/New", method = RequestMethod.POST)
    public R New(@RequestBody Map<String, Object> info) {
        System.out.println("新建");
        System.out.println(info);
        String column = (String) info.get("column");
        switch (column) {
            // New Model
            case "model":
                ModelsEntity modelsEntity = new ModelsEntity();
                modelsEntity.setModelname((String) info.get("modelname"));
                modelsEntity.setModeldescriptions((String) info.get("modeldescriptions"));
                modelsEntity.setModeltype((String) info.get("modeltype"));

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



//                New TestSuite
            case "testsuite":
                TestSuitesEntity testSuitesEntity = new TestSuitesEntity();
                testSuitesEntity.setTestsuitesname((String) info.get("testsuitesname"));
                testSuitesEntity.setTestsuitesdescriptions((String) info.get("testsuitesdescriptions"));
                testSuitesEntity.setTestsuitescontents((String) info.get("testsuitescontents"));
                Object generationtime = info.get("generationtime");
                if (generationtime != null) {
                    testSuitesEntity.setGenerationtime((Integer) generationtime);
                }

                Object size = info.get("size");
                if (size != null) {
                    testSuitesEntity.setSize((Integer) size);
                }

                Object generationtool = info.get("generationtool");
                if (generationtool != null) {
                    testSuitesEntity.setGenerationtool((String) generationtool);
                }

                //        将时间戳转为Date类型
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

                Object createdtime = info.get("createdtime");
                if (createdtime != null) {
                    createTime = LocalDateTime.parse((String) createdtime, formatter);
                    testSuitesEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));

                }
                Object lastupdatedtime = info.get("lastupdatedtime");
                if (lastupdatedtime != null) {
                    lastUpdateTime = LocalDateTime.parse((String) lastupdatedtime, formatter);
                    testSuitesEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
                }


                Object strength = info.get("strength");
                if (strength instanceof String) {
                    testSuitesEntity.setStrength(Integer.parseInt((String) strength));

                } else if (strength instanceof Integer) {
                    testSuitesEntity.setStrength((Integer) strength);
                }


                Object modelid = info.get("modelid");
                if (modelid instanceof String) {
                    testSuitesEntity.setModelid(Integer.parseInt((String) modelid));
                } else if (modelid instanceof Integer) {
                    testSuitesEntity.setModelid((Integer) modelid);
                }
                flag = testSuitesService.save(testSuitesEntity);
                if (flag) return R.ok().put("NewStatus", "success!");
                else return R.ok().put("NewStatus", "failed!");


        }
        return R.ok();
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public R Count(@RequestBody Map<String, Object> info) {
        System.out.println("模型id的数组信息");
        System.out.println(info);
        // 从info中提取modelidList
        List<Integer> modelIds = (List<Integer>) info.get("modelidList");
        System.out.println("modelIds");
        System.out.println(modelIds);
        List<Map<String, Object>> results = testSuitesService.getCountByModelIds(modelIds);

        // 对结果进行处理，确保每个modelid都存在统计结果
        for (Integer modelId : modelIds) {
            boolean found = false;
            for (Map<String, Object> result : results) {
                Integer resultModelId = (Integer) result.get("modelid");
                if (modelId.equals(resultModelId)) {
                    found = true;
                    break;
                }
            }
            // 如果不存在统计结果，则手动添加一个统计结果为0的条目
            if (!found) {
                Map<String, Object> zeroCountResult = new HashMap<>();
                zeroCountResult.put("modelid", modelId);
                zeroCountResult.put("count", 0);
                results.add(zeroCountResult);
            }
        }
        return R.ok().put("data", results);
    }


}
