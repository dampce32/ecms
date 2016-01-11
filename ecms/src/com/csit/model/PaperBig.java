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
import javax.persistence.UniqueConstraint;

/**
 * @Description:试卷模板大题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Entity
@Table(name = "T_PaperBig", uniqueConstraints = @UniqueConstraint(columnNames = {
		"PaperID", "Array" }))
public class PaperBig extends BaseModel {

	private static final long serialVersionUID = -5701344397088695155L;
	// Fields
	/**
	 * 试卷模板大题Id
	 */
	private PaperBigId id;
	/**
	 * 试卷模板
	 */
	private Paper paper;
	/**
	 * 最后操作员
	 */
	private Teacher teacher;
	/**
	 * 试卷大题名称
	 */
	private String bigName;
	/**
	 * 题型（这里也是抽题条件）
	 */
	private String subjectType;
	/**
	 * 是否小题打乱（默认要打乱）
	 */
	private Boolean isSmallMix;
	/**
	 * 大题分值
	 */
	private Double score;
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
	/**
	 * 试卷模板小题
	 */
	private Set<PaperBigSmall> paperBigSmalls = new HashSet<PaperBigSmall>(0);

	// Constructors

	/** default constructor */
	public PaperBig() {
	}

	/** minimal constructor */
	public PaperBig(PaperBigId id, Paper paper, Teacher teacher,
			String bigName, String subjectType, Boolean isSmallMix,
			Double score, Integer array, Timestamp operateTime) {
		this.id = id;
		this.paper = paper;
		this.teacher = teacher;
		this.bigName = bigName;
		this.subjectType = subjectType;
		this.isSmallMix = isSmallMix;
		this.score = score;
		this.array = array;
		this.operateTime = operateTime;
	}

	/** full constructor */
	public PaperBig(PaperBigId id, Paper paper, Teacher teacher,
			String bigName, String subjectType, Boolean isSmallMix,
			Double score, Integer array, String note, Timestamp operateTime,
			Set<PaperBigSmall> paperBigSmalls) {
		this.id = id;
		this.paper = paper;
		this.teacher = teacher;
		this.bigName = bigName;
		this.subjectType = subjectType;
		this.isSmallMix = isSmallMix;
		this.score = score;
		this.array = array;
		this.note = note;
		this.operateTime = operateTime;
		this.paperBigSmalls = paperBigSmalls;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "paperId", column = @Column(name = "paperId", nullable = false)),
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)) })
	public PaperBigId getId() {
		return this.id;
	}

	public void setId(PaperBigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paperId", nullable = false, insertable = false, updatable = false)
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId", nullable = false)
	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Column(name = "bigName", nullable = false)
	public String getBigName() {
		return this.bigName;
	}

	public void setBigName(String bigName) {
		this.bigName = bigName;
	}

	@Column(name = "subjectType", nullable = false, length = 20)
	public String getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	@Column(name = "isSmallMix", nullable = false)
	public Boolean getIsSmallMix() {
		return this.isSmallMix;
	}

	public void setIsSmallMix(Boolean isSmallMix) {
		this.isSmallMix = isSmallMix;
	}

	@Column(name = "score", nullable = false, precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
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

	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "paperBig")
	public Set<PaperBigSmall> getPaperBigSmalls() {
		return this.paperBigSmalls;
	}

	public void setPaperBigSmalls(Set<PaperBigSmall> paperBigSmalls) {
		this.paperBigSmalls = paperBigSmalls;
	}

}