package com.csit.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Description:试卷小题临时表Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Embeddable
public class TempPaperBigSmallId extends BaseModel {

	// Fields

	private static final long serialVersionUID = -4569267802354321533L;
	/**
	 * 最后操作员
	 */
	private Integer teacherId;
	/**
	 * 最后操作时间
	 */
	private Timestamp operateTime;
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
	public TempPaperBigSmallId() {
	}

	/** full constructor */
	public TempPaperBigSmallId(Integer teacherId, Timestamp operateTime,
			Integer paperId, Integer bigId, Integer smallId) {
		this.teacherId = teacherId;
		this.operateTime = operateTime;
		this.paperId = paperId;
		this.bigId = bigId;
		this.smallId = smallId;
	}

	// Property accessors

	@Column(name = "teacherId", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "operateTime", nullable = false, length = 23)
	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

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
		if (!(other instanceof TempPaperBigSmallId))
			return false;
		TempPaperBigSmallId castOther = (TempPaperBigSmallId) other;

		return ((this.getTeacherId() == castOther.getTeacherId()) || (this
				.getTeacherId() != null && castOther.getTeacherId() != null && this
				.getTeacherId().equals(castOther.getTeacherId())))
				&& ((this.getOperateTime() == castOther.getOperateTime()) || (this
						.getOperateTime() != null
						&& castOther.getOperateTime() != null && this
						.getOperateTime().equals(castOther.getOperateTime())))
				&& ((this.getPaperId() == castOther.getPaperId()) || (this
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
				+ (getTeacherId() == null ? 0 : this.getTeacherId().hashCode());
		result = 37
				* result
				+ (getOperateTime() == null ? 0 : this.getOperateTime()
						.hashCode());
		result = 37 * result
				+ (getPaperId() == null ? 0 : this.getPaperId().hashCode());
		result = 37 * result
				+ (getBigId() == null ? 0 : this.getBigId().hashCode());
		result = 37 * result
				+ (getSmallId() == null ? 0 : this.getSmallId().hashCode());
		return result;
	}

}