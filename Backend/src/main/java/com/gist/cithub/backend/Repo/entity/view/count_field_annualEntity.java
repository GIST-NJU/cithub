package com.gist.cithub.backend.Repo.entity.view;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("count_field_annual")
public class count_field_annualEntity implements Serializable {

    @TableId
    private Integer year;

    private String model;
    private String generation;
    private String optimization;
    private String diagnosis;
    private String evaluation;
    private String application;
    private String other;

}
