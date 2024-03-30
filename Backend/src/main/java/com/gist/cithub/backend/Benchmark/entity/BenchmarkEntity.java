package com.gist.cithub.backend.Benchmark.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("benchmark")
public class BenchmarkEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer modelid;

    private String raw;

    private String valid;

    private String proportion;

    private Integer constrainedparameter;

    private Integer cons;

    private Integer maximumvalue;

    private Integer parameter;

    private String field;

    private Integer referenceyear;

    private String reference;

    private String modeldescription;

    private String benchmarkset;

    private String modeltype;

    private String modelname;



}
