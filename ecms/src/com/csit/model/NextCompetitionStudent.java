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
 * @Description:赛事晋级
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-23
 * @Author lys
 */
@Entity
@Table(name = "T_NextCompetitionStudent")
public class NextCompetitionStudent extends BaseModel {

	// Fields
	private static final long serialVersionUID = -8240759166767007230L;
	/**
	 * 赛事晋级名单Id
	 */
	private Integer nextCompetitionStudentId;
	/**
	 * 赛事组别
	 */
	private CompetitionGroup competitionGroup;
	/**
	 * 学生
	 */
	private Student student;
	/**
	 * 晋级分数
	 */
	private Double score;

	// Constructors
	/** default constructor */
	public NextCompetitionStudent() {
	}

	/** minimal constructor */
	public NextCompetitionStudent(Student student) {
		this.student = student;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "nextCompetitionStudentId", unique = true, nullable = false)
	public Integer getNextCompetitionStudentId() {
		return this.nextCompetitionStudentId;
	}

	public void setNextCompetitionStudentId(Integer nextCompetitionStudentId) {
		this.nextCompetitionStudentId = nextCompetitionStudentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionGroupId")
	public CompetitionGroup getCompetitionGroup() {
		return this.competitionGroup;
	}

	public void setCompetitionGroup(CompetitionGroup competitionGroup) {
		this.competitionGroup = competitionGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentId", nullable = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}