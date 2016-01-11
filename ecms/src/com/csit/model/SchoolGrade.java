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

/**
 * @Description:学校年级
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-7-22
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_SchoolGrade")
public class SchoolGrade extends BaseModel {

	private static final long serialVersionUID = 1419856907280295668L;
	// Fields

	private Integer schoolGradeId;
	private School school;
	private Grade grade;
	private Integer array;
	private Set<SchoolGradeClazz> schoolGradeClazzs = new HashSet<SchoolGradeClazz>(0);

	// Constructors

	/** default constructor */
	public SchoolGrade() {
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "schoolGradeId", unique = true, nullable = false)
	public Integer getSchoolGradeId() {
		return schoolGradeId;
	}
	public void setSchoolGradeId(Integer schoolGradeId) {
		this.schoolGradeId = schoolGradeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolId", nullable = false)
	public School getSchool() {
		return school;
	}


	public void setSchool(School school) {
		this.school = school;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gradeId", nullable = false)
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "schoolGrade")
	public Set<SchoolGradeClazz> getSchoolGradeClazzs() {
		return schoolGradeClazzs;
	}

	public void setSchoolGradeClazzs(Set<SchoolGradeClazz> schoolGradeClazzs) {
		this.schoolGradeClazzs = schoolGradeClazzs;
	}

}