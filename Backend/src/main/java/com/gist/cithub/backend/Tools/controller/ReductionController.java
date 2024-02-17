package com.gist.cithub.backend.Tools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Tools.entity.ReductionEntity;
import com.gist.cithub.backend.Tools.entity.TestSuitesEntity;
import com.gist.cithub.backend.Tools.service.ReductionService;
import com.gist.cithub.backend.Tools.service.TestSuitesService;
import com.gist.cithub.backend.common.utils.PageUtils;
import com.gist.cithub.backend.common.utils.R;
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
@RequestMapping("tools/reduction")
public class ReductionController {
    @Autowired
    private ReductionService reductionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = reductionService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listReductionByTestSuitesID")
    public R listReductionByTestSuitesID(@RequestBody Map<String, Object> info) {
        Integer testsuitesid = (Integer) info.get("testsuitesid");
        List<ReductionEntity> Reductions = reductionService.listReductionByTestSuitesID(testsuitesid);
        return R.ok().put("Reductions", Reductions);
    }

    @RequestMapping(value = "/NewReduction", method = RequestMethod.POST)
    public R NewReduction(@RequestBody Map<String, Object> info) {
        ReductionEntity reductionEntity = new ReductionEntity();
        reductionEntity.setReductionname((String) info.get("Reductionname"));
        reductionEntity.setReductiondescriptions((String) info.get("Reductiondescriptions"));
        reductionEntity.setTestsuitesid((Integer) info.get("testsuitesid"));
        reductionEntity.setReductioncontents((String) info.get("ReductiondContents"));
        reductionEntity.setTime((Integer) info.get("time"));
        reductionEntity.setSize((Integer) info.get("size"));
        reductionEntity.setAlgorithm((String) info.get("algorithm"));
//        将时间戳转为Date类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime createTime = LocalDateTime.parse((String) info.get("createdtime"), formatter);
        LocalDateTime lastUpdateTime = LocalDateTime.parse((String) info.get("lastupdatedtime"), formatter);

        reductionEntity.setCreatedtime(java.sql.Timestamp.valueOf(createTime));
        reductionEntity.setLastupdatedtime(java.sql.Timestamp.valueOf(lastUpdateTime));
        Boolean flag = reductionService.save(reductionEntity);
        if (flag) return R.ok().put("NewStatus", "success!");
        else return R.ok().put("NewStatus", "failed!");
    }



    @RequestMapping(value = "/DeleteByReductionID", method = RequestMethod.POST)
    public R DeleteByReductionID(@RequestBody Map<String, Object> info) {
//        System.out.println(info);
        QueryWrapper<ReductionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ReductionID", info.get("reductionid"));
        Boolean deleteFlag = reductionService.remove(queryWrapper);
        if (deleteFlag) return R.ok().put("DeleteStatus", "success!");
        else return R.ok().put("DeleteStatus", "failed!");
    }



}
