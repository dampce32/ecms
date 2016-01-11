package com.csit.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @Description:试卷临时表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Entity
@Table(name = "T_Temp_Paper")
public class TempPaper extends BaseModel {

	// Fields

	private static final long serialVersionUID = -5377002414382485028L;
	/**
	 * 试卷临时表Id
	 */
	private TempPaperId id;
	/**
	 * 试卷类型
	 */
	private String paperType;
	/**
	 * 试卷名称
	 */
	private String paperName;
	/**
	 * 参赛分组Id
	 */
	private Integer groupId;
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
	 * 出卷教师
	 */
	private Integer publishTeacherId;
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
	 * 试卷答题
	 */
	private Set<TempPaperBig> tempPaperBigs = new HashSet<TempPaperBig>(0);

	// Constructors

	/** default constructor */
	public TempPaper() {
	}

	/** minimal constructor */
	public TempPaper(TempPaperId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "teacherId", column = @Column(name = "teacherId", nullable = false)),
			@AttributeOverride(name = "operateTime", column = @Column(name = "operateTime", nullable = false, length = 23)),
			@AttributeOverride(name = "paperId", column = @Column(name = "paperId", nullable = false)) })
	public TempPaperId getId() {
		return this.id;
	}

	public void setId(TempPaperId id) {
		this.id = id;
	}

	@Column(name = "paperType")
	public String getPaperType() {
		return this.paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	@Column(name = "paperName")
	public String getPaperName() {
		return this.paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	@Column(name = "groupId")
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "limits")
	public Integer getLimits() {
		return this.limits;
	}

	public void setLimits(Integer limits) {
		this.limits = limits;
	}

	@Column(name = "isBigMix")
	public Boolean getIsBigMix() {
		return this.isBigMix;
	}

	public void setIsBigMix(Boolean isBigMix) {
		this.isBigMix = isBigMix;
	}

	@Column(name = "isSmallContinue")
	public Boolean getIsSmallContinue() {
		return this.isSmallContinue;
	}

	public void setIsSmallContinue(Boolean isSmallContinue) {
		this.isSmallContinue = isSmallContinue;
	}

	@Column(name = "score", precision = 53, scale = 0)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "publishTeacherId")
	public Integer getPublishTeacherId() {
		return this.publishTeacherId;
	}

	public void setPublishTeacherId(Integer publishTeacherId) {
		this.publishTeacherId = publishTeacherId;
	}

	@Column(name = "publishDate", length = 23)
	public Timestamp getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "state")
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tempPaper")
	public Set<TempPaperBig> getTempPaperBigs() {
		return this.tempPaperBigs;
	}

	public void setTempPaperBigs(Set<TempPaperBig> tempPaperBigs) {
		this.tempPaperBigs = tempPaperBigs;
	}

}