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
 * @Description: 年级
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author yk
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Grade", uniqueConstraints = @UniqueConstraint(columnNames = "gradeName"))
public class Grade extends BaseModel {

	private static final long serialVersionUID = 9078475036385038401L;

	/**
	 * 年级Id
	 */
	private Integer gradeId;
	/**
	 * 年级名称
	 */
	private String gradeName;
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
	@Column(name = "gradeId", unique = true, nullable = false)
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	
	@Column(name="gradeName", unique = true, nullable = false, length = 50)
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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