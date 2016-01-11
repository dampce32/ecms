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
 * @Description:试卷小题临时表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Entity
@Table(name = "T_Temp_PaperBigSmall")
public class TempPaperBigSmall extends BaseModel {

	private static final long serialVersionUID = -5526016154522160945L;
	// Fields
	/**
	 * 试卷小题临时表Id
	 */
	private TempPaperBigSmallId id;
	/**
	 * 试卷大题临时表
	 */
	private TempPaperBig tempPaperBig;
	/**
	 * 试题号
	 */
	private Integer subjectId;
	/**
	 * 参赛分组Id
	 */
	private Integer groupId;
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

	// Constructors

	/** default constructor */
	public TempPaperBigSmall() {
	}

	/** minimal constructor */
	public TempPaperBigSmall(TempPaperBigSmallId id, TempPaperBig tempPaperBig,
			Integer array) {
		this.id = id;
		this.tempPaperBig = tempPaperBig;
		this.array = array;
	}

	/** full constructor */
	public TempPaperBigSmall(TempPaperBigSmallId id, TempPaperBig tempPaperBig,
			Integer subjectId, Integer groupId, String difficulty,
			Double score, Boolean isOptionMix, Integer array, String note) {
		this.id = id;
		this.tempPaperBig = tempPaperBig;
		this.subjectId = subjectId;
		this.groupId = groupId;
		this.difficulty = difficulty;
		this.score = score;
		this.isOptionMix = isOptionMix;
		this.array = array;
		this.note = note;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "teacherId", column = @Column(name = "teacherId", nullable = false)),
			@AttributeOverride(name = "operateTime", column = @Column(name = "operateTime", nullable = false, length = 23)),
			@AttributeOverride(name = "paperId", column = @Column(name = "paperId", nullable = false)),
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)),
			@AttributeOverride(name = "smallId", column = @Column(name = "smallId", nullable = false)) })
	public TempPaperBigSmallId getId() {
		return this.id;
	}

	public void setId(TempPaperBigSmallId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "teacherId", referencedColumnName = "teacherId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "operateTime", referencedColumnName = "operateTime", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "paperId", referencedColumnName = "paperId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "bigId", referencedColumnName = "bigId", nullable = false, insertable = false, updatable = false) })
	public TempPaperBig getTempPaperBig() {
		return this.tempPaperBig;
	}

	public void setTempPaperBig(TempPaperBig tempPaperBig) {
		this.tempPaperBig = tempPaperBig;
	}

	@Column(name = "subjectId")
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "groupId")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "difficulty")
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

	@Column(name = "isOptionMix")
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

}