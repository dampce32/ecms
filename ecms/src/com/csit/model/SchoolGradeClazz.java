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
 * CompetitionPrize entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_SchoolGradeClazz")
public class SchoolGradeClazz extends BaseModel {

	
	private static final long serialVersionUID = -8142337095649140044L;
	// Fields

	private Integer schoolGradeClazzId;
	private SchoolGrade schoolGrade;
	private Clazz clazz;
	private Integer array;

	// Constructors

	/** default constructor */
	public SchoolGradeClazz() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "schoolGradeClazzId", unique = true, nullable = false)
	public Integer getSchoolGradeClazzId() {
		return schoolGradeClazzId;
	}

	public void setSchoolGradeClazzId(Integer schoolGradeClazzId) {
		this.schoolGradeClazzId = schoolGradeClazzId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolGradeId", nullable = false)
	public SchoolGrade getSchoolGrade() {
		return schoolGrade;
	}

	public void setSchoolGrade(SchoolGrade schoolGrade) {
		this.schoolGrade = schoolGrade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clazzId", nullable = false)
	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	@Column(name = "array")
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

}