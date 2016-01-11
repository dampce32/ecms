package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:答卷Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Embeddable
public class ExamAnswerId extends BaseModel {

	private static final long serialVersionUID = 3592604029465988395L;
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

	// Constructors

	/** default constructor */
	public ExamAnswerId() {
	}

	/** full constructor */
	public ExamAnswerId(Integer competitionId, Integer groupId,
			Integer studentId) {
		this.competitionId = competitionId;
		this.groupId = groupId;
		this.studentId = studentId;
	}

	// Property accessors

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
		if (!(other instanceof ExamAnswerId))
			return false;
		ExamAnswerId castOther = (ExamAnswerId) other;

		return ((this.getCompetitionId() == castOther.getCompetitionId()) || (this
				.getCompetitionId() != null
				&& castOther.getCompetitionId() != null && this
				.getCompetitionId().equals(castOther.getCompetitionId())))
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