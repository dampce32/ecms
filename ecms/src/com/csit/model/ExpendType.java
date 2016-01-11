package com.csit.model;

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
 * @Description:支出类型
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-9
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_ExpendType")
public class ExpendType extends BaseModel {

	private static final long serialVersionUID = -1074512876066569046L;
	// Fields

	private Integer expendTypeId;
	private String expendTypeName;
	private Integer status;
	private Integer array;
	private Set<Expend> expends = new HashSet<Expend>(0);

	// Constructors

	/** default constructor */
	public ExpendType() {
	}

	/** minimal constructor */
	public ExpendType(Integer expendTypeId, String expendTypeName,
			Integer status, Integer array) {
		this.expendTypeId = expendTypeId;
		this.expendTypeName = expendTypeName;
		this.status = status;
		this.array = array;
	}

	/** full constructor */
	public ExpendType(Integer expendTypeId, String expendTypeName,
			Integer status, Integer array, Set<Expend> expends) {
		this.expendTypeId = expendTypeId;
		this.expendTypeName = expendTypeName;
		this.status = status;
		this.array = array;
		this.expends = expends;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "expendTypeId", unique = true, nullable = false)
	public Integer getExpendTypeId() {
		return this.expendTypeId;
	}

	public void setExpendTypeId(Integer expendTypeId) {
		this.expendTypeId = expendTypeId;
	}

	@Column(name = "expendTypeName", unique = true, nullable = false, length = 100)
	public String getExpendTypeName() {
		return this.expendTypeName;
	}

	public void setExpendTypeName(String expendTypeName) {
		this.expendTypeName = expendTypeName;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expendType")
	public Set<Expend> getExpends() {
		return this.expends;
	}

	public void setExpends(Set<Expend> expends) {
		this.expends = expends;
	}

}