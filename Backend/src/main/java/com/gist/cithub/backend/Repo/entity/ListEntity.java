package com.gist.cithub.backend.Repo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author xiangjiantu
 * @email 1223564154@qq.com
 * @date 2022-09-08 21:44:17
 */
@Data
@TableName("repository_list")
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
//	private String publisher;
	/**
	 * 
	 */
	private String doi;


	@TableField(value = "abstract")
	private String paperabstract;

//	private String institution;
//	/**
//	 *
//	 */
//	private String projects;

}
