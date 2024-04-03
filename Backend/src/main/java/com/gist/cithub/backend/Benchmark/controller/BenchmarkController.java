package com.gist.cithub.backend.Benchmark.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gist.cithub.backend.Benchmark.dao.BenchmarkDao;
import com.gist.cithub.backend.Benchmark.dao.BenchmarkResultDao;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkEntity;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkResultEntity;
import com.gist.cithub.backend.Benchmark.service.BenchmarkService;
import com.gist.cithub.backend.Repo.dao.ListDao;
import com.gist.cithub.backend.Repo.entity.ListEntity;
import com.gist.cithub.backend.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "benchmark")
public class BenchmarkController {

    @Autowired
    private BenchmarkService benchmarkService;

    @Autowired
    private BenchmarkDao benchmarkDao;

    @Autowired
    private BenchmarkResultDao benchmarkResultDao;

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

        List<ListEntity> res = listDao.getByTitleList(referenceList);

        return R.ok().put("res", res);
    }

    @RequestMapping(value = "/listBenchmarkModels", method = RequestMethod.POST)
    public R listAllBenchmarkModels(@RequestBody Map<String, Object> info) {
/*
pagenum当前是第几页
pagesize每页有几项
* */
        System.out.println("info是" + info);
        Integer pagenum = (Integer) info.get("pagenum");
        Integer pagesize = (Integer) info.get("pagesize");

//        tempResult_1中的BenchmarkEntity已按照benchmarkset分好组，且组间排序按referenceyear的最大值排排序
        List<BenchmarkEntity> tempResult_1 = benchmarkDao.listAllBenchmarkModels();

//      遍历tempResult_1这个List，将每个元素按照它的benchmarkset key的值进行分组benchmarkset key的值相同的为一组，返回一个List<Map<String,List<BenchmarkEntity>>>
        // 遍历这个List，将每个元素按照它的benchmarkset key的值进行分组
        // benchmarkset key的值相同的为一组，最后返回一个List<Map<String,List<BenchmarkEntity>>>
        LinkedHashMap<String, List<BenchmarkEntity>> groupedMap = new LinkedHashMap<>();
        for (BenchmarkEntity entity : tempResult_1) {
            String key = entity.getBenchmarkset();
            List<BenchmarkEntity> groupList = groupedMap.getOrDefault(key, new ArrayList<>());
            groupList.add(entity);
            groupedMap.put(key, groupList);
        }

        // 对每个分组中的BenchmarkEntity列表按modelname排序 A~Z忽略大小写
        groupedMap.forEach((key, value) -> {
            value.sort(Comparator.comparing(e -> e.getModelname().toLowerCase()));
        });

        // 将分组结果拼接成一个List<BenchmarkEntity>
        List<BenchmarkEntity> finalResult = new ArrayList<>();
        groupedMap.forEach((key, value) -> finalResult.addAll(value));


//        分页查询返回结果
        int total = finalResult.size(); // 总记录数
        // 在dataList的基础上执行分页查询，手动分页
        int fromIndex = (pagenum - 1) * pagesize;

        int toIndex = Math.min(pagenum * pagesize, total);

        List<BenchmarkEntity> pageDataList = finalResult.subList(fromIndex, toIndex);
        // 创建一个 Map 对象
        Map<String, Object> resMap = new HashMap<>();

        // 将 Records 数组放入 res 对象中
        resMap.put("records", pageDataList);

        // 向 res 对象中添加 current 键值对
        resMap.put("current", pagenum);


        return R.ok().put("res", resMap).put("total", total);

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public R search(@RequestBody Map<String, Object> searchinfo) {
        Integer pagenum = (Integer) searchinfo.get("pagenum");
        Integer pagesize = (Integer) searchinfo.get("pagesize");
        String searchkeywords = (String) searchinfo.get("searchkeywords");
        String column = (String) searchinfo.get("column");
        QueryWrapper<BenchmarkEntity> queryWrapper = new QueryWrapper<>();
        if (column.equals("benchmarkset") || column.equals("modelid")) {
            System.out.println("精准搜索");
            queryWrapper.eq(column, searchkeywords);
        } else {
            System.out.println("模糊搜索");
            queryWrapper.like("modelname", searchkeywords).or().like("benchmarkset", searchkeywords).or().like("reference", searchkeywords).or().like("referenceyear", searchkeywords).or().like("modeltype", searchkeywords).or().like("field", searchkeywords);
        }
        queryWrapper.orderByDesc("referenceyear");
        Page<BenchmarkEntity> selectPage = benchmarkDao.selectPage(new Page<>(pagenum, pagesize), queryWrapper);
        return R.ok().put("res", selectPage);
    }

    @RequestMapping(value = "/getModelResult", method = RequestMethod.POST)
    public R getModelResult(@RequestBody Map<String, Object> searchinfo) {

        String modelname = (String) searchinfo.get("modelname");
        Integer strength = (Integer) searchinfo.get("strength");
        QueryWrapper<BenchmarkResultEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("modelname",modelname);
        queryWrapper.eq("strength",strength);

        return R.ok().put("res",benchmarkResultDao.selectList(queryWrapper));
    }



}
