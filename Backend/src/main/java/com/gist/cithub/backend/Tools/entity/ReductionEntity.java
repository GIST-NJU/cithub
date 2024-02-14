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
@TableName("reduction")
public class ReductionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer reductionid;
    /**
     *
     */
    private String reductionname;
    /**
     *
     */
    private String reductiondescriptions;
    private String reductioncontents;
    private String algorithm;
    /**
     *
     */
    private Integer testsuitesid;
    private Integer size;
    private Integer time;

    private Date createdtime;

    private Date lastupdatedtime;

}
