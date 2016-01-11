package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @Description: 城市
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author wxy
 * @vesion 1.0
 */
@Entity
@Table(name = "T_City")
public class City extends BaseModel {

	// Fields

	private static final long serialVersionUID = 1L;
	/**
	 * 城市Id
	 */
	private Integer cityId;
	/**
	 * 城市编号
	 */
	private String cityCode;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 排序
	 */
	private Integer array;
	/**
	 * 省份
	 */
	private Province province;
	/**
	 * 县区
	 */
	private Set<Area> areas = new HashSet<Area>(0);

	// Constructors
	
	/** default constructor */
	public City() {
	}

	/** minimal constructor */
	public City(Integer cityId, Province province, String cityCode,
			String cityName, Integer array) {
		this.cityId = cityId;
		this.province = province;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.array = array;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cityId", unique = true, nullable = false)
	public Integer getCityId() {
		return this.cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provinceId", nullable = false)
	public Province getProvince() {
		return this.province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	@Column(name = "cityCode", nullable = false)
	public String getCityCode() {
		return this.cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Column(name = "cityName", nullable = false)
	public String getCityName() {
		return this.cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}
	public void setArray(Integer array) {
		this.array = array;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
	public Set<Area> getAreas() {
		return areas;
	}
	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
}