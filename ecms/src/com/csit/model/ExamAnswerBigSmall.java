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
 * @Description:答卷小题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Entity
@Table(name = "T_Exam_AnswerBigSmall")
public class ExamAnswerBigSmall extends BaseModel {

	// Fields

	private static final long serialVersionUID = 8242569238444808919L;
	/**
	 * 答卷小题Id
	 */
	private ExamAnswerBigSmallId id;
	/**
	 * 答卷大题
	 */
	private ExamAnswerBig examAnswerBig;
	/**
	 * 试题号
	 */
	private Integer subjectId;
	/**
	 * 试题描述(RTF/HTML)
	 */
	private String descript;
	/**
	 * 试题描述(Pain)
	 */
	private String descriptPlain;
	/**
	 * 考生答案
	 */
	private String answer;
	/**
	 * 标准答案
	 */
	private String standardAnswer;
	/**
	 * 选项/填空个数
	 */
	private Integer optionCount;
	/**
	 * 选项A/填空1
	 */
	private String option0;
	/**
	 * 选项B/填空2
	 */
	private String option1;
	/**
	 * 选项C/填空3
	 */
	private String option2;
	/**
	 * 选项D/填空4
	 */
	private String option3;
	/**
	 * 选项E/填空5
	 */
	private String option4;
	/**
	 * 选项F/填空6
	 */
	private String option5;
	/**
	 * 选项G/填空7
	 */
	private String option6;
	/**
	 * 选项H/填空8
	 */
	private String option7;
	/**
	 * 选项I/填空9
	 */
	private String option8;
	/**
	 * 选项J/填空10
	 */
	private String option9;
	/**
 	 * 
 	 */
	private String option0array;
	/**
 	 * 
 	 */
	private String option1array;
	/**
 	 * 
 	 */
	private String option2array;
	/**
 	 * 
 	 */
	private String option3array;
	/**
 	 * 
 	 */
	private String option4array;
	/**
 	 * 
 	 */
	private String option5array;
	/**
 	 * 
 	 */
	private String option6array;
	/**
 	 * 
 	 */
	private String option7array;
	/**
 	 * 
 	 */
	private String option8array;
	/**
 	 * 
 	 */
	private String option9array;
	/**
 	 * 
 	 */
	private String standardOption0;
	/**
 	 * 
 	 */
	private String standardOption1;
	/**
 	 * 
 	 */
	private String standardOption2;
	/**
 	 * 
 	 */
	private String standardOption3;
	/**
 	 * 
 	 */
	private String standardOption4;
	/**
 	 * 
 	 */
	private String standardOption5;
	/**
 	 * 
 	 */
	private String standardOption6;
	/**
 	 * 
 	 */
	private String standardOption7;
	/**
 	 * 
 	 */
	private String standardOption8;
	/**
 	 * 
 	 */
	private String standardOption9;
	/**
	 * 小题排序
	 */
	private Integer array;
	/**
	 * 小题得分
	 */
	private Double score;
	/**
	 * 标准分
	 */
	private Double standardScore;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 答题状态 --0：未答 --1：已部分答题 --2：已答题
	 */
	private Integer status;
	/**
	 * 答卷小题的子小题
	 */
	private Set<ExamAnswerBigSmallSub> examAnswerBigSmallSubs = new HashSet<ExamAnswerBigSmallSub>(
			0);

	// Constructors

	/** default constructor */
	public ExamAnswerBigSmall() {
	}

	/** minimal constructor */
	public ExamAnswerBigSmall(ExamAnswerBigSmallId id,
			ExamAnswerBig examAnswerBig, Integer subjectId) {
		this.id = id;
		this.examAnswerBig = examAnswerBig;
		this.subjectId = subjectId;
	}

