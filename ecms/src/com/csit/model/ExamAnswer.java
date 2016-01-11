package com.csit.model;

import java.sql.Timestamp;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Description: 答卷
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Entity
@Table(name = "T_Exam_Answer")
public class ExamAnswer extends BaseModel {
	// Fields

	private static final long serialVersionUID = -3155857673734630118L;
	/**
	 * 答卷Id
	 */
	private ExamAnswerId id;
	/**
	 * 学生参赛组别
	 */
	private StudentCompetitionGroup studentCompetitionGroup;
	/**
	 * 学生姓名
	 */
    private String studentName;
	/**
	 * 试卷名称
	 */
	private String paperName;
	/**
     * 准考证号
     */
	private String examCode;
	/**
	 * 考试时间（单位：秒）
	 */
	private Integer limits;
	/**
	 * 标准分
	 */
	private Double standardScore;

	/**
	 * 答卷总得分
	 */
	private Double score;
	/**
	 * 开考时间
	 */
	private Timestamp startDateTime;
	/**
	 * 完卷时间
	 */
	private Timestamp finishDateTime;
	/**
	 * 状态
	 * 0 -- 答题
	 * 1 -- 完成答题
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 答卷大题
	 */
	private Set<ExamAnswerBig> examAnswerBigs = new HashSet<ExamAnswerBig>(0);

	// Constructors

	/** default constructor */
	public ExamAnswer() {
	}

	/** minimal constructor */
	public ExamAnswer(ExamAnswerId id, Integer status) {
		this.id = id;
		this.status = status;
	}

	/** full constructor */
	public ExamAnswer(ExamAnswerId id,
			StudentCompetitionGroup studentCompetitionGroup, String paperName,
			Double score, Timestamp startDateTime, Timestamp finishDateTime,
			Integer status, String note, Set<ExamAnswerBig> examAnswerBigs) {
		this.id = id;
		this.studentCompetitionGroup = studentCompetitionGroup;
		this.paperName = paperName;
		this.score = score;
		this.startDateTime = startDateTime;
		this.finishDateTime = finishDateTime;
		this.status = status;
		this.note = note;
		this.examAnswerBigs = examAnswerBigs;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "competitionId", column = @Column(name = "competitionId", nullable = false)),
			@AttributeOverride(name = "groupId", column = @Column(name = "groupId", nullable = false)),
			@AttributeOverride(name = "studentId", column = @Column(name = "studentId", nullable = false)) })
	public ExamAnswerId getId() {
		return this.id;
	}

	public void setId(ExamAnswerId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "studentCompetitionGroupId")
	public StudentCompetitionGroup getStudentCompetitionGroup() {
		return this.studentCompetitionGroup;
	}

	public void setStudentCompetitionGroup(
			StudentCompetitionGroup studentCompetitionGroup) {
		this.studentCompetitionGroup = studentCompetitionGroup;
	}

	@Column(name = "paperName")
	public String getPaperName() {
		return this.paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	@Column(name = "score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "startDateTime", length = 23)
	public Timestamp getStartDateTime() {
		return this.startDateTime;
	}

	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}

	@Column(name = "finishDateTime", length = 23)
	public Timestamp getFinishDateTime() {
		return this.finishDateTime;
	}

	public void setFinishDateTime(Timestamp finishDateTime) {
		this.finishDateTime = finishDateTime;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examAnswer")
	public Set<ExamAnswerBig> getExamAnswerBigs() {
		return this.examAnswerBigs;
	}

	public void setExamAnswerBigs(Set<ExamAnswerBig> examAnswerBigs) {
		this.examAnswerBigs = examAnswerBigs;
	}
	@Column(name = "limits", nullable = false)
	public Integer getLimits() {
		return limits;
	}

	public void setLimits(Integer limits) {
		this.limits = limits;
	}
	@Column(name = "standardScore", precision = 53, scale = 0)
	public Double getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(Double standardScore) {
		this.standardScore = standardScore;
	}
	
	@Column(name="examCode", nullable=false, length=100)
	public String getExamCode() {
		return examCode;
	}
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	@Column(name="studentName", nullable=false, length=50)
    public String getStudentName() {
        return this.studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}