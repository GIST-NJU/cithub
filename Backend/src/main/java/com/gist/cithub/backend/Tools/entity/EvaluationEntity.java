package com.gist.cithub.backend.Tools.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("evaluation")
public class EvaluationEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer evaluationid;
    /**
     *
     */
    private String evaluationcontents;
    /**
     *
     */
    private String evaluationdescriptions;
    private String evaluationname;
    private String algorithm;
    /**
     *
     */
    private Integer testsuitesid;
    private Integer time;

    private Date createdtime;

    private Date lastupdatedtime;

}
