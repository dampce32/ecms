package com.csit.model;

// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CompetitionPrize entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CompetitionPrize")
public class CompetitionPrize extends BaseModel {

	private static final long serialVersionUID = -6732593617755435584L;
	// Fields

	private Integer competitionPrizeId;
	private CompetitionGroup competitionGroup;
	private Prize prize;
	private String award;
	private Integer array;

	// Constructors

	/** default constructor */
	public CompetitionPrize() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "competitionPrizeId", unique = true, nullable = false)
	public Integer getCompetitionPrizeId() {
		return competitionPrizeId;
	}

	public void setCompetitionPrizeId(Integer competitionPrizeId) {
		this.competitionPrizeId = competitionPrizeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionGroupId", nullable = false)
	public CompetitionGroup getCompetitionGroup() {
		return this.competitionGroup;
	}

	public void setCompetitionGroup(CompetitionGroup competitionGroup) {
		this.competitionGroup = competitionGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prizeId", nullable = false)
	public Prize getPrize() {
		return this.prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

	@Column(name = "award")
	public String getAward() {
		return this.award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	@Column(name = "array")
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

}