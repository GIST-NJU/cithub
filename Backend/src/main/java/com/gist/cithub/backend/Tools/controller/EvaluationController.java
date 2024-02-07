package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
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


}
