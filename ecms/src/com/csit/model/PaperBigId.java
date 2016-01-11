package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:试卷大题Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Embeddable
public class PaperBigId extends BaseModel {

	// Fields

	private static final long serialVersionUID = 5300880265618529156L;
	/**
	 * 试卷号
	 */
	private Integer paperId;
	/**
	 * 试卷大题号
	 */
	private Integer bigId;

	// Constructors

	/** default constructor */
	public PaperBigId() {
	}

	/** full constructor */
	public PaperBigId(Integer paperId, Integer bigId) {
		this.paperId = paperId;
		this.bigId = bigId;
	}

	// Property accessors

	@Column(name = "paperId", nullable = false)
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
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
		if (!(other instanceof PaperBigId))
			return false;
		PaperBigId castOther = (PaperBigId) other;

		return ((this.getPaperId() == castOther.getPaperId()) || (this
				.getPaperId() != null && castOther.getPaperId() != null && this
				.getPaperId().equals(castOther.getPaperId())))
				&& ((this.getBigId() == castOther.getBigId()) || (this
						.getBigId() != null && castOther.getBigId() != null && this
						.getBigId().equals(castOther.getBigId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result
				+ (getBigId() == null ? 0 : this.getBigId().hashCode());
		return result;
	}

}