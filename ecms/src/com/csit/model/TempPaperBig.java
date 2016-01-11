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
 * @Description: 试卷大题临时表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Entity
@Table(name = "T_Temp_PaperBig")
public class TempPaperBig extends BaseModel {

	// Fields

	private static final long serialVersionUID = 6015876951805816302L;
	/**
	 * 试卷大题临时表Id
	 */
	private TempPaperBigId id;
	/**
	 * 试卷临时表
	 */
	private TempPaper tempPaper;
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
	 * 试卷小题临时表
	 */
	private Set<TempPaperBigSmall> tempPaperBigSmalls = new HashSet<TempPaperBigSmall>(
			0);

	// Constructors

	/** default constructor */
	public TempPaperBig() {
	}

	/** minimal constructor */
	public TempPaperBig(TempPaperBigId id, TempPaper tempPaper, Integer array) {
		this.id = id;
		this.tempPaper = tempPaper;
		this.array = array;
	}

	/** full constructor */
	public TempPaperBig(TempPaperBigId id, TempPaper tempPaper, String bigName,
			String subjectType, Boolean isSmallMix, Double score,
			Integer array, String note,
			Set<TempPaperBigSmall> tempPaperBigSmalls) {
		this.id = id;
		this.tempPaper = tempPaper;
		this.bigName = bigName;
		this.subjectType = subjectType;
		this.isSmallMix = isSmallMix;
		this.score = score;
		this.array = array;
		this.note = note;
		this.tempPaperBigSmalls = tempPaperBigSmalls;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "teacherId", column = @Column(name = "teacherId", nullable = false)),
			@AttributeOverride(name = "operateTime", column = @Column(name = "operateTime", nullable = false, length = 23)),
			@AttributeOverride(name = "paperId", column = @Column(name = "paperId", nullable = false)),
			@AttributeOverride(name = "bigId", column = @Column(name = "bigId", nullable = false)) })
	public TempPaperBigId getId() {
		return this.id;
	}

	public void setId(TempPaperBigId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "teacherId", referencedColumnName = "teacherId", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "operateTime", referencedColumnName = "operateTime", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "paperId", referencedColumnName = "paperId", nullable = false, insertable = false, updatable = false) })
	public TempPaper getTempPaper() {
		return this.tempPaper;
	}

	public void setTempPaper(TempPaper tempPaper) {
		this.tempPaper = tempPaper;
	}

	@Column(name = "bigName")
	public String getBigName() {
		return this.bigName;
	}

	public void setBigName(String bigName) {
		this.bigName = bigName;
	}

	@Column(name = "subjectType", length = 20)
	public String getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	@Column(name = "isSmallMix")
	public Boolean getIsSmallMix() {
		return this.isSmallMix;
	}

	public void setIsSmallMix(Boolean isSmallMix) {
		this.isSmallMix = isSmallMix;
	}

	@Column(name = "score", precision = 53, scale = 0)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tempPaperBig")
	public Set<TempPaperBigSmall> getTempPaperBigSmalls() {
		return this.tempPaperBigSmalls;
	}

	public void setTempPaperBigSmalls(Set<TempPaperBigSmall> tempPaperBigSmalls) {
		this.tempPaperBigSmalls = tempPaperBigSmalls;
	}

}