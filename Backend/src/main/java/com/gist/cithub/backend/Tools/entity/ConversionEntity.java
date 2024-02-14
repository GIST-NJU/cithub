package com.gist.cithub.backend.Tools.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("conversion")
public class ConversionEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId
    private Integer conversionid;
    /**
     *
     */
    private String conversioncontents;
    /**
     *
     */
    private String conversiondescriptions;
    private String conversionname;
    private String algorithm;
    /**
     *
     */
    private Integer testsuitesid;

    private Date createdtime;

    private Date lastupdatedtime;

}
