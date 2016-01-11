package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:试题表和试题子表中间表
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-5-7
 * @author jcf
 * @vesion 1.0
 */
@Embeddable
public class SubjectSubId extends BaseModel {

	private static final long serialVersionUID = -3885568513585273034L;
	private Integer subjectId;
	private Integer subId;

	// Constructors

	/** default constructor */
	public SubjectSubId() {
	}

	/** full constructor */
	public SubjectSubId(Integer subjectId, Integer subId) {
		this.subjectId = subjectId;
		this.subId = subId;
	}

	// Property accessors

	@Column(name = "SubjectID", nullable = false)
	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "SubID", nullable = false)
	public Integer getSubId() {
		return this.subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SubjectSubId))
			return false;
		SubjectSubId castOther = (SubjectSubId) other;

		return ((this.getSubjectId() == castOther.getSubjectId()) || (this
				.getSubjectId() != null && castOther.getSubjectId() != null && this
				.getSubjectId().equals(castOther.getSubjectId())))
				&& ((this.getSubId() == castOther.getSubId()) || (this
						.getSubId() != null && castOther.getSubId() != null && this
						.getSubId().equals(castOther.getSubId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSubjectId() == null ? 0 : this.getSubjectId().hashCode());
		result = 37 * result
				+ (getSubId() == null ? 0 : this.getSubId().hashCode());
		return result;
	}

}