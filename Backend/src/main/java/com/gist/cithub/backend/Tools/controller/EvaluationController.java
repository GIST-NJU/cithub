package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.entity.ModelsEntity;
import com.gist.cithub.backend.Tools.service.EvaluationService;
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


@RestController
@RequestMapping("tools/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping("/listEvaluationByTestSuitesID")
    public R listEvaluationByTestSuitesID(@RequestBody Map<String, Object> info) {
//        System.out.println("listCAsByModelID");
        Integer testsuitesID = (Integer) info.get("testsuitesid");
        List<EvaluationEntity> evaluations = evaluationService.listEvaluationByTestSuitesID(testsuitesID);
        return R.ok().put("evaluations", evaluations);

    }

    @RequestMapping(value = "/NewEvaluation", method = RequestMethod.POST)
    public R NewEvaluation(@RequestBody Map<String, Object> info) {
//        System.out.println(info);
        EvaluationEntity evaluationEntity = new EvaluationEntity();
        evaluationEntity.setEvaluationname((String) info.get("evaluationname"));
        evaluationEntity.setEvaluationdescriptions((String) info.get("evaluationdescriptions"));
        evaluationEntity.setTestsuitesid((Integer) info.get("testsuitesid"));
        evaluationEntity.setEvaluationcontents((String) info.get("EvaluationContents"));
        evaluationEntity.setTime((Integer) info.get("time"));
        evaluationEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        evaluationEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        evaluationEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        Boolean flag = evaluationService.save(evaluationEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }

    @RequestMapping(value = "/DeleteByEvaluationID", method = RequestMethod.POST)
    public R DeleteByTestSuitesID(@RequestBody Map<String, Object> info) {
//        System.out.println(info);
        QueryWrapper<EvaluationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("EvaluationID", info.get("evaluationid"));
        Boolean deleteFlag = evaluationService.remove(queryWrapper);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }


}
