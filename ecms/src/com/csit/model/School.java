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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description:学校
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_School")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class School extends BaseModel {

	private static final long serialVersionUID = -6829645587398181189L;
	// Fields
	/**
	 * 学校Id
	 */
	private Integer schoolId;
	/**
	 * 学校名称
	 */
	private String schoolName;
	/**
	 * 学校编号
	 */
	private String schoolCode;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 所属区
	 */
	private Area area;
	
	private Set<SchoolGrade> schoolGrades = new HashSet<SchoolGrade>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "schoolId", unique = true, nullable = false)
	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name = "schoolName", nullable = false, length = 50)
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Column(name = "schoolCode", nullable = false, length = 50)
	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId", nullable = false)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "school")
	public Set<SchoolGrade> getSchoolGrades() {
		return schoolGrades;
	}

	public void setSchoolGrades(Set<SchoolGrade> schoolGrades) {
		this.schoolGrades = schoolGrades;
	}

}