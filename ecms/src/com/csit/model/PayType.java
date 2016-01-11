package com.csit.model;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Description:缴费类型
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_PayType")
public class PayType extends BaseModel {

	private static final long serialVersionUID = 4641839270961237214L;
	// Fields

	private Integer payTypeId;
	private String payTypeName;
	private Integer status;
	private Integer array;
	private Set<Pay> paies = new HashSet<Pay>(0);

	// Constructors

	/** default constructor */
	public PayType() {
	}

	/** minimal constructor */
	public PayType(Integer payTypeId, String payTypeName, Integer status,
			Integer array) {
		this.payTypeId = payTypeId;
		this.payTypeName = payTypeName;
		this.status = status;
		this.array = array;
	}

	/** full constructor */
	public PayType(Integer payTypeId, String payTypeName, Integer status,
			Integer array, Set<Pay> paies) {
		this.payTypeId = payTypeId;
		this.payTypeName = payTypeName;
		this.status = status;
		this.array = array;
		this.paies = paies;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "payTypeId", unique = true, nullable = false)
	public Integer getPayTypeId() {
		return this.payTypeId;
	}

	public void setPayTypeId(Integer payTypeId) {
		this.payTypeId = payTypeId;
	}

	@Column(name = "payTypeName", unique = true, nullable = false, length = 100)
	public String getPayTypeName() {
		return this.payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "payType")
	public Set<Pay> getPaies() {
		return this.paies;
	}

	public void setPaies(Set<Pay> paies) {
		this.paies = paies;
	}

}