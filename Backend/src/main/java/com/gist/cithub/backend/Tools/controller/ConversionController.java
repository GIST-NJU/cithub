package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.ConversionEntity;
import com.gist.cithub.backend.Tools.entity.EvaluationEntity;
import com.gist.cithub.backend.Tools.service.ConversionService;
import com.gist.cithub.backend.Tools.service.EvaluationService;
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
@RequestMapping("tools/conversion")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @RequestMapping("/listConversionByTestSuitesID")
    public R listConversionByTestSuitesID(@RequestBody Map<String, Object> info) {
        Integer testsuitesID = (Integer) info.get("testsuitesid");
        List<ConversionEntity> conversions = conversionService.listConversionByTestSuitesID(testsuitesID);
        return R.ok().put("conversions", conversions);
    }

    @RequestMapping(value = "/NewConversion", method = RequestMethod.POST)
    public R NewConversion(@RequestBody Map<String, Object> info) {
        ConversionEntity conversionEntity = new ConversionEntity();
        conversionEntity.setConversionname((String) info.get("conversionname"));
        conversionEntity.setConversiondescriptions((String) info.get("conversiondescriptions"));
        conversionEntity.setTestsuitesid((Integer) info.get("testsuitesid"));
        conversionEntity.setConversioncontents((String) info.get("conversionContents"));
        conversionEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        conversionEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        conversionEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        Boolean flag = conversionService.save(conversionEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }

    @RequestMapping(value = "/DeleteByConversionID", method = RequestMethod.POST)
    public R DeleteByConversionID(@RequestBody Map<String, Object> info) {
        QueryWrapper<ConversionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ConversionID", info.get("conversionid"));
        Boolean deleteFlag = conversionService.remove(queryWrapper);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }
}
