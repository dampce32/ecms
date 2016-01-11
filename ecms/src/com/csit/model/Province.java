package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description: 省
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-28
 * @Author lys
 */
@Entity
@Table(name = "T_Province")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Province extends BaseModel {

	// Fields
	
	private static final long serialVersionUID = -7152655463439476277L;
	/**
	 * 省份Id
	 */
	private Integer provinceId;
	/**
	 * 省份编号
	 */
	private String provinceCode;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 排序
	 */
	private Integer array;
	/**
	 * 城市
	 */
	private Set<City> cities = new HashSet<City>(0);

	// Constructors

	/** default constructor */
	public Province() {
	}

	/** minimal constructor */
	public Province(Integer provinceId, String provinceCode,
			String provinceName, Integer array) {
		this.provinceId = provinceId;
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
		this.array = array;
	}

	/** full constructor */
	public Province(Integer provinceId, String provinceCode,
			String provinceName, Integer array, Set<City> cities) {
		this.provinceId = provinceId;
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
		this.array = array;
		this.cities = cities;
	}
	
	// Property accessors
	
	@Id
	@Column(name = "provinceId", unique = true, nullable = false)
	@GeneratedValue(strategy = IDENTITY)
	public Integer getProvinceId() {
		return this.provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	@Column(name = "provinceCode", nullable = false)
	public String getProvinceCode() {
		return this.provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	@Column(name = "provinceName", nullable = false)
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}
	public void setArray(Integer array) {
		this.array = array;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
	public Set<City> getCities() {
		return this.cities;
	}
	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
}