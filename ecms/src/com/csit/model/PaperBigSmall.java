package com.csit.model;

import java.sql.Timestamp;

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
import javax.persistence.UniqueConstraint;

/**
 * @Description:试卷模板小题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Entity
@Table(name = "T_PaperBigSmall", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PaperID", "BigID", "Array" }))
public class PaperBigSmall extends BaseModel {
	private static final long serialVersionUID = -6790890961703871391L;
	// Fields
	/**
	 * 试卷模板小题Id
	 */
	private PaperBigSmallId id;
	/**
	 * 试卷大题
	 */
	private PaperBig paperBig;
	/**
	 * 课程（在答卷类型是统一难度随机生成时使用）
	 */
	private Group group;
	/**
	 * 试题
	 */
	private Subject subject;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
	/**
	 * 难度
	 */
	private String difficulty;
	/**
	 * 小题分值
	 */
	private Double score;
	/**
	 * 是否选项打乱（默认都要打乱）
	 */
	private Boolean isOptionMix;
	/**
	 * 排序
	 */
	private Integer array;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 最后操作时间
	 */
	private Timestamp operateTime;

	// Constructors

	/** default constructor */
	public PaperBigSmall() {
	}

	/** minimal constructor */
	public PaperBigSmall(PaperBigSmallId id, PaperBig paperBig,
			Teacher teacher, Boolean isOptionMix, Integer array,
			Timestamp operateTime) {
		this.id = id;
		this.paperBig = paperBig;
		this.teacher = teacher;
		this.isOptionMix = isOptionMix;
		this.array = array;
		this.operateTime = operateTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "paperId", column = @Column(name = "paperId", nullable = false)),
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)),
			@AttributeOverride(name = "smallId", column = @Column(name = "smallId", nullable = false)) })
	public PaperBigSmallId getId() {
		return this.id;
	}

	public void setId(PaperBigSmallId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "paperId", referencedColumnName = "paperId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "bigId", referencedColumnName = "bigId", nullable = false, insertable = false, updatable = false) })
	public PaperBig getPaperBig() {
		return this.paperBig;
	}

	public void setPaperBig(PaperBig paperBig) {
		this.paperBig = paperBig;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subjectId")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "difficulty", length = 20)
	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Column(name = "score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "isOptionMix", nullable = false)
	public Boolean getIsOptionMix() {
		return this.isOptionMix;
	}

	public void setIsOptionMix(Boolean isOptionMix) {
		this.isOptionMix = isOptionMix;
	}

	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}