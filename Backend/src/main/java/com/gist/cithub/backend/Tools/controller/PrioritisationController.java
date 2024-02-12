package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import com.gist.cithub.backend.Tools.entity.PrioritisationEntity;
import com.gist.cithub.backend.Tools.service.EvaluationService;
import com.gist.cithub.backend.Tools.service.PrioritisationService;
import com.gist.cithub.backend.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("tools/prioritisation")
public class PrioritisationController {

    @Autowired
    private PrioritisationService prioritisationService;

    @RequestMapping("/listPrioritisationByTestSuitesID")
    public R listPrioritisationByTestSuitesID(@RequestBody Map<String, Object> info) {
        Integer testsuitesID = (Integer) info.get("testsuitesid");
        List<PrioritisationEntity> prioritisations = prioritisationService.listPrioritisationByTestSuitesID(testsuitesID);
        return R.ok().put("prioritisations", prioritisations);

    }

    @RequestMapping(value = "/NewPrioritisation", method = RequestMethod.POST)
    public R NewPrioritisation(@RequestBody Map<String, Object> info) {
        PrioritisationEntity prioritisationEntity = new PrioritisationEntity();
        prioritisationEntity.setPrioritisationname((String) info.get("prioritisationname"));
        prioritisationEntity.setPrioritisationdescriptions((String) info.get("prioritisationdescriptions"));
        prioritisationEntity.setTestsuitesid((Integer) info.get("testsuitesid"));
        prioritisationEntity.setPrioritisationcontents((String) info.get("prioritisationdContents"));
        prioritisationEntity.setTime((Integer) info.get("time"));
        prioritisationEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        prioritisationEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        prioritisationEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        Boolean flag = prioritisationService.save(prioritisationEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }

    @RequestMapping(value = "/DeleteByPrioritisationID", method = RequestMethod.POST)
    public R DeleteByTestSuitesID(@RequestBody Map<String, Object> info) {
//        System.out.println(info);
        QueryWrapper<PrioritisationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PrioritisationID", info.get("Prioritisationid"));
        Boolean deleteFlag = prioritisationService.remove(queryWrapper);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }


}
