package com.gist.cithub.backend.Tools.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("prioritisation")
public class PrioritisationEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer prioritisationid;
    /**
     *
     */
    private String prioritisationcontents;
    /**
     *
     */
    private String prioritisationdescriptions;
    private String prioritisationname;
    private String algorithm;
    /**
     *
     */
    private Integer testsuitesid;
    private Integer time;

    private Date createdtime;

    private Date lastupdatedtime;

}
