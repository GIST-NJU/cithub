package com.gist.cithub.backend.Benchmark.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gist.cithub.backend.Benchmark.dao.BenchmarkDao;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkEntity;
import com.gist.cithub.backend.Benchmark.service.BenchmarkService;
import com.gist.cithub.backend.Repo.dao.ListDao;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "benchmark")
public class BenchmarkController {

    @Autowired
    private BenchmarkService benchmarkService;

    @Autowired
    private BenchmarkDao benchmarkDao;

    @Autowired
    private ListDao listDao;

    @RequestMapping(value = "/initBenchMarkHome", method = RequestMethod.POST)
    public R initBenchMarkHome() {
//        查询benchmarkmark set的数量
        QueryWrapper<BenchmarkEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("Distinct benchmarkset");

//        查询约束和无约束模型的数量
        QueryWrapper<BenchmarkEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq(" modeltype", "unconstrained");

        QueryWrapper<BenchmarkEntity> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq(" modeltype", "constrained");

        return R.ok()
                .put("BenchmarkSetCount", benchmarkService.count(queryWrapper1))
                .put("unconstrainedCount", benchmarkService.count(queryWrapper2))
                .put("constrainedCount", benchmarkService.count(queryWrapper3))
                //        查询Benchmark Table的数据
                .put("BenchmarkStats", benchmarkDao.getBenchmarkStats());
    }

    @RequestMapping(value = "/getReferenceTitle", method = RequestMethod.POST)
    public R getReferenceTitle(@RequestBody Map<String, Object> info) {
        String benchmarkset = (String) info.get("benchmarkset");
        QueryWrapper<BenchmarkEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("benchmarkset", benchmarkset);
        queryWrapper.select("Distinct reference"); //
        List<BenchmarkEntity> resultList = benchmarkService.list(queryWrapper);

        // 从结果中提取 reference 字段的值
        List<String> referenceList = resultList.stream()
                .map(BenchmarkEntity::getReference)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(referenceList);

        List<ListEntity> res=listDao.getByTitleList(referenceList);

        return R.ok().put("res", res);
    }


}
