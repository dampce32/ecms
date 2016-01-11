package com.csit.model;
import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Description:赛事
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Competition")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Competition extends BaseModel {

	private static final long serialVersionUID = 623704334865451160L;
	// Fields
	/**
	 * 赛事Id
	 */
	private Integer competitionId;
	/**
	 * 上一级赛事
	 */
	private Competition parentCompetition;
	/**
	 * 赛事名称
	 */
	private String competitionName;
	/**
	 * 赛事编号
	 */
	private String competitionCode;
	/**
	 * 赛事开始时间
	 */
	private Timestamp beginDate;
	/**
	 * 赛事结束时间
	 */
	private Timestamp endDate;
	/**
	 * 赛事简要说明
	 */
	private String competitionNote;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 图片
	 */
	private Picture picture;
	/**
	 * 
	 */
	private Set<Competition> competitions = new HashSet<Competition>(0);
	/**
	 * 
	 */
	private Set<CompetitionGroup> competitionGroups = new HashSet<CompetitionGroup>(0);
	/**
	 * 
	 */
	private Set<Expend> expends = new HashSet<Expend>(0);

	// Constructors

	/** default constructor */
	public Competition() {
	}

	/** minimal constructor */
	public Competition(Integer competitionId, String competitionName,
			String competitionCode, Integer status) {
		this.competitionId = competitionId;
		this.competitionName = competitionName;
		this.competitionCode = competitionCode;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "competitionId", unique = true, nullable = false)
	public Integer getCompetitionId() {
		return this.competitionId;
	}

	public void setCompetitionId(Integer competitionId) {
		this.competitionId = competitionId;
	}

	@Column(name = "competitionName", nullable = false, length = 100)
	public String getCompetitionName() {
		return this.competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

	@Column(name = "competitionCode", unique = true, nullable = false, length = 50)
	public String getCompetitionCode() {
		return this.competitionCode;
	}

	public void setCompetitionCode(String competitionCode) {
		this.competitionCode = competitionCode;
	}

	@Column(name = "beginDate", nullable = false, length = 23)
	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "endDate", nullable = false, length = 23)
	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	@Column(name = "competitionNote")
	public String getCompetitionNote() {
		return this.competitionNote;
	}

	public void setCompetitionNote(String competitionNote) {
		this.competitionNote = competitionNote;
	}

	@Column(name = "note", length = 500)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pictureId")
	public Picture getPicture() {
		return this.picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentCompetition")
	public Set<Competition> getCompetitions() {
		return this.competitions;
	}

	public void setCompetitions(Set<Competition> competitions) {
		this.competitions = competitions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "competition")
	public Set<CompetitionGroup> getCompetitionGroups() {
		return this.competitionGroups;
	}

	public void setCompetitionGroups(Set<CompetitionGroup> competitionGroups) {
		this.competitionGroups = competitionGroups;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "competition")
	public Set<Expend> getExpends() {
		return expends;
	}

	public void setExpends(Set<Expend> expends) {
		this.expends = expends;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentCompetitionId")
	public Competition getParentCompetition() {
		return parentCompetition;
	}

	public void setParentCompetition(Competition parentCompetition) {
		this.parentCompetition = parentCompetition;
	}

}