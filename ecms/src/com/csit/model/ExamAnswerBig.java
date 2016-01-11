package com.csit.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Description:答卷大题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Entity
@Table(name = "T_Exam_AnswerBig")
public class ExamAnswerBig extends BaseModel {

	private static final long serialVersionUID = -3251034445035543021L;
	// Fields
	/**
	 * 答卷大题Id
	 */
	private ExamAnswerBigId id;
	/**
	 * 答卷
	 */
	private ExamAnswer examAnswer;
	/**
	 * 大题名称
	 */
	private String bigName;
	/**
	 * 题型
	 */
	private String subjectType;
	/**
	 * 试卷大题排序
	 */
	private Integer array;
	/**
	 * 大题标准分
	 */
	private Double standardScore;
	/**
	 * 大题总得分
	 */
	private Double score;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 大题下的小题
	 */
	private Set<ExamAnswerBigSmall> examAnswerBigSmalls = new HashSet<ExamAnswerBigSmall>(0);

	// Constructors

	/** default constructor */
	public ExamAnswerBig() {
	}

	/** minimal constructor */
	public ExamAnswerBig(ExamAnswerBigId id, ExamAnswer examAnswer) {
		this.id = id;
		this.examAnswer = examAnswer;
	}

	/** full constructor */
	public ExamAnswerBig(ExamAnswerBigId id, ExamAnswer examAnswer,
			String bigName, String subjectType, Integer array, Double score,
			String note, Set<ExamAnswerBigSmall> examAnswerBigSmalls) {
		this.id = id;
		this.examAnswer = examAnswer;
		this.bigName = bigName;
		this.subjectType = subjectType;
		this.array = array;
		this.score = score;
		this.note = note;
		this.examAnswerBigSmalls = examAnswerBigSmalls;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)),
			@AttributeOverride(name = "competitionId", column = @Column(name = "competitionId", nullable = false)),
			@AttributeOverride(name = "groupId", column = @Column(name = "groupId", nullable = false)),
			@AttributeOverride(name = "studentId", column = @Column(name = "studentId", nullable = false)) })
	public ExamAnswerBigId getId() {
		return this.id;
	}

	public void setId(ExamAnswerBigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "competitionId", referencedColumnName = "competitionId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "studentId", referencedColumnName = "studentId", nullable = false, insertable = false, updatable = false) })
	public ExamAnswer getExamAnswer() {
		return this.examAnswer;
	}

	public void setExamAnswer(ExamAnswer examAnswer) {
		this.examAnswer = examAnswer;
	}

	@Column(name = "bigName")
	public String getBigName() {
		return this.bigName;
	}

	public void setBigName(String bigName) {
		this.bigName = bigName;
	}

	@Column(name = "subjectType")
	public String getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	@Column(name = "array")
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@Column(name = "score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examAnswerBig")
	public Set<ExamAnswerBigSmall> getExamAnswerBigSmalls() {
		return this.examAnswerBigSmalls;
	}

	public void setExamAnswerBigSmalls(
			Set<ExamAnswerBigSmall> examAnswerBigSmalls) {
		this.examAnswerBigSmalls = examAnswerBigSmalls;
	}
	@Column(name = "standardScore", precision = 53, scale = 0)
	public Double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}

}