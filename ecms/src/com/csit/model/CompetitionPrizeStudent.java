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
 * @Description: 获奖名单
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-22
 * @Author lys
 */
@Entity
@Table(name = "T_CometitionPrizeStudent")
public class CompetitionPrizeStudent extends BaseModel {

	// Fields

	private static final long serialVersionUID = -1476323023827511592L;
	/**
	 * 获奖名单Id
	 */
	private Integer competitionPrizeStudentId;
	/**
	 * 赛事奖项
	 */
	private CompetitionPrize competitionPrize;
	/**
	 * 获奖学生
	 */
	private Student student;

	// Constructors

	/** default constructor */
	public CompetitionPrizeStudent() {
	}

	/** minimal constructor */
	public CompetitionPrizeStudent(Student student) {
		this.student = student;
	}

	/** full constructor */
	public CompetitionPrizeStudent(CompetitionPrize competitionPrize,
			Student student) {
		this.competitionPrize = competitionPrize;
		this.student = student;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "competitionPrizeStudentId", unique = true, nullable = false)
	public Integer getCompetitionPrizeStudentId() {
		return this.competitionPrizeStudentId;
	}

	public void setCompetitionPrizeStudentId(Integer competitionPrizeStudentId) {
		this.competitionPrizeStudentId = competitionPrizeStudentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionPrizeId")
	public CompetitionPrize getCompetitionPrize() {
		return this.competitionPrize;
	}

	public void setCompetitionPrize(CompetitionPrize competitionPrize) {
		this.competitionPrize = competitionPrize;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentId", nullable = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}