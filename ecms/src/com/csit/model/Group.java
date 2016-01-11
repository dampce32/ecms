package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * @Description:参赛组别
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Group")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Group extends BaseModel {

	private static final long serialVersionUID = -2337603596052658467L;
	/**
	 * 参赛组别Id
	 */
	private Integer groupId;
	/**
	 * 参赛组别编号
	 */
	private String groupCode;
	/**
	 * 参赛组别名称
	 */
	private String groupName;
	/**
	 * 排序
	 */
	private Integer array;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态
	 */
	private Integer status;


	/** default constructor */
	public Group() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "GroupID", unique = true, nullable = false)
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GroupName", nullable = false)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "Array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@Column(name = "Note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "groupCode", nullable = false)
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}