package com.csit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @Description:试卷临时表Id
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-4-27
 * @Author lys
 */
@Embeddable
public class TempPaperId extends BaseModel {

	// Fields

	private static final long serialVersionUID = -4334261034599592327L;
	/**
	 * 最后操作员
	 */
	private Integer teacherId;
	/**
	 * 最后操作时间
	 */
	private Date operateTime;
	/**
	 * 试卷编号
	 */
	private Integer paperId;

	// Constructors

	/** default constructor */
	public TempPaperId() {
	}


	// Property accessors

	@Column(name = "teacherId", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "operateTime", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "paperId", nullable = false)
	public Integer getPaperId() {
		return this.paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TempPaperId))
			return false;
		TempPaperId castOther = (TempPaperId) other;

		return ((this.getTeacherId() == castOther.getTeacherId()) || (this
				.getTeacherId() != null && castOther.getTeacherId() != null && this
				.getTeacherId().equals(castOther.getTeacherId())))
				&& ((this.getOperateTime() == castOther.getOperateTime()) || (this
						.getOperateTime() != null
						&& castOther.getOperateTime() != null && this
						.getOperateTime().equals(castOther.getOperateTime())))
				&& ((this.getPaperId() == castOther.getPaperId()) || (this
						.getPaperId() != null && castOther.getPaperId() != null && this
						.getPaperId().equals(castOther.getPaperId())));
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
		return result;
	}

}