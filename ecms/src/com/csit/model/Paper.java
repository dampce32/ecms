package com.csit.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Description:试卷模板
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Entity
@Table(name = "T_Paper")
public class Paper extends BaseModel {

	// Fields
	private static final long serialVersionUID = -6156085828482481387L;
	/**
	 * 试卷编号
	 */
	private Integer paperId;
	/**
	 * 参赛组别
	 */
	private Group group;
	/**
	 * 试卷类型
	 */
	private String paperType;
	/**
	 * 试卷名称
	 */
	private String paperName;
	/**
	 * 考试时间（单位：秒）
	 */
	private Integer limits;
	/**
	 * 是否大题打乱（默认不打乱）
	 */
	private Boolean isBigMix;
	/**
 	 * 是否小题连续
 	 */
	private Boolean isSmallContinue;
	/**
	 * 试卷总分
	 */
	private Double score;
	/**
	 * 出卷日期
	 */
	private Timestamp publishDate;
	/**
	 * 状态
	 */
	private Boolean state;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 出卷教师
	 */
	private Teacher publishTeacher;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
	/**
	 * 最后操作时间
	 */
	private Timestamp operateTime;
	/**
 	 * 
 	 */
	private Set<PaperBig> paperBigs = new HashSet<PaperBig>(0);

	// Constructors

	/** default constructor */
	public Paper() {
	}

	// Property accessors
	@Id
	@Column(name = "paperId", unique = true, nullable = false)
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	@Column(name = "paperType", nullable = false)
	public String getPaperType() {
		return this.paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	@Column(name = "paperName", nullable = false)
	public String getPaperName() {
		return this.paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	@Column(name = "limits", nullable = false)
	public Integer getLimits() {
		return this.limits;
	}

	public void setLimits(Integer limits) {
		this.limits = limits;
	}

	@Column(name = "isBigMix", nullable = false)
	public Boolean getIsBigMix() {
		return this.isBigMix;
	}

	public void setIsBigMix(Boolean isBigMix) {
		this.isBigMix = isBigMix;
	}

	@Column(name = "isSmallContinue", nullable = false)
	public Boolean getIsSmallContinue() {
		return this.isSmallContinue;
	}

	public void setIsSmallContinue(Boolean isSmallContinue) {
		this.isSmallContinue = isSmallContinue;
	}

	@Column(name = "score", nullable = false, precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "publishDate", nullable = false, length = 23)
	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}


	@Column(name = "state", nullable = false)
	public Boolean getState() {
		return this.state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "operateTime", nullable = false, length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paper")
	public Set<PaperBig> getPaperBigs() {
		return this.paperBigs;
	}

	public void setPaperBigs(Set<PaperBig> paperBigs) {
		this.paperBigs = paperBigs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId", nullable = false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publishTeacherId", nullable = false)
	public Teacher getPublishTeacher() {
		return publishTeacher;
	}

	public void setPublishTeacher(Teacher publishTeacher) {
		this.publishTeacher = publishTeacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", nullable = false)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}