	/** full constructor */
	public ExamAnswerBigSmall(ExamAnswerBigSmallId id,
			ExamAnswerBig examAnswerBig, Integer subjectId, String descript,
			String descriptPlain, String answer, String standardAnswer,
			Integer optionCount, String option0, String option1,
			String option2, String option3, String option4, String option5,
			String option6, String option7, String option8, String option9,
			String option0array, String option1array, String option2array,
			String option3array, String option4array, String option5array,
			String option6array, String option7array, String option8array,
			String option9array, String standardOption0,
			String standardOption1, String standardOption2,
			String standardOption3, String standardOption4,
			String standardOption5, String standardOption6,
			String standardOption7, String standardOption8,
			String standardOption9, Integer array, Double score,
			Double standardScore, String note,
			Set<ExamAnswerBigSmallSub> examAnswerBigSmallSubs) {
		this.id = id;
		this.examAnswerBig = examAnswerBig;
		this.subjectId = subjectId;
		this.descript = descript;
		this.descriptPlain = descriptPlain;
		this.answer = answer;
		this.standardAnswer = standardAnswer;
		this.optionCount = optionCount;
		this.option0 = option0;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.option5 = option5;
		this.option6 = option6;
		this.option7 = option7;
		this.option8 = option8;
		this.option9 = option9;
		this.option0array = option0array;
		this.option1array = option1array;
		this.option2array = option2array;
		this.option3array = option3array;
		this.option4array = option4array;
		this.option5array = option5array;
		this.option6array = option6array;
		this.option7array = option7array;
		this.option8array = option8array;
		this.option9array = option9array;
		this.standardOption0 = standardOption0;
		this.standardOption1 = standardOption1;
		this.standardOption2 = standardOption2;
		this.standardOption3 = standardOption3;
		this.standardOption4 = standardOption4;
		this.standardOption5 = standardOption5;
		this.standardOption6 = standardOption6;
		this.standardOption7 = standardOption7;
		this.standardOption8 = standardOption8;
		this.standardOption9 = standardOption9;
		this.array = array;
		this.score = score;
		this.standardScore = standardScore;
		this.note = note;
		this.examAnswerBigSmallSubs = examAnswerBigSmallSubs;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)),
			@AttributeOverride(name = "competitionId", column = @Column(name = "competitionId", nullable = false)),
			@AttributeOverride(name = "groupId", column = @Column(name = "groupId", nullable = false)),
			@AttributeOverride(name = "studentId", column = @Column(name = "studentId", nullable = false)),
			@AttributeOverride(name = "smallId", column = @Column(name = "smallId", nullable = false)) })
	public ExamAnswerBigSmallId getId() {
		return this.id;
	}

	public void setId(ExamAnswerBigSmallId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "bigId", referencedColumnName = "bigId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "competitionId", referencedColumnName = "competitionId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "studentId", referencedColumnName = "studentId", nullable = false, insertable = false, updatable = false) })
	public ExamAnswerBig getExamAnswerBig() {
		return this.examAnswerBig;
	}

	public void setExamAnswerBig(ExamAnswerBig examAnswerBig) {
		this.examAnswerBig = examAnswerBig;
	}

	@Column(name = "subjectId", nullable = false)
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "descript")
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "descriptPlain")
	public String getDescriptPlain() {
		return this.descriptPlain;
	}

	public void setDescriptPlain(String descriptPlain) {
		this.descriptPlain = descriptPlain;
	}

	@Column(name = "answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "standardAnswer")
	public String getStandardAnswer() {
		return this.standardAnswer;
	}

	public void setStandardAnswer(String standardAnswer) {
		this.standardAnswer = standardAnswer;
	}

	@Column(name = "optionCount")
	public Integer getOptionCount() {
		return this.optionCount;
	}

	public void setOptionCount(Integer optionCount) {
		this.optionCount = optionCount;
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

	@Column(name = "option4")
	public String getOption4() {
		return this.option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	@Column(name = "option5")
	public String getOption5() {
		return this.option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	@Column(name = "option6")
	public String getOption6() {
		return this.option6;
	}

	public void setOption6(String option6) {
		this.option6 = option6;
	}

	@Column(name = "option7")
	public String getOption7() {
		return this.option7;
	}

	public void setOption7(String option7) {
		this.option7 = option7;
	}

	@Column(name = "option8")
	public String getOption8() {
		return this.option8;
	}

	public void setOption8(String option8) {
		this.option8 = option8;
	}

	@Column(name = "option9")
	public String getOption9() {
		return this.option9;
	}

	public void setOption9(String option9) {
		this.option9 = option9;
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

	@Column(name = "option4Array", length = 36)
	public String getOption4array() {
		return this.option4array;
	}

	public void setOption4array(String option4array) {
		this.option4array = option4array;
	}

	@Column(name = "option5Array", length = 36)
	public String getOption5array() {
		return this.option5array;
	}

	public void setOption5array(String option5array) {
		this.option5array = option5array;
	}

	@Column(name = "option6Array", length = 36)
	public String getOption6array() {
		return this.option6array;
	}

	public void setOption6array(String option6array) {
		this.option6array = option6array;
	}

	@Column(name = "option7Array", length = 36)
	public String getOption7array() {
		return this.option7array;
	}

	public void setOption7array(String option7array) {
		this.option7array = option7array;
	}

	@Column(name = "option8Array", length = 36)
	public String getOption8array() {
		return this.option8array;
	}

	public void setOption8array(String option8array) {
		this.option8array = option8array;
	}

	@Column(name = "option9Array", length = 36)
	public String getOption9array() {
		return this.option9array;
	}

	public void setOption9array(String option9array) {
		this.option9array = option9array;
	}

	@Column(name = "standardOption0")
	public String getStandardOption0() {
		return this.standardOption0;
	}

	public void setStandardOption0(String standardOption0) {
		this.standardOption0 = standardOption0;
	}

	@Column(name = "standardOption1")
	public String getStandardOption1() {
		return this.standardOption1;
	}

	public void setStandardOption1(String standardOption1) {
		this.standardOption1 = standardOption1;
	}

	@Column(name = "standardOption2")
	public String getStandardOption2() {
		return this.standardOption2;
	}

	public void setStandardOption2(String standardOption2) {
		this.standardOption2 = standardOption2;
	}

	@Column(name = "standardOption3")
	public String getStandardOption3() {
		return this.standardOption3;
	}

	public void setStandardOption3(String standardOption3) {
		this.standardOption3 = standardOption3;
	}

	@Column(name = "standardOption4")
	public String getStandardOption4() {
		return this.standardOption4;
	}

	public void setStandardOption4(String standardOption4) {
		this.standardOption4 = standardOption4;
	}

	@Column(name = "standardOption5")
	public String getStandardOption5() {
		return this.standardOption5;
	}

	public void setStandardOption5(String standardOption5) {
		this.standardOption5 = standardOption5;
	}

	@Column(name = "standardOption6")
	public String getStandardOption6() {
		return this.standardOption6;
	}

	public void setStandardOption6(String standardOption6) {
		this.standardOption6 = standardOption6;
	}

	@Column(name = "standardOption7")
	public String getStandardOption7() {
		return this.standardOption7;
	}

	public void setStandardOption7(String standardOption7) {
		this.standardOption7 = standardOption7;
	}

	@Column(name = "standardOption8")
	public String getStandardOption8() {
		return this.standardOption8;
	}

	public void setStandardOption8(String standardOption8) {
		this.standardOption8 = standardOption8;
	}

	@Column(name = "standardOption9")
	public String getStandardOption9() {
		return this.standardOption9;
	}

	public void setStandardOption9(String standardOption9) {
		this.standardOption9 = standardOption9;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examAnswerBigSmall")
	public Set<ExamAnswerBigSmallSub> getExamAnswerBigSmallSubs() {
		return this.examAnswerBigSmallSubs;
	}

	public void setExamAnswerBigSmallSubs(
			Set<ExamAnswerBigSmallSub> examAnswerBigSmallSubs) {
		this.examAnswerBigSmallSubs = examAnswerBigSmallSubs;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}