package com.csit.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:试卷模板小题Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Embeddable
public class PaperBigSmallId extends BaseModel {

	private static final long serialVersionUID = 7869368776807186601L;
	// Fields
	/**
	 * 试卷号
	 */
	private Integer paperId;
	/**
	 * 试卷大题号
	 */
	private Integer bigId;
	/**
	 * 试卷小题号
	 */
	private Integer smallId;

	// Constructors

	/** default constructor */
	public PaperBigSmallId() {
	}

	/** full constructor */
	public PaperBigSmallId(Integer paperId, Integer bigId, Integer smallId) {
		this.paperId = paperId;
		this.bigId = bigId;
		this.smallId = smallId;
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
		if (!(other instanceof PaperBigSmallId))
			return false;
		PaperBigSmallId castOther = (PaperBigSmallId) other;

		return ((this.getPaperId() == castOther.getPaperId()) || (this
				.getPaperId() != null && castOther.getPaperId() != null && this
				.getPaperId().equals(castOther.getPaperId())))
				&& ((this.getBigId() == castOther.getBigId()) || (this
						.getBigId() != null && castOther.getBigId() != null && this
						.getBigId().equals(castOther.getBigId())))
				&& ((this.getSmallId() == castOther.getSmallId()) || (this
						.getSmallId() != null && castOther.getSmallId() != null && this
						.getSmallId().equals(castOther.getSmallId())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result
				+ (getBigId() == null ? 0 : this.getBigId().hashCode());
		result = 37 * result
				+ (getSmallId() == null ? 0 : this.getSmallId().hashCode());
		return result;
	}

}