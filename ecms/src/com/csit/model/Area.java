package com.csit.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @Description: 区县
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-6
 * @author wxy
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Area")
public class Area extends BaseModel {

	// Fields
	
	private static final long serialVersionUID = 2203452834859401388L;
	/**
	 * 区县Id
	 */
	private Integer areaId;
	/**
	 * 区县编号
	 */
	private String areaCode;
	/**
	 * 区县名称
	 */
	private String areaName;
	/**
	 * 排序
	 */
	private Integer array;
	/**
	 * 省份
	 */
	private Province province;
	/**
	 * 城市
	 */
	private City city;
	
	// Constructors

	/** default constructor */
	public Area() {
	}

	/** full constructor */
	public Area(Integer areaId, Province province, City city, String areaCode,
			String areaName, Integer array) {
		this.areaId = areaId;
		this.province = province;
		this.city = city;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.array = array;
	}

	// Property accessors
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "areaId", unique = true, nullable = false)
	public Integer getAreaId() {
		return this.areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provinceId", nullable = false)
	public Province getProvince() {
		return this.province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityId", nullable = false)
	public City getCity() {
		return this.city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	@Column(name = "areaCode", nullable = false)
	public String getAreaCode() {
		return this.areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	@Column(name = "areaName", nullable = false)
	public String getAreaName() {
		return this.areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}
	public void setArray(Integer array) {
		this.array = array;
	}
}