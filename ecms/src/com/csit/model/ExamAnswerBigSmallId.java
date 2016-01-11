package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description: 答卷小题Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Embeddable
public class ExamAnswerBigSmallId implements java.io.Serializable {

	private static final long serialVersionUID = 2892616047488238275L;
	// Fields
	/**
	 * 赛事Id
	 */
	private Integer competitionId;
	/**
	 * 参赛组别Id
	 */
	private Integer groupId;
	/**
	 * 参赛学生Id
	 */
	private Integer studentId;
	/**
	 * 答卷大题Id
	 */
	private Integer bigId;
	/**
	 * 答卷小题Id
	 */
	private Integer smallId;

	// Constructors

	/** default constructor */
	public ExamAnswerBigSmallId() {
	}

	/** full constructor */
	public ExamAnswerBigSmallId(Integer bigId, Integer competitionId,
			Integer groupId, Integer studentId, Integer smallId) {
		this.bigId = bigId;
		this.competitionId = competitionId;
		this.groupId = groupId;
		this.studentId = studentId;
		this.smallId = smallId;
	}

	// Property accessors

	@Column(name = "bigId", nullable = false)
	public Integer getBigId() {
		return this.bigId;
	}

	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}

	@Column(name = "competitionId", nullable = false)
	public Integer getCompetitionId() {
		return this.competitionId;
	}

	public void setCompetitionId(Integer competitionId) {
		this.competitionId = competitionId;
	}

	@Column(name = "groupId", nullable = false)
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "studentId", nullable = false)
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Column(name = "smallId", nullable = false)
	public Integer getSmallId() {
		return this.smallId;
	}

	public void setSmallId(Integer smallId) {
		this.smallId = smallId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ExamAnswerBigSmallId))
			return false;
		ExamAnswerBigSmallId castOther = (ExamAnswerBigSmallId) other;

		return ((this.getBigId() == castOther.getBigId()) || (this.getBigId() != null
				&& castOther.getBigId() != null && this.getBigId().equals(
				castOther.getBigId())))
				&& ((this.getCompetitionId() == castOther.getCompetitionId()) || (this
						.getCompetitionId() != null
						&& castOther.getCompetitionId() != null && this
						.getCompetitionId()
						.equals(castOther.getCompetitionId())))
				&& ((this.getGroupId() == castOther.getGroupId()) || (this
						.getGroupId() != null && castOther.getGroupId() != null && this
						.getGroupId().equals(castOther.getGroupId())))
				&& ((this.getStudentId() == castOther.getStudentId()) || (this
						.getStudentId() != null
						&& castOther.getStudentId() != null && this
						.getStudentId().equals(castOther.getStudentId())))
				&& ((this.getSmallId() == castOther.getSmallId()) || (this
						.getSmallId() != null && castOther.getSmallId() != null && this
						.getSmallId().equals(castOther.getSmallId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBigId() == null ? 0 : this.getBigId().hashCode());
		result = 37
				* result
				+ (getCompetitionId() == null ? 0 : this.getCompetitionId()
						.hashCode());
		result = 37 * result
				+ (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		result = 37 * result
				+ (getStudentId() == null ? 0 : this.getStudentId().hashCode());
		result = 37 * result
				+ (getSmallId() == null ? 0 : this.getSmallId().hashCode());
		return result;
	}

}