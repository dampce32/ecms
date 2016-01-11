package com.csit.model;

// default package

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
 * StadiumStudent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_StadiumStudent")
public class StadiumStudent extends BaseModel {

	private static final long serialVersionUID = 2149459952066264981L;
	// Fields

	private Integer stadiumStudentId;
	private Student student;
	private Stadium stadium;

	// Constructors

	/** default constructor */
	public StadiumStudent() {
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stadiumStudentId", unique = true, nullable = false)
	public Integer getStadiumStudentId() {
		return stadiumStudentId;
	}


	public void setStadiumStudentId(Integer stadiumStudentId) {
		this.stadiumStudentId = stadiumStudentId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentId", nullable = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stadiumId", nullable = false)
	public Stadium getStadium() {
		return this.stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

}