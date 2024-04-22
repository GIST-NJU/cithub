package com.gist.cithub.backend.Tools.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-08-28 17:02:35
 */
@Data
@TableName("testsuites")
public class TestSuitesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer testsuitesid;
    /**
     *
     */
    private String testsuitesname;
    /**
     *
     */
    private String testsuitesdescriptions;
    private String testsuitescontents;
    private String algorithm;
    /**
     *
     */
    private Integer modelid;
    private Integer size;
    private Integer time;
    private Integer strength;

    private Date createdtime;

    private Date lastupdatedtime;

}
