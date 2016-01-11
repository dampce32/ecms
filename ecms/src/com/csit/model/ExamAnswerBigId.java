package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:答卷大题Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Embeddable
public class ExamAnswerBigId implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -725507091886120272L;
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
	 * 大题Id
	 */
	private Integer bigId;

	// Constructors

	/** default constructor */
	public ExamAnswerBigId() {
	}

	/** full constructor */
	public ExamAnswerBigId(Integer bigId, Integer competitionId,
			Integer groupId, Integer studentId) {
		this.bigId = bigId;
		this.competitionId = competitionId;
		this.groupId = groupId;
		this.studentId = studentId;
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

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ExamAnswerBigId))
			return false;
		ExamAnswerBigId castOther = (ExamAnswerBigId) other;

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
						.getStudentId().equals(castOther.getStudentId())));
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
		return result;
	}

}