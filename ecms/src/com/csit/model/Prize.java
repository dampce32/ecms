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
 * @Description: 奖项
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @Author wxy
 */

@Entity
@Table(name = "T_Prize")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Prize extends BaseModel {
	
	// Fields
	
	private static final long serialVersionUID = -2935689076426042510L;
	/**
	 * 奖项编号
	 */
	private Integer prizeId;
	/**
	 * 奖项名称
	 */
	private String prizeName;
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
	
	// Property accessors
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prizeId", unique = true, nullable = false)
	public Integer getPrizeId() {
		return this.prizeId;
	}
	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}
	@Column(name = "prizeName",unique = true, nullable = false)
	public String getPrizeName() {
		return this.prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}
	public void setArray(Integer array) {
		this.array = array;
	}
	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}