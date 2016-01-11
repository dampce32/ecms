package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:答卷小题的子小题
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-6-8
 * @Author lys
 */
@Embeddable
public class ExamAnswerBigSmallSubId implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -710899557357763307L;
	/**
	 * 赛事Id
	 */
	private Integer competitionId;
	/**
	 * 参赛组别Id
	 */
	private Integer groupId;
	/**
	 * 学生Id
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
	/**
	 * 答卷小题子小题Id
	 */
	private Integer subId;

	// Constructors

	/** default constructor */
	public ExamAnswerBigSmallSubId() {
	}

	/** full constructor */
	public ExamAnswerBigSmallSubId(Integer studentId, Integer competitionId,
			Integer groupId, Integer subId, Integer smallId, Integer bigId) {
		this.studentId = studentId;
		this.competitionId = competitionId;
		this.groupId = groupId;
		this.subId = subId;
		this.smallId = smallId;
		this.bigId = bigId;
	}

	// Property accessors

	@Column(name = "studentId", nullable = false)
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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

	@Column(name = "subId", nullable = false)
	public Integer getSubId() {
		return this.subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	@Column(name = "smallId", nullable = false)
	public Integer getSmallId() {
		return this.smallId;
	}

	public void setSmallId(Integer smallId) {
		this.smallId = smallId;
	}

	@Column(name = "bigId", nullable = false)
	public Integer getBigId() {
		return this.bigId;
	}

	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ExamAnswerBigSmallSubId))
			return false;
		ExamAnswerBigSmallSubId castOther = (ExamAnswerBigSmallSubId) other;

		return ((this.getStudentId() == castOther.getStudentId()) || (this
				.getStudentId() != null && castOther.getStudentId() != null && this
				.getStudentId().equals(castOther.getStudentId())))
				&& ((this.getCompetitionId() == castOther.getCompetitionId()) || (this
						.getCompetitionId() != null
						&& castOther.getCompetitionId() != null && this
						.getCompetitionId()
						.equals(castOther.getCompetitionId())))
				&& ((this.getGroupId() == castOther.getGroupId()) || (this
						.getGroupId() != null && castOther.getGroupId() != null && this
						.getGroupId().equals(castOther.getGroupId())))
				&& ((this.getSubId() == castOther.getSubId()) || (this
						.getSubId() != null && castOther.getSubId() != null && this
						.getSubId().equals(castOther.getSubId())))
				&& ((this.getSmallId() == castOther.getSmallId()) || (this
						.getSmallId() != null && castOther.getSmallId() != null && this
						.getSmallId().equals(castOther.getSmallId())))
				&& ((this.getBigId() == castOther.getBigId()) || (this
						.getBigId() != null && castOther.getBigId() != null && this
						.getBigId().equals(castOther.getBigId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStudentId() == null ? 0 : this.getStudentId().hashCode());
		result = 37
				* result
				+ (getCompetitionId() == null ? 0 : this.getCompetitionId()
						.hashCode());
		result = 37 * result
				+ (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		result = 37 * result
				+ (getSubId() == null ? 0 : this.getSubId().hashCode());
		result = 37 * result
				+ (getSmallId() == null ? 0 : this.getSmallId().hashCode());
		result = 37 * result
				+ (getBigId() == null ? 0 : this.getBigId().hashCode());
		return result;
	}

}