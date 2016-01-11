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
 * @Description: 班级
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Clazz", uniqueConstraints = @UniqueConstraint(columnNames = "clazzName"))
public class Clazz extends BaseModel {

	private static final long serialVersionUID = 9078475036385038401L;

	/**
	 * 班级Id
	 */
	private Integer clazzId;
	/**
	 * 班级名称
	 */
	private String clazzName;
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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "clazzId", unique = true, nullable = false)
	public Integer getClazzId() {
		return clazzId;
	}
	public void setClazzId(Integer clazzId) {
		this.clazzId = clazzId;
	}
	
	@Column(name="clazzName", unique = true, nullable = false, length = 50)
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	
	@Column(name="array", nullable = false)
	public Integer getArray() {
		return array;
	}
	public void setArray(Integer array) {
		this.array = array;
	}
	
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}