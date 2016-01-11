package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @Description: 民族
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Nation", uniqueConstraints = @UniqueConstraint(columnNames = "nationName"))
public class Nation extends BaseModel {

	// Fields

	private static final long serialVersionUID = -1733361494515323453L;
	/**
	 * 民族Id
	 */
	private Integer nationId;
	/**
	 * 民族名称
	 */
	private String nationName;
	/**
	 * 排序
	 */
	private Integer array;
	/**
	 * 状态
	 * 0 - 禁用
	 * 1 - 启用
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public Nation() {
	}

	/** minimal constructor */
	public Nation(Integer nationId, String nationName, Integer array) {
		this.nationId = nationId;
		this.nationName = nationName;
		this.array = array;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "nationId", unique = true, nullable = false)
	public Integer getNationId() {
		return this.nationId;
	}

	public void setNationId(Integer nationId) {
		this.nationId = nationId;
	}

	@Column(name = "nationName", unique = true, nullable = false, length = 100)
	public String getNationName() {
		return this.nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}