package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description:序列表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-28
 * @Author lys
 */
@Entity
@Table(name = "T_SerialNumber")
public class SerialNumber extends BaseModel {

	// Fields
	private static final long serialVersionUID = 5479243688851194965L;
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 下一位序列值
	 */
	private Integer nextSerial;

	// Constructors

	/** default constructor */
	public SerialNumber() {
	}

	/** minimal constructor */
	public SerialNumber(String tableName) {
		this.tableName = tableName;
	}

	// Property accessors
	@Id
	@Column(name = "TableName", unique = true, nullable = false)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "NextSerial")
	public Integer getNextSerial() {
		return this.nextSerial;
	}

	public void setNextSerial(Integer nextSerial) {
		this.nextSerial = nextSerial;
	}

}