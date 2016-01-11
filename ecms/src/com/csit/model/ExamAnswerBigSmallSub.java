package com.csit.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @Description: 答卷小题子小题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Entity
@Table(name = "T_Exam_AnswerBigSmallSub")
public class ExamAnswerBigSmallSub extends BaseModel {

	// Fields

	private static final long serialVersionUID = 6512134356257190860L;
	/**
	 * 答卷小题子小题Id
	 */
	private ExamAnswerBigSmallSubId id;
	/**
	 * 答卷小题
	 */
	private ExamAnswerBigSmall examAnswerBigSmall;
	/**
	 * 试题描述(RTF/HTML)
	 */
	private String descript;
	/**
	 * 标准答案
	 */
	private String standardAnswer;
	/**
	 * 考生答案
	 */
	private String answer;
	/**
	 * 选项A/填空1(RTF/HTML)
	 */
	private String option0;
	/**
	 * 选项B/填空2(RTF/HTML)
	 */
	private String option1;
	/**
	 * 选项C/填空3(RTF/HTML)
	 */
	private String option2;
	/**
	 * 选项D/填空4(RTF/HTML)
	 */
	private String option3;
	/**
	 * 选项A/填空1(RTF/HTML)排序
	 */
	private String option0array;
	/**
	 * 选项B/填空2(RTF/HTML)排序
	 */
	private String option1array;
	/**
	 * 选项C/填空3(RTF/HTML)排序
	 */
	private String option2array;
	/**
	 * 选项D/填空4(RTF/HTML)排序
	 */
	private String option3array;
	/**
	 * 小题得分
	 */
	private Double score;
	/**
	 * 标准分（从试卷小题表中带过来，以便改卷）
	 */
	private Double standardScore;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 答题状态
	 	--0：未答
		--1：已部分答题
		--2：已答题
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public ExamAnswerBigSmallSub() {
	}

	/** minimal constructor */
	public ExamAnswerBigSmallSub(ExamAnswerBigSmallSubId id,
			ExamAnswerBigSmall examAnswerBigSmall) {
		this.id = id;
		this.examAnswerBigSmall = examAnswerBigSmall;
	}

	/** full constructor */
	public ExamAnswerBigSmallSub(ExamAnswerBigSmallSubId id,
			ExamAnswerBigSmall examAnswerBigSmall, String descript,
			String standardAnswer, String answer, String option0,
			String option1, String option2, String option3,
			String option0array, String option1array, String option2array,
			String option3array, Double score, Double standardScore, String note) {
		this.id = id;
		this.examAnswerBigSmall = examAnswerBigSmall;
		this.descript = descript;
		this.standardAnswer = standardAnswer;
		this.answer = answer;
		this.option0 = option0;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option0array = option0array;
		this.option1array = option1array;
		this.option2array = option2array;
		this.option3array = option3array;
		this.score = score;
		this.standardScore = standardScore;
		this.note = note;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "studentId", column = @Column(name = "studentId", nullable = false)),
			@AttributeOverride(name = "competitionId", column = @Column(name = "competitionId", nullable = false)),
			@AttributeOverride(name = "groupId", column = @Column(name = "groupId", nullable = false)),
			@AttributeOverride(name = "subId", column = @Column(name = "subId", nullable = false)),
			@AttributeOverride(name = "smallId", column = @Column(name = "smallId", nullable = false)),
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)) })
	public ExamAnswerBigSmallSubId getId() {
		return this.id;
	}

	public void setId(ExamAnswerBigSmallSubId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "bigId", referencedColumnName = "bigId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "competitionId", referencedColumnName = "competitionId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "studentId", referencedColumnName = "studentId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "smallId", referencedColumnName = "smallId", nullable = false, insertable = false, updatable = false) })
	public ExamAnswerBigSmall getExamAnswerBigSmall() {
		return this.examAnswerBigSmall;
	}

	public void setExamAnswerBigSmall(ExamAnswerBigSmall examAnswerBigSmall) {
		this.examAnswerBigSmall = examAnswerBigSmall;
	}

	@Column(name = "descript")
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "standardAnswer")
	public String getStandardAnswer() {
		return this.standardAnswer;
	}

	public void setStandardAnswer(String standardAnswer) {
		this.standardAnswer = standardAnswer;
	}

	@Column(name = "answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "option0")
	public String getOption0() {
		return this.option0;
	}

	public void setOption0(String option0) {
		this.option0 = option0;
	}

	@Column(name = "option1")
	public String getOption1() {
		return this.option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	@Column(name = "option2")
	public String getOption2() {
		return this.option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	@Column(name = "option3")
	public String getOption3() {
		return this.option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	@Column(name = "option0Array", length = 36)
	public String getOption0array() {
		return this.option0array;
	}

	public void setOption0array(String option0array) {
		this.option0array = option0array;
	}

	@Column(name = "option1Array", length = 36)
	public String getOption1array() {
		return this.option1array;
	}

	public void setOption1array(String option1array) {
		this.option1array = option1array;
	}

	@Column(name = "option2Array", length = 36)
	public String getOption2array() {
		return this.option2array;
	}

	public void setOption2array(String option2array) {
		this.option2array = option2array;
	}

	@Column(name = "option3Array", length = 36)
	public String getOption3array() {
		return this.option3array;
	}

	public void setOption3array(String option3array) {
		this.option3array = option3array;
	}

	@Column(name = "score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "standardScore", precision = 53, scale = 0)
	public Double getStandardScore() {
		return this.standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}