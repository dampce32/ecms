package com.csit.model;


import static javax.persistence.GenerationType.IDENTITY;

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
 * @Description:赛事组别
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-30
 * @author jcf
 * @vesion 1.0
 */
@Entity
@Table(name = "T_CompetitionGroup")
public class CompetitionGroup extends BaseModel {

	private static final long serialVersionUID = -3851038181954847889L;
	// Fields

	private Integer competitionGroupId;
	private Competition competition;
	private Paper paper;
	private Group group;
	private Integer array;
	private Set<CompetitionPrize> competitionPrizes = new HashSet<CompetitionPrize>(0);
	private Set<TrainingClass> trainingClasses = new HashSet<TrainingClass>(0);

	// Constructors

	/** default constructor */
	public CompetitionGroup() {
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "competitionGroupId", unique = true, nullable = false)
	public Integer getCompetitionGroupId() {
		return competitionGroupId;
	}

	public void setCompetitionGroupId(Integer competitionGroupId) {
		this.competitionGroupId = competitionGroupId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competitionId", nullable = false)
	public Competition getCompetition() {
		return this.competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paperId")
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId", nullable = false)
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "array", nullable = false)
	public Integer getArray() {
		return this.array;
	}

	public void setArray(Integer array) {
		this.array = array;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "competitionGroup")
	public Set<CompetitionPrize> getCompetitionPrizes() {
		return this.competitionPrizes;
	}

	public void setCompetitionPrizes(Set<CompetitionPrize> competitionPrizes) {
		this.competitionPrizes = competitionPrizes;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "competitionGroup")
	public Set<TrainingClass> getTrainingClasses() {
		return this.trainingClasses;
	}

	public void setTrainingClasses(Set<TrainingClass> trainingClasses) {
		this.trainingClasses = trainingClasses;
	}
	

}