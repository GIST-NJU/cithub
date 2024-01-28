package com.gist.cithub.backend.Repo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-09-08 21:44:17
 */
@Schema(description = "文献库 数据传输对象")
@Data
@TableName("list")
public class ListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String timeStamp;
	/**
	 * 
	 */
	private Integer year;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String author;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String field;
	/**
	 * 
	 */
	private String tag;
	/**
	 * 
	 */
	private String booktitle;
	/**
	 * 
	 */
	private String abbr;
	/**
	 * 
	 */
	private String vol;
	/**
	 * 
	 */
	private String no;
	/**
	 * 
	 */
	private String pages;
	/**
	 * 
	 */
	private String publisher;
	/**
	 * 
	 */
	private String doi;


	private String paperabstract;

	private String institution;
	/**
	 * 
	 */
	private String projects;

}
