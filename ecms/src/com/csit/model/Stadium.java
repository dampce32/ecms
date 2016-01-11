package com.csit.model;

// default package

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


/**
 * @Description:赛场
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-5
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_Stadium")
public class Stadium extends BaseModel {

	private static final long serialVersionUID = 7775235091691377837L;
	// Fields

	private Integer stadiumId;
	private CompetitionGroup competitionGroup;
	private String stadiumName;
	private String stadiumAddr;
	private Timestamp competitionDate;
	private Integer limit;
	private Integer arrangeNo;
	private String note;
	private Set<StadiumStudent> stadiumStudents = new HashSet<StadiumStudent>(0);

	// Constructors

	/** default constructor */
	public Stadium() {
	}

	/** minimal constructor */
	public Stadium(String stadiumName, Integer limit, Integer arrangeNo) {
		this.stadiumName = stadiumName;
		this.limit = limit;
		this.arrangeNo = arrangeNo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "stadiumId", unique = true, nullable = false)
	public Integer getStadiumId() {
		return this.stadiumId;
	}

	public void setStadiumId(Integer stadiumId) {
		this.stadiumId = stadiumId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionGroupId", nullable = false)
	public CompetitionGroup getCompetitionGroup() {
		return competitionGroup;
	}

	public void setCompetitionGroup(CompetitionGroup competitionGroup) {
		this.competitionGroup = competitionGroup;
	}

	@Column(name = "stadiumName", nullable = false, length = 100)
	public String getStadiumName() {
		return this.stadiumName;
	}

	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}

	@Column(name = "stadiumAddr", length = 100)
	public String getStadiumAddr() {
		return this.stadiumAddr;
	}

	public void setStadiumAddr(String stadiumAddr) {
		this.stadiumAddr = stadiumAddr;
	}

	@Column(name = "competitionDate", length = 23)
	public Timestamp getCompetitionDate() {
		return this.competitionDate;
	}

	public void setCompetitionDate(Timestamp competitionDate) {
		this.competitionDate = competitionDate;
	}

	@Column(name = "limit", nullable = false)
	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Column(name = "arrangeNo", nullable = false)
	public Integer getArrangeNo() {
		return this.arrangeNo;
	}

	public void setArrangeNo(Integer arrangeNo) {
		this.arrangeNo = arrangeNo;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stadium")
	public Set<StadiumStudent> getStadiumStudents() {
		return this.stadiumStudents;
	}

	public void setStadiumStudents(Set<StadiumStudent> stadiumStudents) {
		this.stadiumStudents = stadiumStudents;
	}

}