package com.gist.cithub.backend.Benchmark.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("benchmark_result")
public class BenchmarkResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer resultid;

    private String status;

    private String success;
    private String timebest;
    private String sizebest;
    private String timeave;
    private String sizeave;
    private String tool;
    private String modelname;
    private Integer strength;





}
