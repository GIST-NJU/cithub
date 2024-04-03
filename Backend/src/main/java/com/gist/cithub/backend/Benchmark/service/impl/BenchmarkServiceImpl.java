package com.gist.cithub.backend.Benchmark.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gist.cithub.backend.Benchmark.dao.BenchmarkDao;
import com.gist.cithub.backend.Benchmark.entity.BenchmarkEntity;
import com.gist.cithub.backend.Benchmark.service.BenchmarkService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BenchmarkService")
public class BenchmarkServiceImpl  extends ServiceImpl<BenchmarkDao, BenchmarkEntity> implements BenchmarkService {

    @Autowired
    private BenchmarkDao benchmarkDao;
}
